package com.example.bbtest.list;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bbtest.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {
    private DataSearcher dataSearcher;
    MutableLiveData<City> cities = new MutableLiveData<>();

    private void readAssetData(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Gson gson = new Gson();
        List<City> list = gson.fromJson(json, new TypeToken<List<City>>(){}.getType());
        dataSearcher = new DataSearcher(list != null ? list : new ArrayList<City>());
    }
}
