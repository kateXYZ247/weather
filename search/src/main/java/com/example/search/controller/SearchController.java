package com.example.search.controller;

import com.example.search.entity.SearchDetailsRequestBody;
import com.example.search.entity.SearchDetailsResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@Slf4j(topic = "SEARCH_CONTROLLER")
public class SearchController {
    private final RestTemplate restTemplate;
    private final String url = String.format("http://192.168.0.15:8000/details");

    public SearchController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value="/weather/search", consumes="application/json")
    public ResponseEntity<SearchDetailsResponseBody> getDetails(@RequestBody SearchDetailsRequestBody searchDetailsRequestBody) {
        //TODO

        List<String> cities = searchDetailsRequestBody.getCities();
        CompletableFuture<List<Map<String, Map>>>[] futures = new CompletableFuture[cities.size()];
        int index = 0;
        for (String city : cities) {
            CompletableFuture<List<Map<String, Map>>> completableFuture = CompletableFuture.supplyAsync(() -> getCityDetails(city));
            futures[index++] = completableFuture;
        }
        SearchDetailsResponseBody response = new SearchDetailsResponseBody();
        List<List<Map<String, Map>>> result = CompletableFuture.allOf(futures).thenApply(v -> {
            return Arrays.asList(futures).stream().map(f -> f.join()).collect(Collectors.toList());
        }).join();
        response.setSearchResults(result);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
