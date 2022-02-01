package com.example.search.controller;

import com.example.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Map;


@RestController
@Slf4j(topic = "SEARCH_CONTROLLER")
public class SearchController {
    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }


    @GetMapping(value="/weather/search", consumes="application/json")
    public ResponseEntity<?> getDetails(@RequestParam List<String> cities) {
        //TODO
        return new ResponseEntity<List<List<Map<String, Map>>>> (searchService.getDetails(cities), HttpStatus.OK);
    }
}
