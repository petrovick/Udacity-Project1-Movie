package com.udacity.android.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.movie.Data.Movie;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Gabriel Petrovick on 10/01/2017.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView mParallax_header_imageview;

    TextView tv_movie_detail_original_title;
    TextView tv_movie_detail_overview;
    TextView tv_movie_detail_vote_average;
    TextView tv_movie_detail_release_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        //getSupportActionBar().hide();

        mParallax_header_imageview = (ImageView) findViewById(R.id.parallax_header_imageview);

        tv_movie_detail_original_title = (TextView) findViewById(R.id.tv_movie_detail_original_title);
        tv_movie_detail_overview = (TextView) findViewById(R.id.tv_movie_detail_overview);
        tv_movie_detail_vote_average = (TextView) findViewById(R.id.tv_movie_detail_vote_average);
        tv_movie_detail_release_date= (TextView) findViewById(R.id.tv_movie_detail_release_date);

        getExtras();
    }

    private void getExtras()
    {
        Intent intentThatHaveCalled = getIntent();
        if(intentThatHaveCalled != null)
        {
            if(intentThatHaveCalled.hasExtra("movie"))
            {
                movie = (Movie) intentThatHaveCalled.getParcelableExtra("movie");

                Picasso.with(this).load("http://image.tmdb.org/t/p/w185" + movie.getPoster_path()).into(mParallax_header_imageview);

                DecimalFormat df = new DecimalFormat("#.00");
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                tv_movie_detail_original_title.setText(movie.getOriginal_title());
                tv_movie_detail_overview.setText(movie.getOverview());
                tv_movie_detail_vote_average.setText(df.format(movie.getVote_average()));
                tv_movie_detail_release_date.setText(dateFormat.format(movie.getRelease_date()));
            }
        }
    }

}
