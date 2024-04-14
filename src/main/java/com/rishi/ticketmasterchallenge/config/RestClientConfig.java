package com.rishi.ticketmasterchallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${ticketmaster.baseUrl}")
    private String baseUrl;
    @Bean
    protected RestClient getRestClient() {
        return RestClient.create(baseUrl);
    };
}
