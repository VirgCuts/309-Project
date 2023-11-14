package com.example.sumon.androidvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sumon.androidvolley.app.AppController;
import com.example.sumon.androidvolley.utils.Const;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Class used for the study activity to test the user against data in the database
 *
 */
public class StudyActivity extends AppCompatActivity {
    private final String TAG = StudyActivity.class.getSimpleName();
    private EditText msgResponse;
    private TextView random,displayCorrect,numCorrect,numIncorrect, streak;
    private String currentArtist;
    private int numSongs;
    private TextView songList;

    private Navigation navigationHelper;
    @Override
    /**
     * Intializes Buttons and listeners
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_db);

        navigationHelper = new Navigation(this);
        navigationHelper.setupNavigation();

//        Button btnBack = findViewById(R.id.btnBack);
        Button getRandomButton = findViewById(R.id.button);
        Button showAnswers = findViewById(R.id.showAllAnswers);

        msgResponse = findViewById(R.id.songGuess);
        random = findViewById(R.id.artistName);
        displayCorrect = findViewById(R.id.displayCorrectness);
        numCorrect = findViewById(R.id.numCorrect);
        numIncorrect = findViewById(R.id.numIncorrect);
        streak = findViewById(R.id.streak);
        songList = findViewById(R.id.songList);


        msgResponse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String artistToSearch = currentArtist;
                    String songToSearch = msgResponse.getText().toString();
                    Log.d("EDIT", "ACTION TAKEN");
                    boolean checker = checkArtistSong(artistToSearch, songToSearch);
                    if (checker) {

                        return true;
                    }
                }

                return false;
            }
        });


        getRandomButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                retrieveRandomArtist(); displayCorrect.setText("");
            }
        });
        showAnswers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAllAnswers(currentArtist);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return navigationHelper.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    /**
     * Retrieves a random artist from the server and updates the UI with the artist's information.
     */
    private void retrieveRandomArtist() {

        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/random";
//        String url = "http://localhost:8080/artists/random";


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        // Parse the JSON array to populate the leaderboardData list
                        //for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject artistJson = response;
                                String name = artistJson.getString("name");
                                int plat = artistJson.getInt("numPlatinums");
                                int grammys = artistJson.getInt("numGrammys");
                                String songs = artistJson.getString("songs");

                                String albums = artistJson.getString("albums");

                                Log.d("PLAT", Integer.toString(plat));
                                Log.d("GRAM", Integer.toString(grammys));
                                Log.d("SONGS", songs);
                                Log.d("ALBUMS", albums);

                                random.setText(name);
                                currentArtist = name;
//                                showToast("Name a song from this artist: " + name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        //}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
//                        showToast(error.getMessage());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }
    /**
     * Shows all the answers (songs) for a given artist.
     *
     * @param name The name of the artist.
     */
    private void showAllAnswers(String name) {

        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + name + "/songs/string/study";


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        // Parse the JSON array to populate the leaderboardData list
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject artistJson = response;
                                String list = artistJson.getString("list");
                                songList.setText("The artists' songs are: " + list);
                                Log.d("List", list);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
//                        showToast(error.getMessage());
                    }
                }
        );
        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Increases Incorrect answer tally on screen
     */
    private void updateIncorrect() {
        String[] splitTxt = numIncorrect.getText().toString().split(":");
        int var = Integer.parseInt(splitTxt[1].trim());
        var++;
        String varS = Integer.toString(var);
        numIncorrect.setText("Incorrect: " + varS);

        streak.setText("Correct Streak: 0");
        //resets the editText
        msgResponse.setText("");
    }
    /**
     * Increases Correct answer tally on screen
     */
    private void updateCorrect() {
        String[] splitTxt = numCorrect.getText().toString().split(":");
        int var = Integer.parseInt(splitTxt[1].trim());
        var++;
        String varS = Integer.toString(var);
        numCorrect.setText("Correct: " + varS);

        String[] splitStreak = streak.getText().toString().split(":");
        int streakInt = Integer.parseInt(splitStreak[1].trim());
        streakInt++;
        String streakvar = Integer.toString(streakInt);

        streak.setText("Correct Streak:" + streakvar);
        //resets the editText
        msgResponse.setText("");
        //When correct generates new random artist
        retrieveRandomArtist();

    }
    /**
     * Checks if a given song by a specific artist is correct by making a request to the server.
     *
     * @param artist The name of the artist.
     * @param song   The name of the song to be checked.
     * @return True if the song is correct, false otherwise.
     */
    private boolean checkArtistSong(String artist, String song) {

        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + artist + "/songs/" + song;
//        String url = "http://localhost:8080/artists/random";

        final boolean[] checker = {false};

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the response from the server
                        // Parse the JSON array to populate the leaderboardData list
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject artistJson = response;
                                String message = artistJson.getString("message");
                                if (message.equals("success")) {
                                    checker[0] = true;
                                    displayCorrect.setText("Correct!");
                                    updateCorrect();
                                }
                                else {
                                    checker[0] = false;
                                    displayCorrect.setText("Incorrect!");
                                    updateIncorrect();
                                }

//                                int plat = artistJson.getInt("numPlatinums");
//                                int grammys = artistJson.getInt("numGrammys");
//                                String songs = artistJson.getString("songs");
//                                String albums = artistJson.getString("albums");
//                                random.setText("Your random artist is: " + name);
//                                currentArtist = name;
//                                showToast("Name a song from this artist: " + name);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
        return checker[0];
    }

//    private void showToast(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//    }
}
