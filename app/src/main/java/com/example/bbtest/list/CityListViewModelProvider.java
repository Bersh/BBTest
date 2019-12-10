package com.example.bbtest.list;

import android.app.Application;

import com.example.bbtest.model.City;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CityListViewModelProvider implements ViewModelProvider.Factory {
    private List<City> citiesList;


    public CityListViewModelProvider(@NonNull List<City> citiesList) {
        this.citiesList = citiesList;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CityListViewModel(citiesList);
    }
}