package com.example.bbtest.list;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbtest.R;
import com.example.bbtest.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityListFragment extends Fragment {

    private CityListViewModel viewModel;
    private CitiesAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progress;
    private CitySelectedCallback callback;
    private TextWatcher searchTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            if (s != null) {
                searchCities(s.toString());
            }
        }
    };

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
        search.addTextChangedListener(searchTextWatcher);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchCities(v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void searchCities(String query) {
        viewModel.searchCities(query);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Context context = getContext();
        if (context == null) {
            return;
        }
        viewModel = ViewModelProviders.of(this, new CityListViewModelProvider(context.getAssets())).get(CityListViewModel.class);
        adapter = new CitiesAdapter(getContext(), new ArrayList<City>(), callback);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CitySelectedCallback) {
            callback = (CitySelectedCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CitySelectedCallback");
        }
    }

    public interface CitySelectedCallback {
        void onCitySelected(City city);
        void onAboutSelected(City city);
    }
}
