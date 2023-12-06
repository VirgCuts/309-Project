package com.example.sumon.androidvolley;

import static java.lang.String.valueOf;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sumon.androidvolley.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Fragment} subclass that represents the all-time leaderboard.
 * This fragment is used to display a list or a summary of top scorers or performers
 * across all time in a specific category.
 */
public class WeeklyFragment extends Fragment {
    private List<PlayerData> allTimeLeaderboardData;
    private ArrayAdapter<PlayerData> adapter;

    /**
     * Required empty public constructor for instantiating the fragment.
     */
    public WeeklyFragment() {}

    /**
     * Called to have the fragment instantiate its user interface view. This is optional,
     * and non-graphical fragments can return null (which is the default implementation).
     * This will be called between {@code onCreate(Bundle)} and {@code onActivityCreated(Bundle)}.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to. The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_leaderboard, container, false);

        ListView leaderboardListView = view.findViewById(R.id.leaderboardListViewWeekly);
        allTimeLeaderboardData = new ArrayList<>();
        adapter = new AllTimeLeaderboardAdapter(getActivity(), allTimeLeaderboardData);
        leaderboardListView.setAdapter(adapter);

        // Retrieve the leaderboard data (e.g., from an API)
        retrieveLeaderboardData();

        return view;
    }
    /**
     * Retrieves leaderboard data from the API using Volley based on the leaderboard type.
     * The type of leaderboard to retrieve (e.g., "allTime", "monthly", "weekly").
     */
    private void retrieveLeaderboardData() {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/leaderboard";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        allTimeLeaderboardData.clear(); // Clear existing data

                        // Iterate over the JSON array
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                // Get the JSON object for each player
                                JSONObject playerJson = response.getJSONObject(i);

                                // Extract the required data
                                String username = playerJson.getString("name");
                                int highScore = playerJson.getInt("highScore");
                                int highmScore = playerJson.getInt("highScoreMonthly");
                                int highwScore = playerJson.getInt("highScoreWeekly");
                                // Add to leaderboard data list
                                allTimeLeaderboardData.add(new PlayerData(username, highScore, highmScore, highwScore));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Notify the adapter of the data change
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response
                        showToast("Error retrieving leaderboard data: " + error.getMessage());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(getContext()).add(request);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }


    public class AllTimeLeaderboardAdapter extends ArrayAdapter<PlayerData> {
        // Constructor
        AllTimeLeaderboardAdapter(Context context, List<PlayerData> data) {
            super(context, R.layout.list_item_layout, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_layout, parent, false);
            }

            TextView playerNameTextView = convertView.findViewById(R.id.playerUsernameTextView);
            TextView highScoreTextView = convertView.findViewById(R.id.scoreTextView);
            TextView playerRankView = convertView.findViewById(R.id.playerRank);
            PlayerData player = getItem(position);
            if (player != null) {
                playerNameTextView.setText(player.getUsername());
                highScoreTextView.setText(String.valueOf(player.getWeeklyScore())); // Display all-time high score
                int rank = position + 1;
                playerRankView.setText(String.format("#%d", rank));
            }

            return convertView;
        }
    }
}
