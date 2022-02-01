package com.example.search.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "SEARCH_SERVICE")
public class SearchServiceImpl implements SearchService {
    private final RestTemplate restTemplate;
    private final String url = String.format("http://192.168.0.15:8000/details");

    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<List<Map<String, Map>>> getDetails(List<String> cities) {
        CompletableFuture<List<Map<String, Map>>>[] futures = new CompletableFuture[cities.size()];
        int index = 0;
        for (String city : cities) {
            CompletableFuture<List<Map<String, Map>>> completableFuture = CompletableFuture.supplyAsync(() -> getCityDetails(city));
            futures[index++] = completableFuture;
        }
        List<List<Map<String, Map>>> result = CompletableFuture.allOf(futures).thenApply(v -> {
            return Arrays.asList(futures).stream().map(f -> f.join()).collect(Collectors.toList());
        }).handle((output, exception) -> {
            if (exception != null) {
                log.error(exception.getMessage());
                return null;
            } else {
                return output;
            }
        }).join();
        if (result == null) {
            throw new IllegalArgumentException();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    private List<Map<String, Map>> getCityDetails(String city) {
        int[] cityIds = restTemplate.getForObject(url + "?city=" + city, int[].class);
        CompletableFuture<Map<String, Map>>[] futures = new CompletableFuture[cityIds.length];
        int index = 0;
        for (int cityId : cityIds) {
            CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
                Map detail = restTemplate.getForObject(url + "/" + cityId, Map.class);
                return detail;
            });
            futures[index++] = cf;
        }
        return CompletableFuture.allOf(futures).thenApply(v -> {
            return Arrays.asList(futures).stream().map(f -> f.join()).collect(Collectors.toList());
        }).join();
    }
}
