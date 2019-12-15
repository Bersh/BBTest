package com.example.bbtest.list.searcher;

import androidx.annotation.WorkerThread;

import com.example.bbtest.model.City;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DataSearcherImpl implements DataSearcher {
    private Map<String, Queue<City>> data = new HashMap<>();

    @WorkerThread
    public DataSearcherImpl(List<City> cities) {
        Queue<City> fullList = new PriorityQueue<>();
        StringBuilder sb = new StringBuilder();
        for (City city : cities) {
            fullList.add(city);
            String name = city.getName().toLowerCase();
            sb.setLength(0);
            for (Character c : name.toCharArray()) {
                sb.append(c);
                Queue<City> queue = data.containsKey(sb.toString()) ? data.get(sb.toString()) : new PriorityQueue<City>();
                queue.add(city);
                data.put(sb.toString(), queue);
            }
        }
        data.put("", fullList);
    }

    public List<City> findCities(String query) {
        query = query.toLowerCase();
        final Queue<City> result = data.get(query);
        return result != null ? new ArrayList<>(result) : new ArrayList<City>();
    }
}
