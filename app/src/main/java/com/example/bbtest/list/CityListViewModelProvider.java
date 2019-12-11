package com.example.bbtest.list;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CityListViewModelProvider implements ViewModelProvider.Factory {
    private AssetManager assetsManager;


    public CityListViewModelProvider(@NonNull AssetManager assetsManager) {
        this.assetsManager = assetsManager;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CityListViewModel(assetsManager);
    }
}