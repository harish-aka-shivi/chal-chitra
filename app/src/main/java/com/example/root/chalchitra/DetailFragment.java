package com.example.root.chalchitra;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {
    Movie mMovie;
    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle bundle = getArguments();
        String moviesJson = bundle.getString(MainFragment.MOVIE_DETAILS);
        Gson gson = new Gson();
        mMovie = gson.fromJson(moviesJson,Movie.class);
        String string = mMovie.getPlot();
        //TextView textView = (TextView) rootView.findViewById(R.id.textView);
        //textView.setText(string);
        return rootView;
    }
}
