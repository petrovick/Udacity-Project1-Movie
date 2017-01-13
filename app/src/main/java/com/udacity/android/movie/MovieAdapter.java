package com.udacity.android.movie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.movie.Data.Movie;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel Petrovick on 10/01/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final MovieAdapterOnClickHandler mClickHandler;

    private List<Movie> mMovieList;
    Context context;

    public MovieAdapter(MovieAdapterOnClickHandler handler, Context context)
    {
        this.mClickHandler = handler;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachtoParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachtoParentImmediately);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie currentMovie = mMovieList.get(position);
        holder.tv_movie_description.setText(currentMovie.getTitle());
        holder.tv_movie_ratings.setText("Nota: " + currentMovie.getVote_average());
        try {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185" + currentMovie.getPoster_path()).into(holder.iv_movie_image);
        }
        catch (Exception ex)
        {

        }
    }

    @Override
    public int getItemCount() {
        if (null == mMovieList) return 0;
        return mMovieList.size();
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }


    public void setMMovieList(List<Movie> movieList, boolean shouldCleanList) {
        if(this.mMovieList == null)
            mMovieList = new ArrayList<Movie>();
        if(movieList != null) {
            if(shouldCleanList) {
                this.mMovieList = movieList;
            }
            else {
                this.mMovieList.addAll(movieList);
            }
            notifyDataSetChanged();
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView iv_movie_image;
        TextView tv_movie_description;
        TextView tv_movie_ratings;

        public MovieViewHolder(View view)
        {
            super(view);
            iv_movie_image = (ImageView) view.findViewById(R.id.iv_movie_table_image);
            tv_movie_description = (TextView) view.findViewById(R.id.tv_movie_table_description);
            tv_movie_ratings = (TextView) view.findViewById(R.id.tv_movie_table_ratings);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            mClickHandler.onClick(mMovieList.get(position));
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
