package com.example.owner.movieapp;

/**
 * Created by Owner on 2016-04-21.
 */
public class Movie {
    private String posterPath;
    private String title;
    private String synopsis;
    private float rating;
    private String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public float getRating() {
        return rating;
    }

    public Movie(String title, String posterPath, String synopsis, double rating, String releaseDate) {
        this.title = title;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.rating = (float)rating;
        this.releaseDate = releaseDate;
    }
}
