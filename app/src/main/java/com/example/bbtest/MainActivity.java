package com.example.bbtest;

import android.content.res.Configuration;
import android.os.Bundle;

import com.example.bbtest.list.CityListFragment;
import com.example.bbtest.map.MapFragment;
import com.example.bbtest.model.City;
import com.google.android.gms.maps.model.LatLng;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements CityListFragment.CitySelectedCallback {
    private static final String MAP_TAG = "MAP";
    private static final String KEY_POINT = "point";
    private static final String KEY_NAME = "name";

    private boolean isLandscapeMode = false;
    private MapFragment mapFragment = null;
    private LatLng targetPoint = null;
    private String name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        isLandscapeMode = findViewById(R.id.details_fragment) != null;

        if (getSupportFragmentManager().findFragmentByTag(MAP_TAG) != null) {
            mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MAP_TAG);
            trans.detach(mapFragment);
            trans.replace(R.id.main_fragment, CityListFragment.newInstance());
        } else if (savedInstanceState == null) {
            trans.replace(R.id.main_fragment, CityListFragment.newInstance());
        }

        if (isLandscapeMode && getSupportFragmentManager().findFragmentById(R.id.details_fragment) == null) {
            mapFragment = MapFragment.newInstance();
            trans.replace(R.id.details_fragment, mapFragment);
            if (savedInstanceState != null && savedInstanceState.containsKey(KEY_POINT)) {
                mapFragment.goToPoint((LatLng) savedInstanceState.getParcelable(KEY_POINT), savedInstanceState.getString(KEY_NAME, ""));
            }
        }

        trans.commit();
    }

    @Override
    public void onCitySelected(City city) {
        targetPoint = city.getCoord();
        name = city.getName();

        if (isLandscapeMode) {
            mapFragment.goToPoint(targetPoint, name);
        } else {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            mapFragment = MapFragment.newInstance(targetPoint, name);
            trans.replace(R.id.main_fragment, mapFragment, MAP_TAG);
            trans.addToBackStack(null);
            trans.commit();
        }
    }

/*
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(!isLandscapeMode && mapFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.detach(mapFragment).commit();
        }
    }
*/

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(targetPoint != null) {
            outState.putParcelable(KEY_POINT, targetPoint);
            outState.putString(KEY_NAME, name);
        }
    }
}
