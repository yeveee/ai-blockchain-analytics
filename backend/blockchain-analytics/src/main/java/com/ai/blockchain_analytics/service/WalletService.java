package com.ai.blockchain_analytics.service;

import com.ai.blockchain_analytics.dto.WalletBalanceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class WalletService {

    @Value("${covalent.api.url}")
    private String covalentApiUrl;

    @Value("${covalent.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<WalletBalanceDTO> getWalletBalances(String address) {

        String url = UriComponentsBuilder
                .fromHttpUrl(covalentApiUrl + "/1/address/" + address + "/balances_v2/")
                .queryParam("key", apiKey)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response == null || response.get("data") == null)
            return new ArrayList<>();

        Map<String, Object> data = (Map<String, Object>) response.get("data");
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("items");

        List<WalletBalanceDTO> results = new ArrayList<>();

        for (Map<String, Object> item : items) {
            String name = (String) item.get("contract_name");
            String symbol = (String) item.get("contract_ticker_symbol");

            // SAFE DECIMALS
            Integer decimals = item.get("contract_decimals") != null
                    ? Integer.parseInt(item.get("contract_decimals").toString())
                    : 18;

            // SAFE RAW BALANCE
            String rawBalance = item.get("balance") != null 
                    ? item.get("balance").toString() 
                    : "0";

            BigDecimal balance = BigDecimal.ZERO;
            try {
                balance = new BigDecimal(rawBalance)
                        .divide(BigDecimal.TEN.pow(decimals));
            } catch (Exception ignored) {}

            // SAFE USD QUOTE
            BigDecimal quote = item.get("quote") != null
                    ? new BigDecimal(item.get("quote").toString())
                    : BigDecimal.ZERO;

            results.add(new WalletBalanceDTO(name, symbol, balance, quote));
        }

        return results;
    }
}


