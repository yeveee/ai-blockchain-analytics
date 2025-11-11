package com.ai.blockchain_analytics.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

//cree une table price_snapshot dans PostgreSQL avec ces colonnes
@Entity
public class PriceSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private BigDecimal price;

    private LocalDateTime timestamp;

    // ----- Getters et Setters -----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
public String toString() {
    return "PriceSnapshot{" +
            "id=" + id +
            ", symbol='" + symbol + '\'' +
            ", price=" + price +
            ", timestamp=" + timestamp +
            '}';
}

}

