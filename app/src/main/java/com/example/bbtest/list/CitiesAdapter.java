package com.example.bbtest.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bbtest.R;
import com.example.bbtest.model.City;

import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> {
    private Context context;
    private List<City> items;
    private CityListFragment.CitySelectedCallback callback;

    public CitiesAdapter(@NonNull Context context, @NonNull List<City> items, @Nullable CityListFragment.CitySelectedCallback callback) {
        this.context = context;
        this.items = items;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CitiesAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.CityViewHolder holder, int position) {
        final City city = items.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onCitySelected(city);
                }
            }
        });
        holder.txtTitle.setText(String.format("%s, %s", city.getName(), city.getCountry()));
        holder.txtSubtitle.setText(city.getCoord().toString());
        holder.btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onAboutSelected(city);
            }
        });
    }

    void setItems(List<City> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtSubtitle;
        Button btnAbout;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtSubtitle = itemView.findViewById(R.id.txt_subtitle);
            btnAbout = itemView.findViewById(R.id.btn_about);
        }
    }
}
