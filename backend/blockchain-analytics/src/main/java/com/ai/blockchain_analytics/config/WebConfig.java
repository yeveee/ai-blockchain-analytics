package com.ai.blockchain_analytics.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    "http://localhost:3000",
                    "http://localhost:3001",
                    "https://ai-blockchain-analytics.vercel.app",
                    "https://ai-blockchain-analytics-5yub757p9-yevs-projects-1d7b945c.vercel.app",
                    "https://ai-blockchain-analytics-qoxonlng1-yevs-projects-1d7b945c.vercel.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
