package com.example.helpmepick.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Review {

    String username;
    String rating;
    String content;

    //Empty constructor needed for parcel
    public Review() {
    }

    public Review(JSONObject jsonObject) throws JSONException {
       JSONObject author_detail = jsonObject.getJSONObject("author_details");
       try{
           rating = String.valueOf(author_detail.getInt("rating")) + "/10";
       } catch (Exception e){
           rating = "N/A";
       }

       username = author_detail.getString("username");
       content = jsonObject.getString("content");

    }

    // 2. Create a list of reviews and then populate it with JsonObjects from the JsonArray instance we got in the reviewActivity
    public static List<Review> fromJsonArray(JSONArray reviewJsonArray) throws JSONException {
        List<Review> reviews = new ArrayList<>();

        for (int i = 0; i < reviewJsonArray.length(); ++i) {
            // Each time we add a new Movie Json Object the constructor is called and each value is populated
            JSONObject review = reviewJsonArray.getJSONObject(i);
            reviews.add(new Review(review));
        }
        // The List is then returned into the other method which it was called.
        return reviews;

    }

    public String getRating() {
        return rating;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

}
