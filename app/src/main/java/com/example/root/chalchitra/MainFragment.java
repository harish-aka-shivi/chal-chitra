package com.example.root.chalchitra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by root on 16/3/16.
 */
public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<String> movieList;
    private final String POPULAR = "popular";
    private final String TOP_RATED = "top_rated";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main,null);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        //movieList.add("/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg");
        //movieList.add("/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg");
        mAdapter = new RecyclerViewAdapter(movieList,getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        updateMovies();
        return rootView;
    }
    //a1f357995447ec4b6c7c576d5531e90a key

    public void updateMovies() {
        FetchMoviesTask fetchMoviesTask  = new FetchMoviesTask(getActivity(),new FragmentCallback(){
            @Override
            public void onTaskDone(ArrayList<String> strings) {
                //setPosterUrlArraylist(strings);
                //movieList.clear();
                movieList.addAll(strings);
                mAdapter.notifyDataSetChanged();

            };});
        fetchMoviesTask.execute(POPULAR);
    }
    /*public void setPosterUrlArraylist(ArrayList<String> posterPath) {
        mAdapter.setPosterUrlArraylist(posterPath);
    }*/

    // callback to be called when update is to be done on fragment
    public interface FragmentCallback{
        void onTaskDone(ArrayList<String> strings);
    }
}
