package com.ai.blockchain_analytics.controller;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import com.ai.blockchain_analytics.repository.PriceSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @Autowired
    private PriceSnapshotRepository repository;

    @GetMapping
    public List<PriceSnapshot> getPrices() {
        return repository.findAll(); // récupère tous les snapshots depuis la DB
    }
}
