package com.example.helpmepick.fragments;

import com.example.helpmepick.Keys;
import com.example.helpmepick.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class RecommendFragment extends TrendingFragment {
    private final String RECOMMEND_URL_PREFIX = "https://api.themoviedb.org/3/search/movie?api_key=" + Keys.MOVIEDB_KEY + "&language=en-US";


    protected void loadMovies() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(RECOMMEND_URL_PREFIX + "&query=natasha&page=1&include_adult=false", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results");

                    movies.addAll(Movie.fromJsonArray(movieJsonArray));

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}