package com.example.bbtest.list;

import com.example.bbtest.model.City;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CityListViewModel extends ViewModel {
    private DataSearcher dataSearcher;
    MutableLiveData<List<City>> cities = new MutableLiveData<>();

    public CityListViewModel(@NonNull List<City> citiesList) {
        dataSearcher = new DataSearcher(citiesList);
        cities.setValue(dataSearcher.findCities(""));
    }

    public void searchCities(String searchQuery) {
        cities.setValue(dataSearcher.findCities(searchQuery));
    }
}
