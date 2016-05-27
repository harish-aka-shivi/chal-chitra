package com.example.root.chalchitra;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by root on 16/3/16.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<Movie> movieList;
    private final String POPULAR = "popular";
    private final String TOP_RATED = "top_rated";
    private String PARAM_MOVIE;
    public static final String LOG_TAG = "Main Fragment";
    public static final String MOVIE_DETAILS = "movie_detail";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main,null);

        //get the recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();

        // Initialize the adapter
        mAdapter = new RecyclerViewAdapter(movieList,getContext());

        //set the grid layout
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        // add an adapter
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        //Log.d(LOG_TAG,"this is detail activity trial");
                        Movie movie = mAdapter.movieArrayList.get(position);
                        Gson gson = new Gson();
                        String json = gson.toJson(movie);
                        Bundle bundle = new Bundle();
                        bundle.putString(MOVIE_DETAILS,json);

                        Intent intent = new Intent(getActivity(),DetailActivity.class);
                        intent.putExtra(MOVIE_DETAILS,json);
                        startActivity(intent);
                    }
                }));
        setHasOptionsMenu(true);
        updateMovies();
        return rootView;
    }
    //a1f357995447ec4b6c7c576d5531e90a key

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // find spinner and attach a listener
        Spinner spinner = (Spinner) getActivity().findViewById(R.id.criteria_spinner);
        spinner.setOnItemSelectedListener(this);
        super.onActivityCreated(savedInstanceState);
    }

    public void updateMovies() {
        FetchMoviesTask fetchMoviesTask  = new FetchMoviesTask(getActivity(),new FragmentCallback(){
            // Callback to be called by FetchMoviesTask after loading
            @Override
            public void onTaskDone(ArrayList<Movie> movieArrayList) {
                movieList.clear();
                movieList.addAll(movieArrayList);
                mAdapter.notifyDataSetChanged();
            };});

        fetchMoviesTask.execute(PARAM_MOVIE);
    }

    // callback to be called when update is to be done on fragment
    public interface FragmentCallback{
        void onTaskDone(ArrayList<Movie> movieArrayList);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = (String) parent.getItemAtPosition(position);
        PARAM_MOVIE = choice;
        updateMovies();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        PARAM_MOVIE = POPULAR;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }
}
