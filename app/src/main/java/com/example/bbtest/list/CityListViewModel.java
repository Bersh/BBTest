package com.example.bbtest.list;

import android.content.res.AssetManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bbtest.util.LatLngDeserializer;
import com.example.bbtest.list.searcher.DataSearcher;
import com.example.bbtest.list.searcher.DataSearcherImpl;
import com.example.bbtest.model.City;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CityListViewModel extends ViewModel {
    @NonNull
    MutableLiveData<List<City>> cities = new MutableLiveData<>();
    @NonNull
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private DataSearcher dataSearcher = null;

    CityListViewModel(@Nullable final AssetManager assetsManager) {
        isLoading.setValue(true);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                dataSearcher = new DataSearcherImpl(readCitiesData(assetsManager));
                cities.postValue(dataSearcher.findCities(""));
                isLoading.postValue(false);
            }
        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private List<City> readCitiesData(@Nullable AssetManager assetManager) {
        if (assetManager == null) {
            return new ArrayList<>();
        }
        String json = null;
        InputStream is = null;
        try {
            is = assetManager.open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            json = new String(buffer, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LatLng.class, new LatLngDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, new TypeToken<List<City>>() {
        }.getType());
    }

    void searchCities(String searchQuery) {
        if (dataSearcher != null && !isLoading.getValue()) {
            cities.setValue(dataSearcher.findCities(searchQuery));
        }
    }

    protected void onCleared() {
        executorService.shutdownNow();
        executorService = null;
    }
}
