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
 * This class represents the Leaderboard Activity.
 */
public class LeaderboardActivity extends AppCompatActivity {

    private ListView leaderboardListView;
    private EditText inputEditText;
    private EditText inputEditTextScore;
    private Button updateButton;
    private Button deleteButton;
    private Button addButton;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    private List<PlayerData> leaderboardData;
    private CustomAdapter adapter;
    private int selectedItemPosition = -1;
    private Navigation navigationHelper;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

        leaderboardListView = findViewById(R.id.leaderboardListView);
        tabLayout = findViewById(R.id.scoresTabLayout);
        viewPager = findViewById(R.id.leaderboardViewPager);

        leaderboardData = new ArrayList<>();
        adapter = new CustomAdapter(leaderboardData);
        leaderboardListView.setAdapter(adapter);
        // Set up the ViewPager2 with the adapter
        viewPager.setAdapter(new ScreenSlidePagerAdapter(this));

        // Set up the TabLayoutMediator to link the TabLayout and ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
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
                    }
                }).attach();

        leaderboardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;


                // Find the TextViews where you want to display the rank and score.
                TextView playerRankTextView = findViewById(R.id.playerRankTextView);
                TextView playerScoreTextView = findViewById(R.id.playerScoreTextView);

                // Get the selected player data.
                PlayerData selectedPlayer = leaderboardData.get(position);

                // Update the TextViews with the selected player's rank and score.
                playerRankTextView.setText(String.format("%d", position + 1));
                playerScoreTextView.setText(String.format("%d", selectedPlayer.getScore()));
            }
        });
        // Retrieve the current leaderboard data from the API using Volley
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        retrieveLeaderboardData("");
                        break;
                    case 1:
                        retrieveLeaderboardData("");
                        break;
                    case 2:
                        retrieveLeaderboardData("");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselect
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselect if necessary
            }
        });
        retrieveLeaderboardData("");

    }

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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    /**
     * Clear the input fields and reset the selected item position.
     */
    private void clearInput() {
        inputEditText.setText("");
        selectedItemPosition = -1;
    }

    /**
     * Retrieve leaderboard data from the API using Volley.
     */
    private void retrieveLeaderboardData(String leaderboardType) {
        String baseUrl = "http://coms-309-022.class.las.iastate.edu:8080/leaderboard";
        String url = "";
        switch (leaderboardType) {
            case "allTime":
                url = baseUrl + "/allTime";
                break;
            case "monthly":
                url = baseUrl + "/monthly";
                break;
            case "weekly":
                url = baseUrl + "/weekly";
                break;
            default:
                url = baseUrl; // Default case can be handled as you see fit
                break;
        }

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the response from the server
                        // Parse the JSON array to populate the leaderboardData list
                        leaderboardData.clear(); // Clear existing data
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject playerJson = response.getJSONObject(i);
                                String username = playerJson.getString("name");
                                int score = playerJson.getInt("highScore");
                                leaderboardData.add(new PlayerData(username, score));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged(); // Notify the adapter of the data change
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error retrieving leaderboard data: " + error.getMessage());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Display a Toast message.
     *
     * @param message The message to display.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Custom adapter to display player data in the ListView.
     */
    private class CustomAdapter extends ArrayAdapter<PlayerData> {
        CustomAdapter(List<PlayerData> data) {
            super(LeaderboardActivity.this, R.layout.list_item_layout, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
            }

            TextView playerRankTextView = convertView.findViewById(R.id.playerRank);
            TextView playerNameTextView = convertView.findViewById(R.id.playerUsernameTextView);
            TextView scoreTextView = convertView.findViewById(R.id.scoreTextView);


            PlayerData player = getItem(position);
            if (player != null) {
                int rank = position + 1;
                playerRankTextView.setText(String.valueOf(rank));
                playerNameTextView.setText(player.getUsername());
                scoreTextView.setText(String.valueOf(player.getScore()));

            }

            return convertView;
        }
    }

}
