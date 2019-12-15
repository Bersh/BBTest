package com.example.bbtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bbtest.about.AboutActivity;
import com.example.bbtest.list.CityListFragment;
import com.example.bbtest.map.MapFragment;
import com.example.bbtest.model.City;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements CityListFragment.CityItemCallback {
    private static final String MAP_TAG = "MAP";
    private static final String LIST_TAG = "LIST";
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

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        isLandscapeMode = findViewById(R.id.details_fragment) != null;

        final Fragment oldMap = fm.findFragmentByTag(MAP_TAG);
        if (oldMap != null && isLandscapeMode) {
            //map has already been added in portrait mode
            trans.remove(oldMap);
            fm.popBackStack();
        } else if (savedInstanceState == null) {
            //app is just started
            trans.replace(R.id.main_fragment, CityListFragment.newInstance(), LIST_TAG);
        }

        if (isLandscapeMode) {
            mapFragment = MapFragment.newInstance();
            trans.replace(R.id.details_fragment, mapFragment);
        }

        trans.commit();
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (targetPoint != null && !isLandscapeMode) {
            targetPoint = null;
        }
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

    @Override
    public void onAboutSelected(City city) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState == null) {
            return;
        }
        final LatLng savedPoint = savedInstanceState.getParcelable(KEY_POINT);
        if (savedPoint != null && mapFragment != null) {
            mapFragment.goToPoint(savedPoint, savedInstanceState.getString(KEY_NAME, ""));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (targetPoint != null) {
            outState.putParcelable(KEY_POINT, targetPoint);
            outState.putString(KEY_NAME, name);
        }
    }
}
