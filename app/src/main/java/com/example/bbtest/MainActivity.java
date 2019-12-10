package com.example.bbtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.bbtest.list.CityListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.content, CityListFragment.newInstance());
        trans.commit();
    }
}
