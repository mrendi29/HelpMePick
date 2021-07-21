package com.example.helpmepick.fragments;

import com.example.helpmepick.Keys;

public class RecommendFragment extends TrendingFragment {
    private final String RECOMMEND_URL_PREFIX = "https://api.themoviedb.org/3/search/movie?api_key=" + Keys.MOVIEDB_KEY + "&language=en-US";


    protected void loadMovies(String url) {
        //Ugly hack? Might fix later.
        super.loadMovies(RECOMMEND_URL_PREFIX + "&query=natasha&page=1&include_adult=false");
    }
}