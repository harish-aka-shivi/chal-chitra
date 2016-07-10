package com.example.root.chalchitra;

import android.content.Intent;
import android.graphics.Rect;
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
    private static final int VERTICAL_ITEM_SPACE = 20;
    private static final int HORIZONTAL_ITEM_SPACE = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main,null);

        //get the recycler view
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();

        recyclerView.addItemDecoration(new VerticalSpaceDecoration(VERTICAL_ITEM_SPACE));
        recyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(HORIZONTAL_ITEM_SPACE));
        // Initialize the adapter
        mAdapter = new RecyclerViewAdapter(movieList,getContext());

        //set the grid layout
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);

        // add an adapter
        recyclerView.setAdapter(mAdapter);

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

    public class VerticalSpaceDecoration extends RecyclerView.ItemDecoration {
        private final int mVerticalSpaceHeight;

        public VerticalSpaceDecoration(int verticalSpaceHeight) {
            mVerticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;

            //super.getItemOffsets(outRect, view, parent, state);
        }
    }

    /*
   Adding horizontal space in RecyclerView
   */
    public class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int mHorizontalSpaceHeight;

        public HorizontalSpaceItemDecoration(int mHorizontalSpaceHeight) {
            this.mHorizontalSpaceHeight = mHorizontalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.left = mHorizontalSpaceHeight;
            outRect.right = mHorizontalSpaceHeight;
        }

    }
}
