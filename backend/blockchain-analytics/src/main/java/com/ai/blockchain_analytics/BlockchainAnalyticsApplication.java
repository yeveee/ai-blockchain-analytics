package com.ai.blockchain_analytics;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import com.ai.blockchain_analytics.repository.PriceSnapshotRepository;
@EnableScheduling
@SpringBootApplication
public class BlockchainAnalyticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainAnalyticsApplication.class, args);
	}

}
