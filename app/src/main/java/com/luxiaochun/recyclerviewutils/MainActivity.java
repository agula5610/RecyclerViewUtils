package com.luxiaochun.recyclerviewutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerViewFragment fragment = RecyclerViewFragment.newInstance("");
        ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), fragment, R.id.fragment_container);
    }
}
