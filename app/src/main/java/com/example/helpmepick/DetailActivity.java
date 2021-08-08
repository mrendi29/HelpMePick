package com.example.helpmepick;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helpmepick.adapter.ReviewAdapter;
import com.example.helpmepick.adapter.SwipeMovieAdapter;
import com.example.helpmepick.model.Movie;
import com.example.helpmepick.model.Review;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class DetailActivity extends YouTubeBaseActivity {

    private static final String TRAILER_API_KEY = "https://api.themoviedb.org/3/movie/%d/videos?api_key=" + Keys.TRAILER_API_KEY;
    private static final String MOVIE_API_KEY = "https://api.themoviedb.org/3/movie/%d/recommendations?api_key=" + Keys.MOVIEDB_KEY;
    private static final String REVIEW_API_KEY = "https://api.themoviedb.org/3/movie/%d/reviews?api_key=" + Keys.MOVIEDB_KEY;


    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    Movie movie;
    List<Movie> recommendedMovies;
    YouTubePlayerView youTubePlayerView;
    RecyclerView recommendView;
    SwipeMovieAdapter swipeMovieAdapter;
    RecyclerView reviewView;
    ReviewAdapter reviewAdapter;
    List<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Pull references
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);

        //Getting intent from parcelable
        movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));


        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating(movie.getRating());

        //Make a request call for the youtube client as we did with the previous one.
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get(String.format(TRAILER_API_KEY, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    if (results.length() == 0)
                        return;

                    JSONObject movieTrailer = results.getJSONObject(0);
                    String youtubeKey = movieTrailer.getString("key");
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //set Review adapter
        reviewView =  findViewById(R.id.rvReview);
        reviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(this, reviews);

        reviewView.setAdapter(reviewAdapter);
        reviewView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //load Reviews
        loadReviews(String.format(REVIEW_API_KEY, movie.getMovieId()));

        //set adapter
        recommendView =  findViewById(R.id.rvRecommendSwipe);
        recommendedMovies = new ArrayList<>();
        swipeMovieAdapter = new SwipeMovieAdapter(this, recommendedMovies);

        recommendView.setAdapter(swipeMovieAdapter);
        recommendView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //load recommended movies
        loadMovies(String.format(MOVIE_API_KEY, movie.getMovieId()));

        //set show hide button
        TextView showOrHide =  (TextView) findViewById(R.id.showRecommendationsButton);
        showOrHideReccomendations(showOrHide);

    }

    private void initializeYoutube(final String youtubeKey) {
        youTubePlayerView.initialize(Keys.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                Log.d("youtube", "success");
                if (movie.getRating() > 7)
                    youTubePlayer.setFullscreen(true);

                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("youtube", "fail");
            }
        });
    }

    protected void loadMovies(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                        JSONArray result = response.getJSONArray("results");
                        if (statusCode != 200 || result.length() == 0)
                            return;

                        recommendedMovies.addAll(Movie.fromJsonArray(result));

                        swipeMovieAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Movie Recommendation","You Donked up");
            }
        });
    }

    protected void loadReviews(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");
                    if (statusCode != 200 || result.length() == 0)
                        return;

                    reviews.addAll(Review.fromJsonArray(result));

                    reviewAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Review","Review didn't load");
            }
        });
    }

    protected void showOrHideReccomendations(TextView showOrHide){
        recommendView = findViewById(R.id.rvRecommendSwipe);
        showOrHide.setOnClickListener(v ->{
            if (recommendView.getVisibility() == View.VISIBLE){
                recommendView.setVisibility(View.GONE);
                showOrHide.setText("Show");
            } else {
                recommendView.setVisibility(View.VISIBLE);
                showOrHide.setText("Hide");
            }
        });
    }
}