package com.example.root.chalchitra;

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

/**
 * Created by root on 18/6/16.
 */
public class FetchVideosTask extends AsyncTask<String,Void,ArrayList<VideosDetail>> {

    static final String LOG_TAG = "FetchVideosTask";
    private ArrayList<VideosDetail> mVideosDetail;
    @Override
    protected ArrayList<VideosDetail> doInBackground(String... params) {
        mVideosDetail = new ArrayList<>();
        String id = params[0];

        final String BASE_URL = " https://api.themoviedb.org/3/movie/";
        final String BASE_URL_WITH_ID = BASE_URL+id+"/";
        final String VIDEOS = "videos";
        final String VIDEOS_URL_STRING = BASE_URL_WITH_ID + VIDEOS;
        String reviewsJsonString = null;
        HttpURLConnection httpURLConnection;
        BufferedReader bufferedReader;

        try {
            final String API_KEY_PARAM = "api_key";
            Uri reviewsUri = Uri.parse(VIDEOS_URL_STRING).buildUpon().appendQueryParameter
                    (API_KEY_PARAM, BuildConfig.TMDB_API_KEY).build();
            URL reviewsUrl = new URL(reviewsUri.toString());
            httpURLConnection = (HttpURLConnection) reviewsUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();


            InputStream inputStream = httpURLConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            reviewsJsonString = stringBuffer.toString();
            getVideosUrlFromJson(reviewsJsonString);
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

        return mVideosDetail;
    }

    void getVideosUrlFromJson(String reviewsJsonString) throws JSONException {

        //keys to different Json parameters
        final String VIDEOS_DETAIL_RESULTS_ARRAY = "results";
        final String VIDEOS_DETAIL_NAME = "name" ;
        final String VIDEOS_DETAIL_KEY = "key";

        //Values to be strored in ArrayList
        String name;
        String youTubeKey;

        JSONObject jsonObject = new JSONObject(reviewsJsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(VIDEOS_DETAIL_RESULTS_ARRAY);
        int length = jsonArray.length();

        for(int i = 0; i < length; i++) {
            JSONObject videosObject = jsonArray.getJSONObject(i);
            name = videosObject.getString(VIDEOS_DETAIL_NAME);
            youTubeKey = videosObject.getString(VIDEOS_DETAIL_KEY);
            mVideosDetail.add(new VideosDetail(youTubeKey,name));
        }
    }

}
