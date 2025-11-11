package com.ai.blockchain_analytics.controller;

import com.ai.blockchain_analytics.model.PriceSnapshot;
import com.ai.blockchain_analytics.repository.PriceSnapshotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;

@WebMvcTest(PriceController.class)
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc; // simule les requêtes HTTP

    @MockBean
    private PriceSnapshotRepository repository; // mock du repo pour ne pas toucher à la base

    @Test
    public void testGetPrices() throws Exception {
        PriceSnapshot snapshot = new PriceSnapshot();
        snapshot.setId(1L);
        snapshot.setSymbol("BTC");
        snapshot.setPrice(BigDecimal.valueOf(60000));
        snapshot.setTimestamp(LocalDateTime.now());

        given(repository.findAll()).willReturn(List.of(snapshot)); // définit ce que le mock doit retourner

        mockMvc.perform(get("/api/prices/current"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("BTC"))
                .andExpect(jsonPath("$[0].price").value(60000));
    }
}
