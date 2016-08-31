package com.example.owner.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/*
 * This activity should only be run on small devices
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // start detail fragment here
        if (savedInstanceState == null){
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailFragment.MOVIE_KEY,
                    getIntent().getParcelableExtra(DetailFragment.MOVIE_KEY));

            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, detailFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_settings){
            // TODO: launch settings activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
