package com.example.root.chalchitra;

import android.renderscript.Short4;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by root on 9/4/16.
 */
public class Movie implements Serializable{
    String title;
    String date;
    String voteAverage;
    String plot;
    String posterUrl;

    public Movie(){}

    public Movie(String title, String date, String voteAverage, String plot, String posterUrl) {
        this.title = title;
        this.date = date;
        this.voteAverage = voteAverage;
        this.plot = plot;
        this.posterUrl = posterUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getDate() {
        return date;
    }

    public String getPlot() {
        return plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }
}

