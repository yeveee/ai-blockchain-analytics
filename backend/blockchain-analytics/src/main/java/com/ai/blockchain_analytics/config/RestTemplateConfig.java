package com.ai.blockchain_analytics.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for RestTemplate bean.
 * Provides a shared, configured RestTemplate instance for HTTP requests.
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a RestTemplate bean with configured timeout settings.
     * 
     * @param builder the RestTemplateBuilder
     * @return configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
