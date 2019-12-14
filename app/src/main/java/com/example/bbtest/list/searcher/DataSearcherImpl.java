package com.example.bbtest.list.searcher;

import com.example.bbtest.model.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DataSearcherImpl implements DataSearcher {
    private Map<String, Queue<City>> data = new HashMap<>();

    public DataSearcherImpl(List<City> cities) {
        Queue<City> allList = new PriorityQueue<>();
        data.put("", allList);
        for (City city : cities) {
            allList.add(city);
            String name = city.getName().toLowerCase();
            StringBuilder sb = new StringBuilder();
            for (Character c : name.toCharArray()) {
                sb.append(c);
                Queue<City> queue = data.containsKey(sb.toString()) ? data.get(sb.toString()) : new PriorityQueue<City>();
                queue.add(city);
                data.put(sb.toString(), queue);
            }
        }
    }

    public List<City> findCities(String query) {
        query = query.toLowerCase();
        return data.get(query) != null ? new ArrayList<>(data.get(query)) : new ArrayList<City>();
    }
}
