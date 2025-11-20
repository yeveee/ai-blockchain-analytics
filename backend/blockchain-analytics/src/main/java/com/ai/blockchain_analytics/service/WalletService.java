package com.ai.blockchain_analytics.service;

import com.ai.blockchain_analytics.dto.WalletBalanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service for retrieving Ethereum wallet balances via Covalent API.
 * Fetches ERC-20 token balances and their current USD values.
 */
@Service
public class WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletService.class);
    private static final int DEFAULT_DECIMALS = 18;
    private static final String ETHEREUM_CHAIN_ID = "1";

    private final RestTemplate restTemplate;

    @Value("${covalent.api.url}")
    private String covalentApiUrl;

    @Value("${covalent.api.key}")
    private String apiKey;

    public WalletService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves all token balances for a given Ethereum address.
     * 
     * @param address the Ethereum wallet address
     * @return list of wallet balance DTOs
     * @throws RestClientException if the API request fails
     */
    public List<WalletBalanceDTO> getWalletBalances(String address) {
        try {
            String url = buildCovalentUrl(address);
            logger.debug("Calling Covalent API: {}", url);

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null || response.get("data") == null) {
                logger.warn("Empty response from Covalent API for address: {}", address);
                return new ArrayList<>();
            }

            return parseWalletBalances(response);
        } catch (RestClientException e) {
            logger.error("Failed to fetch wallet balances from Covalent API for address: {}", address, e);
            throw e;
        }
    }

    /**
     * Builds the Covalent API URL for fetching wallet balances.
     * 
     * @param address the Ethereum wallet address
     * @return the complete API URL with authentication
     */
    private String buildCovalentUrl(String address) {
        return UriComponentsBuilder
                .fromHttpUrl(String.format("%s/%s/address/%s/balances_v2/", 
                        covalentApiUrl, ETHEREUM_CHAIN_ID, address))
                .queryParam("key", apiKey)
                .toUriString();
    }

    /**
     * Parses the Covalent API response into WalletBalanceDTO objects.
     * 
     * @param response the API response map
     * @return list of parsed wallet balance DTOs
     */
    @SuppressWarnings("unchecked")
    private List<WalletBalanceDTO> parseWalletBalances(Map<String, Object> response) {
        Map<String, Object> data = (Map<String, Object>) response.get("data");
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("items");

        if (items == null || items.isEmpty()) {
            logger.info("No token balances found");
            return new ArrayList<>();
        }

        List<WalletBalanceDTO> results = new ArrayList<>();
        for (Map<String, Object> item : items) {
            try {
                WalletBalanceDTO dto = parseTokenBalance(item);
                results.add(dto);
            } catch (Exception e) {
                logger.warn("Failed to parse token balance: {}", item, e);
            }
        }

        logger.info("Successfully parsed {} token balances", results.size());
        return results;
    }

    /**
     * Parses a single token balance item from the API response.
     * 
     * @param item the token item map
     * @return wallet balance DTO
     */
    private WalletBalanceDTO parseTokenBalance(Map<String, Object> item) {
        String name = (String) item.get("contract_name");
        String symbol = (String) item.get("contract_ticker_symbol");

        Integer decimals = item.get("contract_decimals") != null
                ? Integer.parseInt(item.get("contract_decimals").toString())
                : DEFAULT_DECIMALS;

        String rawBalance = item.get("balance") != null 
                ? item.get("balance").toString() 
                : "0";

        BigDecimal balance = calculateBalance(rawBalance, decimals);
        BigDecimal usdValue = parseUsdValue(item.get("quote"));

        return new WalletBalanceDTO(name, symbol, balance, usdValue);
    }

    /**
     * Calculates the token balance from raw value and decimals.
     * 
     * @param rawBalance the raw balance string
     * @param decimals the number of decimal places
     * @return the calculated balance
     */
    private BigDecimal calculateBalance(String rawBalance, int decimals) {
        try {
            return new BigDecimal(rawBalance)
                    .divide(BigDecimal.TEN.pow(decimals), decimals, RoundingMode.DOWN);
        } catch (Exception e) {
            logger.warn("Failed to calculate balance from raw value: {}", rawBalance, e);
            return BigDecimal.ZERO;
        }
    }

    /**
     * Parses the USD value from the quote field.
     * 
     * @param quoteObj the quote object
     * @return the USD value as BigDecimal
     */
    private BigDecimal parseUsdValue(Object quoteObj) {
        try {
            return quoteObj != null 
                    ? new BigDecimal(quoteObj.toString())
                    : BigDecimal.ZERO;
        } catch (Exception e) {
            logger.warn("Failed to parse USD value: {}", quoteObj, e);
            return BigDecimal.ZERO;
        }
    }
}


