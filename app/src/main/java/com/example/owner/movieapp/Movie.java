package com.example.owner.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Owner on 2016-04-21.
 */
public class Movie implements Parcelable {
    private String title;
    private String posterPath;
    private String synopsis;
    private double rating;
    private String releaseDate;

    public String getTitle() {
        return title;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public String getSynopsis() {
        return synopsis;
    }
    public double getRating() {
        return rating;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie(String title, String posterPath, String synopsis, double rating, String releaseDate) {
        this.title = title;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    /*
     * The below is for parcelable
     */
    private Movie(Parcel in) {
        title = in.readString();
        posterPath = in.readString();
        synopsis = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
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
        out.writeString(title);
        out.writeString(posterPath);
        out.writeString(synopsis);
        out.writeDouble(rating);
        out.writeString(releaseDate);
    }
}
