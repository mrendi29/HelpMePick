package com.example.helpmepick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.msebera.android.httpclient.Header;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.helpmepick.adapter.MoviesAdapter;
import com.example.helpmepick.fragments.RecommendFragment;
import com.example.helpmepick.fragments.TrendingFragment;
import com.example.helpmepick.model.Movie;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final Fragment trendingMovies = new TrendingFragment();
    private final Fragment recommendedMovies = new RecommendFragment();
    private  Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


//        fragmentManager.beginTransaction().add(R.id.flContainer,trendingMovies,"1").commit();
//        fragmentManager.beginTransaction().add(R.id.flContainer,recommendedMovies,"2").hide(recommendedMovies).commit();
//        activeFragment = trendingMovies;

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            Fragment fragment;
//            Fragment activeFragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.action_home:
                    Toast.makeText(MainActivity.this,"Home",Toast.LENGTH_LONG).show();
                    fragmentManager.beginTransaction().hide(activeFragment).show(recommendedMovies).commit();
                    activeFragment = recommendedMovies;
//                    fragmentManager.beginTransaction().replace(R.id.flContainer,fragment);
                    Log.v("MyApp","Recommend");
                    break;
                case R.id.action_movies:
                    Toast.makeText(MainActivity.this,"Movies",Toast.LENGTH_LONG).show();
                    activeFragment = new TrendingFragment();
                    Log.v("MyApp","Movies");
//                    fragmentManager.beginTransaction().replace(R.id.flContainer,fragment);
                    break;
                default:
                    activeFragment = new TrendingFragment();
                    Log.v("MyApp","Myapp");
//                    fragmentManager.beginTransaction().replace(R.id.flContainer,fragment);
                    break;
            }

//            fragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit();
            fragmentManager.beginTransaction().replace(R.id.flContainer,activeFragment);
            Log.v("MyApp","fmanager");
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.action_movies);
    }
}