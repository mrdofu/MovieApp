package com.example.owner.movieapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 */
public class MovieFragment extends Fragment {

    private static final String TAG = "MovieFragment";
    private MovieAdapter mMovieAdapter;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_sort_by_hot) {
            updateMovies(getString(R.string.pref_sort_hot));
            return true;
        } else if (id == R.id.action_sort_by_rating) {
            updateMovies(getString(R.string.pref_sort_rating));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<Movie> dummyList = new ArrayList<>();

        mMovieAdapter = new MovieAdapter(
                getActivity(),
                R.layout.list_item_movie,
                R.id.list_item_movie_imageview,
                dummyList);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        // attach the adapter to the gridview
        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(mMovieAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: launch detail fragment from here
                Movie movie = mMovieAdapter.getItem(position);
                Intent intent = new Intent(getActivity(),DetailFragment.class)
                        .putExtra("Movie", movie);
            }
        });
        return rootView;
    }

    /**
     * Updates the movie grid with the new sort method
     *
     * @param sortParam resource string indicating which tmdb api endpoint to request
     */
    private void updateMovies(String sortParam) {
        if (sortParam.length() <= 0) {
            Log.w(TAG, "updateMovies: sortParam string empty");
        } else {
            FetchMovieTask movieTask = new FetchMovieTask();
            movieTask.execute(sortParam);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies(getString(R.string.pref_sort_hot));
    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        /**
         * Get all the movie data for one page of tMDB data. Each page has max 20 movies
         */
        private Movie[] getMovieDataFromJson(String moviesJsonString)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS = "results";
            final String TMDB_TITLE = "title";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_PLOT = "overview";
            final String TMDB_RATING = "vote_average";
            final String TMDB_RELEASE_DATE = "release_date";

            JSONObject moviesJson = new JSONObject(moviesJsonString);
            JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);
            int numMovies = moviesArray.length();

            Movie[] resultMovies = new Movie[numMovies];

            for (int i = 0; i < numMovies; i++) {
                // Get the JSON object representing the movie
                JSONObject movieData = moviesArray.getJSONObject(i);
                resultMovies[i] = new Movie(movieData.getString(TMDB_TITLE),
                        movieData.getString(TMDB_POSTER_PATH),
                        movieData.getString(TMDB_PLOT),
                        movieData.getDouble(TMDB_RATING),
                        movieData.getString(TMDB_RELEASE_DATE));
            }
            return resultMovies;
        }

        @Override
        protected Movie[] doInBackground(String... params) {
            // Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            try {

                // Construct the URL for the movie db api
                final String MOVIES_BASE_URL =
                        "http://api.themoviedb.org/3/movie/";
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                        .appendPath(params[0])
                        .appendQueryParameter(API_KEY_PARAM, getString(R.string.tMDB_API_KEY))
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to moviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] movies) {
            if (movies != null) {
                mMovieAdapter.clear();
                for (Movie movie : movies) {
                    mMovieAdapter.add(movie);
                }
                // New data is back from the server.  Hooray!
            }
        }
    }
}
