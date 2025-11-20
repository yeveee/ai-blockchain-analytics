package com.ai.blockchain_analytics.service;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import com.ai.blockchain_analytics.repository.PriceSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Service for fetching cryptocurrency prices from CoinGecko API.
 * Periodically retrieves price data and stores it in the database.
 */
@Service
public class CoinGeckoService {

    private static final Logger logger = LoggerFactory.getLogger(CoinGeckoService.class);
    private static final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3/simple/price";
    private static final String[] TRACKED_SYMBOLS = {"BTC", "ETH", "MATIC"};
    private static final long FETCH_INTERVAL_MS = 300000; // 5 minutes

    private final PriceSnapshotRepository repository;
    private final RestTemplate restTemplate;

    public CoinGeckoService(PriceSnapshotRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    /**
     * Maps cryptocurrency symbols to CoinGecko IDs.
     * 
     * @param symbol the cryptocurrency symbol (e.g., BTC, ETH)
     * @return the corresponding CoinGecko ID
     */
    private String mapSymbolToId(String symbol) {
        switch (symbol.toUpperCase()) {
            case "BTC":
                return "bitcoin";
            case "ETH":
                return "ethereum";
            case "MATIC":
                return "matic-network";
            default:
                return symbol.toLowerCase();
        }
    }

    /**
     * Fetches the current price for a cryptocurrency and saves it to the database.
     * 
     * @param symbol the cryptocurrency symbol
     */
    public void fetchAndSavePrice(String symbol) {
        String id = mapSymbolToId(symbol);
        String url = String.format("%s?ids=%s&vs_currencies=usd", COINGECKO_API_URL, id);

        try {
            logger.debug("Fetching price from CoinGecko for: {}", symbol);
            Map<String, Map<String, Object>> response = restTemplate.getForObject(url, Map.class);
            
            if (response == null || !response.containsKey(id)) {
                logger.warn("Invalid response from CoinGecko for symbol: {}", symbol);
                return;
            }

            Object priceObj = response.get(id).get("usd");
            if (!(priceObj instanceof Number)) {
                logger.warn("Invalid price format for symbol: {}", symbol);
                return;
            }

            BigDecimal price = BigDecimal.valueOf(((Number) priceObj).doubleValue());
            savePriceSnapshot(symbol, price);
            logger.info("Successfully saved price snapshot for {}: ${}", symbol, price);
        } catch (RestClientException e) {
            logger.error("Failed to fetch price from CoinGecko API for symbol: {}", symbol, e);
        } catch (Exception e) {
            logger.error("Unexpected error while processing price for symbol: {}", symbol, e);
        }
    }

    /**
     * Creates and saves a price snapshot to the database.
     * 
     * @param symbol the cryptocurrency symbol
     * @param price the current price in USD
     */
    private void savePriceSnapshot(String symbol, BigDecimal price) {
        PriceSnapshot snapshot = new PriceSnapshot();
        snapshot.setSymbol(symbol.toUpperCase());
        snapshot.setPrice(price);
        snapshot.setTimestamp(LocalDateTime.now());
        repository.save(snapshot);
        logger.debug("Price snapshot saved: {}", snapshot);
    }

    /**
     * Scheduled task that fetches prices for all tracked cryptocurrencies.
     * Runs every 5 minutes.
     */
    @Scheduled(fixedRate = FETCH_INTERVAL_MS)
    public void fetchPricesScheduled() {
        logger.info("Starting scheduled price fetch for {} symbols", TRACKED_SYMBOLS.length);
        
        for (String symbol : TRACKED_SYMBOLS) {
            try {
                fetchAndSavePrice(symbol);
            } catch (Exception e) {
                logger.error("Error fetching price for symbol: {}", symbol, e);
            }
        }
        
        logger.info("Completed scheduled price fetch");
    }
}
