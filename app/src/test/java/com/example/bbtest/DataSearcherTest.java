package com.example.bbtest;

import com.example.bbtest.list.searcher.DataSearcher;
import com.example.bbtest.list.searcher.DataSearcherImpl;
import com.example.bbtest.model.City;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DataSearcherTest {
    private DataSearcher dataSearcher;
    private List<City> list;

    @Before
    public void setUp() {
        list = new ArrayList<>();
        list.add(new City("UA", "Hurzuf", 707860, new LatLng(44.549999, 34.283333)));
        list.add(new City("RU", "Novinki", 519188, new LatLng(44.549999, 34.283333)));
        list.add(new City("NP", "Gorkhā", 1283378, new LatLng(44.549999, 34.283333)));
        list.add(new City("IN", "State of Haryāna", 1270260, new LatLng(44.549999, 34.283333)));
        dataSearcher = new DataSearcherImpl(list);
    }

    @Test
    public void basicSearch() {
        List<City> result = dataSearcher.findCities("Hur");
        assertEquals(1, result.size());
        assertEquals("Hurzuf", result.get(0).getName());
        assertEquals("UA", result.get(0).getCountry());
    }

    @Test
    public void searchIsCaseInsensitive() {
        List<City> result1 = dataSearcher.findCities("HUR");
        List<City> result2 = dataSearcher.findCities("HuR");
        List<City> result3 = dataSearcher.findCities("hur");
        assertEquals(result1, result2);
        assertEquals(result2, result3);
        assertEquals(1, result1.size());
    }

    @Test
    public void emptyStringSearch() {
        assertEquals(list, dataSearcher.findCities(""));
    }

    @Test
    public void invalidCharactersSearch() {
        assertEquals(0, dataSearcher.findCities("@#%$%^&*^&*!*(").size());
    }
}