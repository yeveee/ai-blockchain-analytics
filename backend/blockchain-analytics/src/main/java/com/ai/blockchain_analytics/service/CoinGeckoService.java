package com.ai.blockchain_analytics.service;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import com.ai.blockchain_analytics.repository.PriceSnapshotRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CoinGeckoService {

    private final PriceSnapshotRepository repository;
    private final RestTemplate restTemplate;

    public CoinGeckoService(PriceSnapshotRepository repository) {
        this.repository = repository;
        this.restTemplate = new RestTemplate();
    }

    private String mapSymbolToId(String symbol) {
        switch (symbol.toUpperCase()) {
            case "BTC": return "bitcoin";
            case "ETH": return "ethereum";
            case "MATIC": return "matic-network";
            default: return symbol.toLowerCase();
        }
    }

    public void fetchAndSavePrice(String symbol) {
        String id = mapSymbolToId(symbol);
        String url = "https://api.coingecko.com/api/v3/simple/price?ids=" + id + "&vs_currencies=usd";

        try {
            Map<String, Map<String, Object>> response = restTemplate.getForObject(url, Map.class);
            System.out.println("Response CoinGecko pour " + symbol + ": " + response);

            if (response != null && response.containsKey(id)) {
                Object priceObj = response.get(id).get("usd");
                if (priceObj instanceof Number) {
                    BigDecimal price = BigDecimal.valueOf(((Number) priceObj).doubleValue());
                    PriceSnapshot snapshot = new PriceSnapshot();
                    snapshot.setSymbol(symbol.toUpperCase());
                    snapshot.setPrice(price);
                    snapshot.setTimestamp(LocalDateTime.now());
                    repository.save(snapshot);
                    System.out.println("Snapshot saved: " + snapshot);
                } else {
                    System.out.println("Erreur: prix nul ou invalide pour " + symbol);
                }
            } else {
                System.out.println("Erreur: réponse invalide pour " + symbol);
            }
        } catch (RestClientException e) {
            System.out.println("Erreur API CoinGecko pour " + symbol + ": " + e.getMessage());
        }
    }

    @Scheduled(fixedRate = 300000) // toutes les 5 minutes
    public void fetchPricesScheduled() {
        System.out.println("Scheduled fetch triggered...");
        String[] symbols = {"BTC", "ETH", "MATIC"};
        for (String symbol : symbols) {
            fetchAndSavePrice(symbol);
        }
    }
}
