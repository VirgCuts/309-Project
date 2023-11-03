package com.example.sumon.androidvolley;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

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

public class StudyActivity extends Activity {
    private final String TAG = StudyActivity.class.getSimpleName();
    private EditText msgResponse;
    private TextView random;

    private TextView displayCorrect;

    private String currentArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.study_db);

//        Button btnBack = findViewById(R.id.btnBack);
        Button getRandomButton = findViewById(R.id.button);

        msgResponse = findViewById(R.id.songGuess);
        random = findViewById(R.id.artistName);
        displayCorrect = findViewById(R.id.displayCorrectness);

//        pDialog = new ProgressDialog(this);
//        pDialog.setMessage("Loading...");
//        pDialog.setCancelable(false);
//        makeStringReq();

        msgResponse.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) { // Use EditorInfo.IME_ACTION_DONE
                    String artistToSearch = currentArtist;
                    String songToSearch = msgResponse.getText().toString();

                    boolean checker = checkArtistSong(artistToSearch, songToSearch);
//                    if (checker) {
//                        displayCorrect.setText("Correct!");
//                    }
//                    else {
//                        displayCorrect.setText("Incorrect");
//                    }

                    return true; // Return true to indicate that the action has been handled
                }
                return false; // Return false if not handled
            }
        });
        getRandomButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                retrieveRandomArtist();
            }
        });
    }



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
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject artistJson = response;
                                String name = artistJson.getString("name");
                                int plat = artistJson.getInt("numPlatinums");
                                int grammys = artistJson.getInt("numGrammys");
                                String songs = artistJson.getString("songs");
                                String albums = artistJson.getString("albums");
                                random.setText("Your random artist is: " + name);
                                currentArtist = name;
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
//                        showToast(error.getMessage());
                    }
                }
        );

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }

    private boolean checkArtistSong(String artist, String song) {
//        artist = artist.replace(' ', '-');
//        song = song.replace(' ', '-');
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
                                }
                                else {
                                    checker[0] = false;
                                    displayCorrect.setText("Incorrect!");
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
//                        showToast(error.getMessage());
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
