package com.example.owner.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 2016-04-21.
 */
public class Movie implements Parcelable {
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
        this.rating = (float) rating;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size){
            return new Movie[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
    }
}
