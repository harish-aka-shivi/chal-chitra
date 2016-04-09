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
    Context mContext;
    private static final String BASE_URL = "http://image.tmdb.org/t/p/w342";

    public RecyclerViewAdapter(ArrayList<String> posterPath, Context context) {
        mPosterPath = posterPath;
        mContext = context;
    }
    public static class PlaceViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView trialText;
        public PlaceViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            //trialText = (TextView) itemView.findViewById(R.id.trial_text);
        }
    }
    //public void setPosterUrlArraylist(ArrayList<String> posterPath) {
    //    mPosterPath = posterPath;
    //}

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        PlaceViewHolder placeViewHolder = new PlaceViewHolder(view);
        return placeViewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        ImageView imageView = holder.poster;
        String imageUrl = mPosterPath.get(position);
        String finalPosterPath = BASE_URL+imageUrl;
        //imageView.setImageResource(R.mipmap.ic_launcher);
        //String trial = BASE_URL + "/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg";
        Picasso.with(mContext).load(finalPosterPath).into(imageView);
        /*TextView textView  = holder.trialText;
        textView.setText("this is great");*/
    }

    @Override
    public int getItemCount() {
        return mPosterPath.size();
    }
}
