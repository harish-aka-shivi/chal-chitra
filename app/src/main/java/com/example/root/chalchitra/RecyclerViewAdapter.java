package com.example.root.chalchitra;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.zip.Inflater;
/*
* Created by harish.
* */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PlaceViewHolder> {
    private ArrayList<String> mPosterPath;
    private ArrayList<Movie> mMovieArrayList;
    Context mContext;
    public static final String MOVIE_DETAILS = "movie_detail";
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w342";

    public RecyclerViewAdapter(ArrayList<Movie> movieArrayList, Context context) {
        mMovieArrayList = movieArrayList;
        mContext = context;
    }
    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;

        public PlaceViewHolder(View itemView, final
        RecyclerViewAdapter recyclerViewAdapter, final Context mContext) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Movie movie = recyclerViewAdapter.mMovieArrayList.get(position);
                    Gson gson = new Gson();
                    String json = gson.toJson(movie);
                    Bundle bundle = new Bundle();
                    bundle.putString(MOVIE_DETAILS,json);

                    Intent intent = new Intent(mContext,DetailActivity.class);
                    intent.putExtra(MOVIE_DETAILS,json);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        PlaceViewHolder placeViewHolder = new PlaceViewHolder(view,this,mContext);
        return placeViewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {

        //get the imageview
        ImageView imageView = holder.poster;

        //get the movie from movieArrayList
        Movie movie = mMovieArrayList.get(position);
        String imageUrl = movie.getPosterUrl();

        //final poster path with image url appended
        String finalPosterPath = BASE_URL+imageUrl;

        //loading with picasso library
        //Picasso.with(mContext).load(finalPosterPath).into(imageView);
        Glide.with(mContext).load(finalPosterPath).crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(imageView);
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }
}
