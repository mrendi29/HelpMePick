package com.example.helpmepick.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
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
import com.example.helpmepick.model.Review;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_review_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewAdapter.ViewHolder holder, int position) {

        Review review = reviews.get(position);

        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        TextView rating;
        TextView content;
        RelativeLayout reviewContainer;
        boolean viewFullReview = false;

        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.review_username);
            rating  = itemView.findViewById(R.id.review_rating);
            content = itemView.findViewById(R.id.review_content);
            reviewContainer = itemView.findViewById(R.id.reviewContainer);

        }

        public void bind(final Review review) {

            username.setText(review.getUsername());
            rating.setText(review.getRating());
            content.setText(review.getContent());

            reviewContainer.setOnClickListener(v ->{
                if (viewFullReview) {
                    content.setMaxLines(4);
                    viewFullReview = false;
                }
                else {
                    content.setMaxLines(Integer.MAX_VALUE);
                    viewFullReview = true;
                }
            });

        }

    }

}
