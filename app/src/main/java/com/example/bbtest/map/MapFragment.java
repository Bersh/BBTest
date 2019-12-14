package com.example.bbtest.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bbtest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment {
    private static final String ARG_PARAM_POINT = "point";
    private static final String ARG_PARAM_NAME = "name";

    private LatLng targetPoint = null;
    private String name = "";

    private MapView mapView;
    private GoogleMap googleMap;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param point LatLng to display
     * @return A new instance of MapFragment.
     */
    public static MapFragment newInstance(@Nullable LatLng point, @Nullable String name) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        if (point != null) {
            args.putParcelable(ARG_PARAM_POINT, point);
        }
        if (name != null) {
            args.putString(ARG_PARAM_NAME, name);
        }
        fragment.setArguments(args);
        return fragment;
    }

    public static MapFragment newInstance() {
        return newInstance(null, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            targetPoint = getArguments().getParcelable(ARG_PARAM_POINT);
            name = getArguments().getString(ARG_PARAM_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.onResume();

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                if (targetPoint != null) {
                    goToTargetPoint();
                }
            }
        });
    }

    public void goToPoint(@NonNull LatLng point, @NonNull String title) {
        targetPoint = point;
        name = title;
        goToTargetPoint();
    }

    private void goToTargetPoint() {
        if (googleMap == null) {
            return;
        }
        googleMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(targetPoint);
        markerOptions.title(name);
        googleMap.addMarker(markerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(targetPoint).zoom(8.0f).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }
}
