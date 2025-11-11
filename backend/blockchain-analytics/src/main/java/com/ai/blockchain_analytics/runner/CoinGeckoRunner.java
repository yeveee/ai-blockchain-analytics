package com.ai.blockchain_analytics.runner;

import com.ai.blockchain_analytics.service.CoinGeckoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
//à chaque lancement de ./mvnw spring-boot:run, les snapshots Bitcoin et Ethereum sont récupérés et stockés automatiquement.
@Component
public class CoinGeckoRunner implements CommandLineRunner {

    private final CoinGeckoService service;

    public CoinGeckoRunner(CoinGeckoService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        // Exemple : récupère Bitcoin et Ethereum
        service.fetchAndSavePrice("bitcoin");
        service.fetchAndSavePrice("ethereum");
    }
}
