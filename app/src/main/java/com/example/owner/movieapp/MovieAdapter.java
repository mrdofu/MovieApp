package com.example.owner.movieapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Owner on 2016-04-21.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;

    public MovieAdapter(Context context, int resource, int textViewResourceId, List<Movie> movies) {
        super(context, resource, textViewResourceId, movies);
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageView;

        if (convertView==null){
            // if it's not recycled, initialize
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) convertView;
        }

        String url = ;
        Picasso.with(mContext)
                .load(url)
                .into(imageView);
    }
}
