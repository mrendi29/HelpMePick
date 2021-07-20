package com.example.helpmepick.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    String posterPath;
    String title;
    String overview;
    String backDropPath;
    double rating;


    int movieId;

    //Empty constructor needed for parcel
    public Movie() {
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backDropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");

    }

    // 2. Create a list of movies and then populate it with JsonObjects from the JsonArray instance we got in the movieActivity
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < movieJsonArray.length(); ++i) {
            // Each time we add a new Movie Json Object the constructor is called and each value is populated
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        // The List is then returned into the other method which it was called.
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackDropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backDropPath);
    }

    public Float getRating() {
        return (float) rating;
    }

    public int getMovieId() {
        return movieId;
    }
}

