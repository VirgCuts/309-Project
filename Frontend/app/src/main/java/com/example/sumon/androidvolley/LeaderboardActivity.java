package com.example.sumon.androidvolley;

import android.app.Activity;
import android.os.Bundle;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sumon.androidvolley.PlayerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends Activity {

    private ListView leaderboardListView;
    private EditText inputEditText;
    private EditText inputEditTextScore;
    private Button updateButton;
    private Button deleteButton;
    private Button addButton;

    private List<PlayerData> leaderboardData;
    private CustomAdapter adapter;
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        leaderboardListView = findViewById(R.id.leaderboardListView);
        inputEditText = findViewById(R.id.inputEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        addButton = findViewById(R.id.addButton);
        inputEditTextScore = findViewById(R.id.inputEditTextScore);

        leaderboardData = new ArrayList<>();
        adapter = new CustomAdapter(leaderboardData);
        leaderboardListView.setAdapter(adapter);


        // Handle item click on the ListView
        leaderboardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItemPosition = position;
                inputEditText.setText(leaderboardData.get(position).getUsername());
            }
        });

        // Retrieve the current leaderboard data from the API using Volley
        retrieveLeaderboardData();




    // Update button click listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemPosition != -1) {
                    String username = leaderboardData.get(selectedItemPosition).getUsername();
                    int newScore = Integer.parseInt(inputEditTextScore.getText().toString().trim());

                    // Create a Volley request to update the score for the selected username
                    updateScore(username, newScore);
                    finish();
                    startActivity(getIntent());
                    showToast("Successfully Updated!");
                } else {
                    showToast("Select an item to update.");
                }
            }
        });



        // Delete button click listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItemPosition != -1) {
                    String usernameToDelete = leaderboardData.get(selectedItemPosition).getUsername();

                    // Create a Volley request to delete the selected username
                    deletePlayer(usernameToDelete);
                    finish();
                    startActivity(getIntent());
                    showToast("Successfully Deleted!");
                } else {
                    showToast("Select an item to delete.");
                }
            }
        });




        // Add button click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputEditText.getText().toString().trim();
                String scoreText = inputEditTextScore.getText().toString().trim();

                if (!username.isEmpty() && isValidScore(scoreText)) {
                    int score = Integer.parseInt(scoreText);
                    // Create a Volley request to add the new player data
                    addPlayer(username, score);
                    finish();
                    startActivity(getIntent());
                    showToast("Successfully Added!");
                } else {
                    showToast("Enter a valid username and score.");
                }
            }
        });
    }

    private void clearInput() {
        inputEditText.setText("");
        selectedItemPosition = -1;
    }

    private void retrieveLeaderboardData() {

        String url = "http://coms-309-022.class.las.iastate.edu:8080/leaderboard";

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
    // Function to update score using Volley
    private void updateScore(String username, int newScore) {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/leaderboard/" + username + "/" + newScore;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server (if needed)
                        // Update the data in your leaderboardData list and notify the adapter
                        for (PlayerData player : leaderboardData) {
                           //Very important
                            if (player.getUsername().equals(username)) {
                                player.setScore(newScore);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        clearInput();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error updating score: " + error.getMessage());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }
    // Function to delete a player using Volley
    private void deletePlayer(String usernameToDelete) {

        String url = "http://coms-309-022.class.las.iastate.edu:8080/leaderboard/" + usernameToDelete;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server (if needed)
                        // Remove the player from your leaderboardData list and notify the adapter
                        for (int i = 0; i < leaderboardData.size(); i++) {
                            if (leaderboardData.get(i).getUsername().equals(usernameToDelete)) {
                                leaderboardData.remove(i);
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                        clearInput();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showToast("Error deleting player: " + error.getMessage());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    // Function to add a new player using Volley
    private void addPlayer(String username, int score) {
        // Create a JSON request to your API to add a new player
        // Modify the URL and request method as per your API's requirements
        String url = "http://coms-309-022.class.las.iastate.edu:8080/leaderboard/" + username + "/" + score;

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("name", username);
            requestBody.put("highScore", score);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Handle the response from the server (if needed)
                            // Create a new PlayerData object and add it to your leaderboardData list
                            leaderboardData.add(new PlayerData(username, score));
                            adapter.notifyDataSetChanged();
                            clearInput();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showToast("Error adding player: " + error.getMessage());
                        }
                    }
            );

            // Add the request to the Volley request queue
            Volley.newRequestQueue(this).add(request);
        } catch (JSONException e) {
            showToast("Error creating JSON request: " + e.getMessage());
        }
    }

    // Helper function to validate score as a positive integer
    private boolean isValidScore(String scoreText) {
        try {
            int score = Integer.parseInt(scoreText);
            return score >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Custom Adapter to display player data
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
