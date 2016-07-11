package com.example.root.chalchitra;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState == null) {
            String jsonMovies = getIntent().getStringExtra(MainFragment.MOVIE_DETAILS);
            Bundle bundle = new Bundle();
            bundle.putString(MainFragment.MOVIE_DETAILS,jsonMovies);
            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.movies_detail_container,
                    fragment).commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

   /* @Override
    public boolean onNavigateUp() {
        return super.onNavigateUp();
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }*/
}
