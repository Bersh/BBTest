package com.example.bbtest.list;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bbtest.R;
import com.example.bbtest.model.City;

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

    private CityListViewModel viewModel;
    private CitiesAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progress;

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progress = view.findViewById(R.id.progress);

        final EditText search = view.findViewById(R.id.edit_search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchCities(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Context context = getContext();
        if (context == null) {
            return;
        }
        viewModel = ViewModelProviders.of(this, new CityListViewModelProvider(context.getAssets())).get(CityListViewModel.class);
        adapter = new CitiesAdapter(getContext(), new ArrayList<City>());
        recyclerView.setAdapter(adapter);
        viewModel.cities.observe(this, new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                adapter.setItems(cities);
                adapter.notifyDataSetChanged();
            }
        });

        if (viewModel.cities.getValue() != null) {
            adapter.setItems(viewModel.cities.getValue());
            adapter.notifyDataSetChanged();
        }

        viewModel.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
            }
        });
    }

}
