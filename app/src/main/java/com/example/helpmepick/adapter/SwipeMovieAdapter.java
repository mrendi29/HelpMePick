package com.example.helpmepick.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.helpmepick.DetailActivity;
import com.example.helpmepick.model.Movie;
import com.example.helpmepick.R;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SwipeMovieAdapter extends RecyclerView.Adapter<SwipeMovieAdapter.ViewHolder> {

    Context context;
    List<Movie> recommendedMovies;


    public SwipeMovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.recommendedMovies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.swipe_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = recommendedMovies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return recommendedMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView movieName;
        ImageView movieImage;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_name);
            container = itemView.findViewById(R.id.rvMovieSwipeContainer);
        }

        public void bind(final Movie movie) {
            String imageUrl = movie.getPosterPath();
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackDropPath();
            }
            movieName.setText(movie.getTitle());

            container.setOnClickListener(v -> {
                Intent i = new Intent(context, DetailActivity.class);

                i.putExtra("movie", Parcels.wrap(movie));

                context.startActivity(i);
            });


            Glide.with(context).load(imageUrl).into(movieImage);
        }
    }

}