package com.example.root.chalchitra;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15/4/16.
 */
public class FetchReviewsTask extends AsyncTask<String,Void,ArrayList<Review>>{
    static final String LOG_TAG = "FetchVideosAndReviews";
    private ArrayList<Review> mReviewList;
    private DetailFragment.FragmentCallback mFragmentCallback;
    private Context mContext;
    public FetchReviewsTask(Context context,DetailFragment.FragmentCallback fragmentCallback) {
        mContext = context;
        mFragmentCallback = fragmentCallback;
    }

    @Override
    protected ArrayList<Review> doInBackground(String... params) {
        mReviewList = new ArrayList<>();
        String id = params[0];

        final String BASE_URL = " https://api.themoviedb.org/3/movie/";
        //final String  = id;
        final String BASE_URL_WITH_ID = BASE_URL+id+"/";
        final String REVIEWS = "reviews";
        final String VIDEOS = "videos";
        final String REVIEWS_URL_STRING = BASE_URL_WITH_ID + REVIEWS;
        final String VIDEOS_URL_STRING = BASE_URL_WITH_ID + VIDEOS;
        String reviewsJsonString = null;

        HttpURLConnection httpURLConnection;
        BufferedReader bufferedReader;
        try {
            final String API_KEY_PARAM = "api_key";
            Uri reviewsUri = Uri.parse(REVIEWS_URL_STRING).buildUpon().appendQueryParameter
                    (API_KEY_PARAM,BuildConfig.TMDB_API_KEY).build();
            URL reviewsUrl = new URL(reviewsUri.toString());
            httpURLConnection = (HttpURLConnection) reviewsUrl.openConnection();
            httpURLConnection.setRequestMethod("Get");
            httpURLConnection.connect();

            InputStream inputStream = httpURLConnection.getInputStream();
            if(inputStream == null) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine())  != null) {
                stringBuffer.append(line + "\n");
            }
            reviewsJsonString = stringBuffer.toString();
            getReviewsFromJson(reviewsJsonString);
            Uri videosUri = Uri.parse(VIDEOS_URL_STRING).buildUpon().appendQueryParameter
                    (API_KEY_PARAM,BuildConfig.TMDB_API_KEY).build();
            URL videosUrl = new URL(videosUri.toString());
            httpURLConnection = (HttpURLConnection) videosUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
        }

        catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.toString());
        }

        catch (IOException e) {
            Log.e(LOG_TAG, e.toString());
        }

        catch (JSONException e) {
            Log.e(LOG_TAG, e.toString());
        }

        return mReviewList;
    }

    public void getReviewsFromJson(String reviewsJsonString) throws JSONException {

        //keys to different JSON parameters
        final String TMDB_TOTAL_RESULTS = "total_results";
        final String TMDB_TOTAL_PAGES = "total_pages";
        final String TMDB_RESULTS_ARRAY = "results";
        final String TMDB_AUTHOR = "author";
        final String TMDB_CONTENT = "content";

        //parameters to be parsed
        int totalPages;
        String content;
        String author;
        int totalResults;


        try {
            JSONObject reviewsJson = new JSONObject(reviewsJsonString);
            JSONArray resultsArray = reviewsJson.getJSONArray(TMDB_RESULTS_ARRAY);
            totalPages = reviewsJson.getInt(TMDB_TOTAL_PAGES);
            totalResults = reviewsJson.getInt(TMDB_TOTAL_RESULTS);

            for (int i = 0; i < totalResults; i++) {
                JSONObject reviewObject = resultsArray.getJSONObject(i);
                author = reviewObject.getString(TMDB_AUTHOR);
                content = reviewObject.getString(TMDB_CONTENT);
                mReviewList.add(new Review(author,content));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG,e.toString());
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        super.onPostExecute(reviews);

    }
}

