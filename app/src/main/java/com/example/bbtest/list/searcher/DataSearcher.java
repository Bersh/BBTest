package com.example.bbtest.list.searcher;

import com.example.bbtest.model.City;

import java.util.List;

public interface DataSearcher {
    List<City> findCities(String query);
}
