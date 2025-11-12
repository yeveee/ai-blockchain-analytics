package com.ai.blockchain_analytics.controller;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import com.ai.blockchain_analytics.repository.PriceSnapshotRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://ai-blockchain-analytics.vercel.app",
    "https://ai-blockchain-analytics-5yub757p9-yevs-projects-1d7b945c.vercel.app"
})
@RestController
@RequestMapping("/api/prices")
public class PriceSnapshotController {

    private final PriceSnapshotRepository repository;

    public PriceSnapshotController(PriceSnapshotRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/snapshots")
public List<Map<String, Object>> getAllPrices() {
    return repository.findAll().stream()
            .map(snapshot -> {
                Map<String, Object> map = new HashMap<>();
                map.put("symbol", snapshot.getSymbol());
                map.put("timestamp", snapshot.getTimestamp().toString());
                map.put("price", snapshot.getPrice());
                return map;
            })
            .collect(Collectors.toList());
}

}
