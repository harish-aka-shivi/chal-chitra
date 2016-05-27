package com.example.root.chalchitra;

/**
 * Created by root on 21/4/16.
 */
public class Review {
    String content;
    String author;
    String httpUrl;

    public Review(String author,String content) {
        this.author = author;
        this.content = content;
    }

    public Review() {}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public String getHttpUrl() {
        return httpUrl;
    }
}
