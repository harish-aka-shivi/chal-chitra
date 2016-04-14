package com.example.root.chalchitra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;
/**
 * Created by root on 6/4/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlaceViewHolder> {
    ArrayList<String> mPosterPath;
    ArrayList<Movie> movieArrayList;
    Context mContext;
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w342";

    public RecyclerViewAdapter(ArrayList<Movie> movieArrayList, Context context) {
        this.movieArrayList = movieArrayList;
        mContext = context;
    }
    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        public PlaceViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
        }
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        PlaceViewHolder placeViewHolder = new PlaceViewHolder(view);
        return placeViewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {

        //get the imageview
        ImageView imageView = holder.poster;

        //get the movie from movieArrayList
        Movie movie = movieArrayList.get(position);
        String imageUrl = movie.getPosterUrl();

        //final poster path with image url appended
        String finalPosterPath = BASE_URL+imageUrl;

        //loading with picasso library
        Picasso.with(mContext).load(finalPosterPath).into(imageView);
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }
}
