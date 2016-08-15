package com.example.owner.movieapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    // update for horizontal tablet
    public void updateDetail(Movie movie){
        FragmentActivity fragmentActivity= getActivity();

        ImageView posterIv = (ImageView) fragmentActivity.findViewById(R.id.detail_poster);
        TextView titleTv = (TextView) fragmentActivity.findViewById(R.id.detail_title);
        TextView ratingTv = (TextView) fragmentActivity.findViewById(R.id.detail_rating);
        TextView releaseTv = (TextView) fragmentActivity.findViewById(R.id.detail_release);
        TextView synopsisTv = (TextView) fragmentActivity.findViewById(R.id.detail_synopsis);

        // TODO: update poster imageview
        titleTv.setText(movie.getTitle());
        ratingTv.setText(movie.getRating() + "/10");
        releaseTv.setText(movie.getReleaseDate());
        synopsisTv.setText(movie.getSynopsis());
    }
}
