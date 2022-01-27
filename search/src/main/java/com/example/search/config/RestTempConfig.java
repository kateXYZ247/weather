package com.example.search.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTempConfig {
    // @Bean is an annotation used on our method level
    // when our spring boot scan during our runtime, it will start scanning all files in the restTemplate
    // and store results in the application context at runtime

    // this is how we manage third party library instance or use extend as well
    @Bean
    public RestTemplate getRestTemplate() {
        // we return new instance instead of class
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(5)).build();
    }
}