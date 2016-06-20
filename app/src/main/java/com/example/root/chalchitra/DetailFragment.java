package com.example.root.chalchitra;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/w342";
    Movie mMovie;
    ArrayList<Review> reviewArrayList;

    public DetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate fragment and get the arguments
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Bundle bundle = getArguments();
        String moviesJson = bundle.getString(MainFragment.MOVIE_DETAILS);

        //Extract movie and its fields
        Gson gson = new Gson();
        mMovie = gson.fromJson(moviesJson,Movie.class);
        String plot = mMovie.getPlot();
        String rating = mMovie.getVoteAverage();
        String release = mMovie.getDate();
        String posterUrl = mMovie.getPosterUrl();
        String id = mMovie.getId();
        String finalUrl = BASE_URL+posterUrl;

        //Get all the views
        ImageView posterView = (ImageView) rootView.findViewById(R.id.poster_View);
        TextView releaseView = (TextView)  rootView.findViewById(R.id.release_view);
        TextView ratingView = (TextView)  rootView.findViewById(R.id.rating_view);
        TextView plot_View = (TextView) rootView.findViewById(R.id.plot_view);
        final LinearLayout youTubeLinksView = (LinearLayout) rootView.findViewById(R.id.youTube_links_layout);

        //set content to the views
        Picasso.with(getContext()).load(finalUrl).into(posterView);
        releaseView.setText(release);
        ratingView.setText(rating);
        plot_View.setText(plot);

        //Callback for showing Reviews
        new FetchReviewsTask(getContext(), new FragmentCallback() {
            @Override
            public void onTaskDone(ArrayList<Review> reviews) {
                String reviewsString = "";
                Iterator<Review> iterator = reviews.iterator();
                while (iterator.hasNext()) {
                    reviewsString = reviewsString + iterator.next().getContent();
                    reviewsString = reviewsString + "\n";
                }
                TextView reviewsView = (TextView) rootView.findViewById(R.id.reviews_view);
                reviewsView.setText(reviewsString);
            }
        }).execute(id);

        //AsyncTask and callback  for displaying youtube buttons.
        new FetchVideosTask(getContext(), new YouTubeFragmentCallback() {
            @Override
            public void onTaskDone(ArrayList<YouTubeLink> youTubeLinks) {
                Iterator<YouTubeLink> iterator = youTubeLinks.iterator();
                while (iterator.hasNext()) {
                    String buttonText = iterator.next().getName();
                    Button button = new Button(getActivity());
                    button.setText(buttonText);
                    youTubeLinksView.addView(button);
                }
            }
        }).execute(id);
        return rootView;
    }

    public void setReviewArrayList(ArrayList<Review> arrayList) {
        reviewArrayList = arrayList;
    }

    //define callback interface for fetching reviews
    public interface FragmentCallback {
        void onTaskDone (ArrayList<Review> reviews);
    }

    //Define callback interface for fetching youtube links.
    public interface YouTubeFragmentCallback {
        void onTaskDone (ArrayList<YouTubeLink> youTubeLinks);
    }
}
