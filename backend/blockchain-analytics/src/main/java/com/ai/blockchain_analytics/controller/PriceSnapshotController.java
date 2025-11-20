package com.ai.blockchain_analytics.controller;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import com.ai.blockchain_analytics.repository.PriceSnapshotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller for cryptocurrency price data.
 * Provides endpoints to retrieve price snapshots from the database.
 */
@RestController
@RequestMapping("/api/prices")
public class PriceSnapshotController {

    private static final Logger logger = LoggerFactory.getLogger(PriceSnapshotController.class);
    private final PriceSnapshotRepository repository;

    public PriceSnapshotController(PriceSnapshotRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieves all price snapshots from the database.
     * Each snapshot contains the cryptocurrency symbol, timestamp, and price in USD.
     * 
     * @return list of price snapshot maps or error response
     */
    @GetMapping("/snapshots")
    public ResponseEntity<List<Map<String, Object>>> getAllPriceSnapshots() {
        try {
            logger.debug("Fetching all price snapshots");
            List<Map<String, Object>> snapshots = repository.findAll().stream()
                    .map(this::convertToMap)
                    .collect(Collectors.toList());
            
            logger.info("Successfully retrieved {} price snapshots", snapshots.size());
            return ResponseEntity.ok(snapshots);
        } catch (Exception e) {
            logger.error("Error fetching price snapshots", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Converts a PriceSnapshot entity to a map representation.
     * 
     * @param snapshot the price snapshot entity
     * @return map containing snapshot data
     */
    private Map<String, Object> convertToMap(PriceSnapshot snapshot) {
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", snapshot.getSymbol());
        map.put("timestamp", snapshot.getTimestamp().toString());
        map.put("price", snapshot.getPrice());
        return map;
    }
}
