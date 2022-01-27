package com.example.search.entity;

import java.util.List;

public class SearchDetailsRequestBody {
    private List<String> cities;

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }
}
