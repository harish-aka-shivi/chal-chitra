package com.example.root.chalchitra;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SecureCacheResponse;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by root on 17/3/16.
 */
public class FetchMoviesTask extends AsyncTask<String,Void,ArrayList<String>> {
    static final String LOG_TAG = "Fetch_Movies_Task";
    static String API_KEY = "a1f357995447ec4b6c7c576d5531e90a";
    String jsonMoviesString;
    Context mContext;
    MainFragment.FragmentCallback mFragmentCallback;
    ArrayList<String> posterUrl = new ArrayList<String>();

    public FetchMoviesTask(Context context, MainFragment.FragmentCallback fragmentCallback) {
        mContext = context;
        mFragmentCallback = fragmentCallback;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String sortCriteria = params[0];
        int initialPage = 1;

        // for loop to load two pages
        for (int j = initialPage;j<initialPage+2;j++){
            try {
                final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/" + sortCriteria +"?";
                final String API_KEY_PARAM = "api_key";
                final String PAGE_PARAM = "page";

                //build uri
                Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon().appendQueryParameter(API_KEY_PARAM,API_KEY)
                        .appendQueryParameter(PAGE_PARAM,String.valueOf(j)).build();
                URL url = new URL(builtUri.toString());

                //get connection
                httpURLConnection = (HttpURLConnection)  url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer  = new StringBuffer();

                // if stream is null do nothing
                if(inputStream == null) {
                    return null;
                }
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line =bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }
                if(stringBuffer.length() == 0) {
                    return null;
                }

                jsonMoviesString = stringBuffer.toString();
                final String TMDB_result_key = "results";
                final String poster_path_key = "poster_path";
                JSONObject moviesJson = new JSONObject(jsonMoviesString);
                JSONArray moviesArray = moviesJson.getJSONArray(TMDB_result_key);
                int len = moviesArray.length();

                // store all image urls in an ArrayList
                for (int i=0;i< len;i++) {
                    JSONObject movieInstance = moviesArray.getJSONObject(i);
                    String posterPath = movieInstance.getString(poster_path_key);
                    posterUrl.add(posterPath);
                }
            }
            catch (java.io.IOException e){
                Log.e(LOG_TAG,e.toString());
            }
            catch (org.json.JSONException e) {
                Log.e(LOG_TAG, e.toString());
            }

            //close the connections
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    }
                    catch (IOException e) {
                        Log.e(LOG_TAG,e.toString());
                    }
                }
            }
            }

            /*final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/" + sortCriteria +"?";
            final String api_key = "api_key";

            //build uri
            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon().appendQueryParameter(api_key,API_KEY).build();
            URL url = new URL(builtUri.toString());

            //get connection
            httpURLConnection = (HttpURLConnection)  url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer stringBuffer  = new StringBuffer();

            // if stream is null do nothing
            if(inputStream == null) {
                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line =bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\n");
            }
            if(stringBuffer.length() == 0) {
                return null;
            }

            jsonMoviesString = stringBuffer.toString();
            final String TMDB_result_key = "results";
            final String poster_path_key = "poster_path";
            JSONObject moviesJson = new JSONObject(jsonMoviesString);
            JSONArray moviesArray = moviesJson.getJSONArray(TMDB_result_key);
            int len = moviesArray.length();
            for (int i=0;i< len;i++) {
                JSONObject movieInstance = moviesArray.getJSONObject(i);
                String posterPath = movieInstance.getString(poster_path_key);
                posterUrl.add(posterPath);
            }
        }
        catch (java.io.IOException e){
            Log.e("Chalchitra",e.toString());
        }
        catch (org.json.JSONException e) {
            Log.e("Chalchitra", e.toString());
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException e) {
                    Log.e(LOG_TAG,e.toString());
                }
            }
        }*/
        return posterUrl;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        //RecyclerViewAdapter.setPosterUrlArraylist(strings);
        mFragmentCallback.onTaskDone(strings);
    }
}

