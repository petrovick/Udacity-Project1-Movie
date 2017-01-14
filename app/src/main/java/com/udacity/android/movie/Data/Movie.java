package com.udacity.android.movie.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gabriel Petrovick on 10/01/2017.
 */

public class Movie implements Serializable
{
    private int id;
    private String overview;
    private String poster_path;
    private String original_title;
    private String original_language;
    private String title;
    private String popularity;
    private int vote_count;
    private float vote_average;
    private Date release_date;

    public Movie(){}

    public Movie(int id, String overview, String poster_path, String original_title, String original_language, String title, String popularity, int vote_count, float vote_average, Date release_date) {
        this.id = id;
        this.overview = overview;
        this.poster_path = poster_path;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", original_title='" + original_title + '\'' +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", popularity='" + popularity + '\'' +
                ", vote_count=" + vote_count +
                ", vote_average=" + vote_average +
                ", release_date=" + release_date +
                '}';
    }
}
