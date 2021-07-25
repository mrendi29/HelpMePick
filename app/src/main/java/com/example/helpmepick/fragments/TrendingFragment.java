package com.example.helpmepick.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.helpmepick.Keys;
import com.example.helpmepick.R;
import com.example.helpmepick.adapter.MoviesAdapter;
import com.example.helpmepick.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

public class TrendingFragment extends Fragment {
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + Keys.MOVIEDB_KEY;
    protected List<Movie> movies;
    protected MoviesAdapter adapter;

    private RecyclerView rvMovies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false);
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovies = view.findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        adapter = new MoviesAdapter(getContext(), movies);

        rvMovies.setAdapter(adapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        loadMovies(MOVIE_URL);

    }

    protected void loadMovies(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (isAdded()) {
                        if (statusCode == 200 && response.getJSONArray("results").length() != 0) {
                            JSONArray movieJsonArray = response.getJSONArray("results");

                            movies.addAll(Movie.fromJsonArray(movieJsonArray));

                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity(), "Try searching something interesting :)", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (isAdded())
                    Toast.makeText(getActivity(), "Whoops! Something went wrong :( \n Please try again :)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}