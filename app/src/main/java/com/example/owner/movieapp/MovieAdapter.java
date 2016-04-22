package com.example.owner.movieapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Owner on 2016-04-21.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, int resource, int textViewResourceId, List<Movie> movies) {
        super(context, resource, textViewResourceId, movies);
    }
}
