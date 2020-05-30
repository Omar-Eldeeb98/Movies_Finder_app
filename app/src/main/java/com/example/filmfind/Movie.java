package com.example.filmfind;

import java.io.Serializable;

public class Movie implements Serializable {

    private static final long id=1L;


    private String imageUrl;
    private String title;
    private String year;
    private String type;
    private String imdbID;

    public Movie(String imageUrl, String title, String year, String type,String imdbID) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.year = year;
        this.type = type;
        this.imdbID = imdbID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
}
