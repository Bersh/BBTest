package com.example.bbtest.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bbtest.R;
import com.example.bbtest.model.City;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CityViewHolder> {
    private Context context;
    private List<City> items;

    public CitiesAdapter(Context context, List<City> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public CitiesAdapter.CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.CityViewHolder holder, int position) {
        City city = items.get(position);
        holder.txtTitle.setText(String.format("%s, %s", city.getName(),city.getCountry()));
        holder.txtSubtitle.setText(city.getCoord().toString());
        holder.btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
    }

    public void setItems(List<City> items) {
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
