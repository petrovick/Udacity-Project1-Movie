package com.udacity.android.movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.udacity.android.movie.Data.Movie;
import com.udacity.android.movie.Utilities.NetworkUtils;
import com.udacity.android.movie.Utilities.OpenMovieJsonUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel Petrovick on 10/01/2017.
 */

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerMovies;
    private int page = 1;
    int pastVisiblesItems;


    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;


    private List<Movie> mMovieList;
    private MovieAdapter mMovieAdapter;
    private Toast mToast;

    private String calledFunction = "top_rated";
    private boolean cleanList = false;
    //LinearLayoutManager layoutManager;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerMovies = (RecyclerView) findViewById(R.id.rv_movies);

        layoutManager = new GridLayoutManager(this, 4);//LinearLayoutManager.VERTICAL, false);

        mRecyclerMovies.setLayoutManager(layoutManager);
        mRecyclerMovies.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this, this);
        mRecyclerMovies.setAdapter(mMovieAdapter);


        mRecyclerMovies.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {

                recountListForLoadingPages();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached
                    getNextPage();
                    loading = true;
                }
            }
        });

        callService();
    }

    private void getNextPage()
    {
        page++;
        cleanList = false;
        callService();
    }

    private void recountListForLoadingPages()
    {
        visibleItemCount = mRecyclerMovies.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
    }

    private class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>>
    {
        @Override
        protected List<Movie> doInBackground(String... params) {

            String location = params[0];
            try {
            URL movieRequestUrl = NetworkUtils.buildUrl(location, page);

                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                List<Movie> simpleJsonMovieData = OpenMovieJsonUtils
                        .getSimpleMoviesFromJson(MainActivity.this, jsonWeatherResponse);
                mMovieList = simpleJsonMovieData;
                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            if(movies == null)
            {
                mToast = Toast.makeText(MainActivity.this, "Erro ao buscar dados do servidor, verifique sua conexão.", Toast.LENGTH_SHORT);
                mToast.show();
            }
            mMovieAdapter.setMMovieList(mMovieList, cleanList);

        }
    }

    private void callService()
    {
        String url = calledFunction;
        new MovieAsyncTask().execute(url);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_refresh:
                cleanList = true;
                page = 1;
                visibleThreshold = 5;
                previousTotal = 0;
                //recountListForLoadingPages();
                callService();
                break;
            case R.id.action_rated:
                if(!calledFunction.equals("top_rated")) {
                    calledFunction = "top_rated";
                    cleanList = true;
                    page = 1;
                    visibleThreshold = 5;
                    previousTotal = 0;
                    recountListForLoadingPages();
                    callService();
                }
                break;
            case R.id.action_popularity:
                if(!calledFunction.equals("popular")) {
                    calledFunction = "popular";
                    cleanList = true;
                    page = 1;
                    visibleThreshold = 5;
                    previousTotal = 0;
                    recountListForLoadingPages();
                    callService();
                }
                break;
            default:
                break;
        }
        return true;
    }
}
