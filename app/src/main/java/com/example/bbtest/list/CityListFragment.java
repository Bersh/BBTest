package com.example.bbtest.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bbtest.R;
import com.example.bbtest.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CityListFragment extends Fragment {

    private CityListViewModel mViewModel;
    private CitiesAdapter adapter;
    private RecyclerView recyclerView;

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<City> readCitiesData() {
        final Context context = getContext();
        if (context == null) {
            return new ArrayList<>();
        }
        String json = null;
        try {
            InputStream is = getContext().getAssets().open("cities.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<City>>() {
        }.getType());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, new CityListViewModelProvider(readCitiesData())).get(CityListViewModel.class);
        adapter = new CitiesAdapter(getContext(), new ArrayList<City>());
        recyclerView.setAdapter(adapter);
        mViewModel.cities.observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                adapter.setItems(cities);
                adapter.notifyDataSetChanged();
            }
        });
    }

}
