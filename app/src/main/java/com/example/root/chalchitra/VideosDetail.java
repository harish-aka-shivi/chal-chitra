package com.example.root.chalchitra;

/**
 * Created by root on 20/6/16.
 */
public class VideosDetail {
    String youTubeKey;
    String name;

    public VideosDetail(String key, String name) {
        this.youTubeKey = key;
        this.name = name;
    }

    public void setKey(String key) {
        this.youTubeKey = key;
    }

    public String getKey() {
        return youTubeKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
