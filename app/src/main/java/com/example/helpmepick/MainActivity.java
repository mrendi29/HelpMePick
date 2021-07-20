package com.example.helpmepick;

import android.os.Bundle;

import com.example.helpmepick.fragments.RecommendFragment;
import com.example.helpmepick.fragments.TrendingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment activeFragment;
            switch (item.getItemId()) {
                case R.id.action_home:
                    activeFragment = new RecommendFragment();
                    break;
                case R.id.action_movies:
                    activeFragment = new TrendingFragment();
                    break;
                default:
                    activeFragment = new TrendingFragment();
                    break;
            }

            fragmentManager.beginTransaction().replace(R.id.flContainer, activeFragment).commit();
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.action_movies);
    }
}