package com.example.sumon.androidvolley;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sumon.androidvolley.PlayerData;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * LeaderboardActivity is an AppCompatActivity that displays a leaderboard in a ListView.
 * It manages different tabs for all-time, monthly, and weekly leaderboards.
 * This class also handles the retrieval and display of leaderboard data using Volley.
 */
public class LeaderboardActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    private Navigation navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        tabLayout = findViewById(R.id.scoresTabLayout);
        viewPager = findViewById(R.id.leaderboardViewPager);

        // Set up the ViewPager2 with the adapter
        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));

        // Set up the TabLayoutMediator to link the TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("All-time");
                            break;
                        case 1:
                            tab.setText("Monthly");
                            break;
                        case 2:
                            tab.setText("Weekly");
                            break;
                    }
                }).attach();

    }
    /**
     * Inner class for managing the fragments for the ViewPager2.
     */
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            // Return a NEW fragment instance in createFragment(int)
            switch (position) {
                case 0:
                    return new AllTimeFragment();
                case 1:
                    return new MonthlyFragment(); // Replace with your monthly fragment
                case 2:
                    return new WeeklyFragment(); // Replace with your weekly fragment
                default:
                    return null; // This should never happen. Consider throwing an exception
            }
        }

        @Override
        public int getItemCount() {
            return 3; // number of tabs
        }
    }

    /**
     * Handles item selections in the options menu.
     * @param item The MenuItem that was selected.
     * @return boolean Return false to allow normal menu processing to proceed,
     *                 true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}
