package com.example.owner.movieapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Owner on 2016-04-21.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context mContext;

    public MovieAdapter(Context context, int resource, int imgViewResourceId, List<Movie> movies) {
        super(context, resource, imgViewResourceId, movies);
        mContext = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            // if it's not recycled, initialize
            imageView = new ImageView(mContext);
            float posterWidth = getContext().getResources().getDimension(R.dimen.poster_width);
            imageView.setLayoutParams(new GridView.LayoutParams((int) posterWidth, (int) posterWidth * 3 / 2));
        } else {
            imageView = (ImageView) convertView;
        }

        // building the url to retrieve the movie thumbnail
        String[] sizeOptions = {"w92", "w154", "w185", "w342", "w500", "w780", "original"};

        final String baseUrl = "http://image.tmdb.org/t/p/";
        String imgSize = sizeOptions[2];
        String posterPath = super.getItem(position).getPosterPath();
        String url = baseUrl + imgSize + posterPath;

        // picasso loads the image into the imageView
        Picasso.with(mContext)
                .load(url)
                .into(imageView);

        return imageView;
    }
}
