package com.example.helpmepick.fragments;

import com.example.helpmepick.Keys;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class RecommendFragment extends TrendingFragment {
    private final String RECOMMEND_URL_PREFIX = "https://api.themoviedb.org/3/search/movie?api_key=" + Keys.MOVIEDB_KEY + "&language=en-US";

    public RecommendFragment(String query){
        this.loadMovies(query);
    }

    protected void loadMovies(String url) {
        //Ugly hack? Might fix later.
        try {
            super.loadMovies(RECOMMEND_URL_PREFIX + String.format("&query=%s&page=1&include_adult=false",
                    URLEncoder.encode(url, StandardCharsets.UTF_8.toString()))
            );
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
}