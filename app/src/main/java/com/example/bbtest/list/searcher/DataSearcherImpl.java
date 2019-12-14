package com.example.bbtest.list.searcher;

import com.example.bbtest.model.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSearcherImpl implements DataSearcher {
    private Map<String, List<City>> data = new HashMap<>();

    public DataSearcherImpl(List<City> cities) {
        List<City> allList = new ArrayList<>();
        data.put("", allList);
        for (City city : cities) {
            allList.add(city);
            String name = city.getName().toLowerCase();
            StringBuilder sb = new StringBuilder();
            for (Character c : name.toCharArray()) {
                sb.append(c);
                List<City> list = data.containsKey(sb.toString()) ? data.get(sb.toString()) : new ArrayList<City>();
                list.add(city);
                data.put(sb.toString(), list);
            }
        }
    }

    public List<City> findCities(String query) {
        query = query.toLowerCase();
        return data.containsKey(query) ? data.get(query) : new ArrayList<City>();
    }
}
