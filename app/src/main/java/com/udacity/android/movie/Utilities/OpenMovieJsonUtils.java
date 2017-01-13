package com.udacity.android.movie.Utilities;

import android.content.Context;

import com.udacity.android.movie.Data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Gabriel Petrovick on 10/01/2017.
 */

public class OpenMovieJsonUtils
{
    public static List<Movie> getSimpleMoviesFromJson(Context context, String movieJsonStr)
            throws JSONException, ParseException {

        /* Weather information. Each day's forecast info is an element of the "list" array */
        final String OWM_LIST = "results";

        /* All temperatures are children of the "temp" object */
        final String OWM_POSTER_PATH = "poster_path";

        /* Max temperature for the day */
        final String OWM_OVERVIEW = "overview";
        final String OWM_ID = "id";

        final String OWM_ORIGINAL_TITLE = "original_title";
        final String OWM_ORIGINAL_LANGUAGE = "original_language";

        final String OWM_TITLE = "title";
        final String OWM_POPULARITY = "popularity";
        final String OWM_VOTE_COUNT = "vote_count";
        final String OWM_VOTE_AVERAGE = "vote_average";
        final String OWM_RELEASE_DATE = "release_date";


        /* String array to hold each day's weather String */
        List<Movie> parsedMovieData = null;

        JSONObject moviesJson = new JSONObject(movieJsonStr);

        JSONArray moviesArray = moviesJson.getJSONArray(OWM_LIST);

        parsedMovieData = new ArrayList<Movie>();

            /* These are the values that will be collected */
        int id;
        String overview;
        String poster_path;
        String original_title;
        String original_language;
        String title;
        String popularity;
        int vote_count;
        float vote_average;
        String release_date;
        Date release_date_DATA;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < moviesArray.length(); i++) {

            /* Get the JSON object representing the day */
            JSONObject movieObject = moviesArray.getJSONObject(i);

            id = movieObject.getInt(OWM_ID);
            overview= movieObject.getString(OWM_OVERVIEW);
            poster_path= movieObject.getString(OWM_POSTER_PATH);
            original_title= movieObject.getString(OWM_ORIGINAL_TITLE);
            original_language = movieObject.getString(OWM_ORIGINAL_LANGUAGE);
            title = movieObject.getString(OWM_TITLE);
            popularity = movieObject.getString(OWM_POPULARITY);
            vote_count = movieObject.getInt(OWM_VOTE_COUNT);
            vote_average = (float) movieObject.getDouble(OWM_VOTE_AVERAGE);
            release_date = movieObject.getString(OWM_RELEASE_DATE);
            release_date_DATA = dateFormat.parse(release_date);

            parsedMovieData.add(new Movie(id, overview, poster_path, original_title,original_language,title,popularity,vote_count,vote_average, release_date_DATA));

        }

        return parsedMovieData;
    }
}
