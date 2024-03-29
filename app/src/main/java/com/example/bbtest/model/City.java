package com.example.bbtest.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class City implements Comparable {
    private String country;
    private String name;
    @SerializedName("_id")
    private long id;
    private LatLng coord;

    public City(String country, String name, long id, LatLng coord) {
        this.country = country;
        this.name = name;
        this.id = id;
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public LatLng getCoord() {
        return coord;
    }

    public String getCityCountryString() {
        return String.format("%s, %s", name, country);
    }

    @Override
    public int compareTo(Object o) {
        return getCityCountryString().compareTo(((City) o).getCityCountryString());
    }
}
