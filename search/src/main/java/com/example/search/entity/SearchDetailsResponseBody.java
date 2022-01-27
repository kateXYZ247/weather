package com.example.search.entity;

import java.util.List;
import java.util.Map;

public class SearchDetailsResponseBody {
    private List<List<Map<String, Map>>> searchResults;

    public List<List<Map<String, Map>>> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<List<Map<String, Map>>> searchResults) {
        this.searchResults = searchResults;
    }
}
