package com.example.sumon.androidvolley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SinglePlayerGame extends AppCompatActivity implements GameViewInterface, GameControllerInterface {
    private TextView timerTextView;
    private Button endGameButton;
    private Handler handler = new Handler();
    private int seconds = 240;
    private int points = 0;

    private PlayerBoard playerBoard;
    private int correctGuesses = 0;
    private final int TOTAL_EDIT_TEXTS = 9;
    private RequestQueue queue;
    private EditText r1c1,r1c2,r1c3,r2c1,r2c2,r2c3,r3c1,r3c2,r3c3;
    private TextView col1,col2,col3,row1,row2,row3;
    private boolean categoriesLoaded = false;
    //Used for the categories. Stored in a String[][] format.
    //Each category has [[text, subject, check, keyword],[...]]
    List<Map<String, String>> categories;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String USERNAME_KEY = "username";

    private String username = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player);
        fetchCategories();

        //Set up the player's grid
        r1c2 = findViewById(R.id.r1c2);
        r1c3 = findViewById(R.id.r1c3);
        r2c1 = findViewById(R.id.r2c1);
        r2c2 = findViewById(R.id.r2c2);
        r2c3 = findViewById(R.id.r2c3);
        r3c1 = findViewById(R.id.r3c1);
        r3c2 = findViewById(R.id.r3c2);
        r3c3 = findViewById(R.id.r3c3);
        r1c1 = findViewById(R.id.r1c1);
        col1 = findViewById(R.id.col1);
        col2 = findViewById(R.id.col2);
        col3 = findViewById(R.id.col3);
        row1 = findViewById(R.id.row1);
        row2 = findViewById(R.id.row2);
        row3 = findViewById(R.id.row3);

        r1c1.setText("");
        r1c2.setText("");
        r1c3.setText("");
        r2c1.setText("");
        r2c2.setText("");
        r2c3.setText("");
        r3c1.setText("");
        r3c2.setText("");
        r3c3.setText("");

        setEditTextListener(r1c1);
        setEditTextListener(r1c2);
        setEditTextListener(r1c3);
        setEditTextListener(r2c1);
        setEditTextListener(r2c2);
        setEditTextListener(r2c3);
        setEditTextListener(r3c1);
        setEditTextListener(r3c2);
        setEditTextListener(r3c3);
        username = getUsername(this);

        Log.d("Username", username);
        playerBoard = new PlayerBoard();

        endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });
        timerTextView = findViewById(R.id.timer);
    }

    @Override
    public void Timer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                seconds--;

                // Check if time is over
                if (seconds <= 0) {
                    timerTextView.setText("Time Over");
                    handler.removeCallbacks(this); // Remove any pending callbacks to stop the timer
                    endGame();
                    return;
                }

                int minutes = seconds / 60;
                int remainingSeconds = seconds % 60;

                // Format the time as "mm:ss"
                String time = String.format("%d:%02d", minutes, remainingSeconds);
                timerTextView.setText(time);

                handler.postDelayed(this, 1000); // Call this Runnable after 1 second delay
            }
        });
    }

    public String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = prefs.getString(USERNAME_KEY, null); // Return null if not found

        // Debugging log
        Log.d("SharedPreferences", "Retrieving username: " + username);

        return username;
    }
    private void updatePlayerBoard(EditText editText, String answer) {
        // Get the tag from the EditText, which contains the row and column information
        String tag = (String) editText.getTag();

        // Split the tag to separate row and column values
        String[] parts = tag.split(",");
        if (parts.length == 2) {
            try {
                // Parse the row and column from the tag
                int row = Integer.parseInt(parts[0]) - 1; // Subtract 1 if your row/column indices start at 1
                int column = Integer.parseInt(parts[1]) - 1; // Subtract 1 if your row/column indices start at 1

                // Update the playerBoard with the answer at the specified row and column
                playerBoard.edit(row, column, answer);
            } catch (NumberFormatException e) {
                Log.e("updatePlayerBoard", "Invalid tag format for EditText: " + tag, e);
            }
        } else {
            Log.e("updatePlayerBoard", "Tag on EditText does not contain both row and column information: " + tag);
        }
    }

    @Override
    public void setBoxText(TextView textView, String text) {
        textView.setText(text);
    }

    public void setPoints() {
        TextView pointView = findViewById(R.id.Points);
        pointView.setText("Points: "+ points);
    }
    @Override
    public void changeBoxColor(EditText editText, boolean isCorrect) {
        if (isCorrect) {
            int semiTransparentGreen = Color.argb(128, 0, 255, 0);
            ColorFilter colorFilter = new PorterDuffColorFilter(semiTransparentGreen, PorterDuff.Mode.SRC_ATOP);
            editText.getBackground().mutate().setColorFilter(colorFilter);
        } else {
            int semiTransparentRed = Color.argb(128, 255, 0, 0);
            ColorFilter colorFilter = new PorterDuffColorFilter(semiTransparentRed, PorterDuff.Mode.SRC_ATOP);
            editText.getBackground().mutate().setColorFilter(colorFilter);
            animateFlash(editText);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    editText.getBackground().mutate().clearColorFilter(); // Use your original EditText drawable here
                    editText.setText("");
                }
            }, 280);// Set a delay to revert the color
        }
    }
    private void animateFlash(EditText editText) {
        Animation flash = AnimationUtils.loadAnimation(this, R.anim.shake_and_flash_animation);
        editText.startAnimation(flash);
    }
    @Override
    public void endGame() {
        handler.removeCallbacksAndMessages(null); // Remove any pending callbacks
        showWinnerDialog(username, playerBoard.getGrid());
        addToLeaderboard(username, points);

    }


    private void showWinnerDialog(String winner, String[][] winnerBoard) {
        // Create and show the custom dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.end_game_popup, null);
        builder.setView(dialogView);

        // Set Winner Text
        TextView winnerText = dialogView.findViewById(R.id.winnerText);
        winnerText.setText("Player " + winner + " wins");  // Change 'Player X' dynamically based on game result

        // Set Time Remaining Text
        TextView timeRemainingText = dialogView.findViewById(R.id.timeRemainingText);
        timeRemainingText.setText("Time Remaining: " + timerTextView.getText().toString());

        TextView pointsText = dialogView.findViewById(R.id.pointsTextView);
        pointsText.setText("Points: " + String.valueOf(points));

        // Populate the winner's board grid
        int[] cellIds = {
                R.id.r1c1, R.id.r1c2, R.id.r1c3,
                R.id.r2c1, R.id.r2c2, R.id.r2c3,
                R.id.r3c1, R.id.r3c2, R.id.r3c3
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextView cell = dialogView.findViewById(cellIds[i * 3 + j]);
                cell.setText(winnerBoard[i][j]);
            }
        }
        TextView cell = dialogView.findViewById(R.id.Col1);
        cell.setText(col1.getText());
        TextView cell2 = dialogView.findViewById(R.id.Col2);
        cell2.setText(col2.getText());
        TextView cell3 = dialogView.findViewById(R.id.Col3);
        cell3.setText(col3.getText());
        TextView cell4 = dialogView.findViewById(R.id.Row1);
        cell4.setText(row1.getText());
        TextView cell5 = dialogView.findViewById(R.id.Row2);
        cell5.setText(row2.getText());
        TextView cell6 = dialogView.findViewById(R.id.Row3);
        cell6.setText(row3.getText());


        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Add restart button functionality
        Button restartButton = dialogView.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart your game here
                correctGuesses = 0;
                alertDialog.dismiss();
                startActivity(new Intent(SinglePlayerGame.this,
                        MainActivity.class));
            }
        });

        Button showScore = dialogView.findViewById(R.id.showScoreButton);
        showScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart your game here
                correctGuesses = 0;
                alertDialog.dismiss();
                startActivity(new Intent(SinglePlayerGame.this,
                        LeaderboardActivity.class));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Remove any callbacks and messages from the handler
    }
    //Backend Support
    private void fetchCategories() {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/categories"; // Replace with your actual backend server URL

        // StringRequest for fetching a string response from the given URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response to a List of Maps to represent the JSON objects
                        categories = parseCategories(response);
                        // Use the fetched categories to set up the game
                        setUpGameBoard(categories);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
                showErrorDialog();
            }
        });

        queue.add(stringRequest); // Add the request to the RequestQueue
    }
    private List<Map<String, String>> parseCategories(String jsonResponse) {
        List<Map<String, String>> categoryList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                String categoryJson = jsonArray.getString(i);
                JSONObject jsonObject = new JSONObject(categoryJson);
                Map<String, String> categoryMap = new HashMap<>();
                categoryMap.put("text", jsonObject.getString("text"));
                categoryMap.put("subject", jsonObject.getString("subject"));
                categoryMap.put("check", jsonObject.getString("check"));
                categoryMap.put("keyword", jsonObject.getString("keyword"));
                categoryList.add(categoryMap);
            }
        } catch (JSONException e) {
            // Handle the exception
        }
        return categoryList;
    }
    private void setUpGameBoard(List<Map<String, String>> categories) {
        // Assuming you have a layout or a way to set categories to your game board
        row1.setText(categories.get(0).get("text"));
        row2.setText(categories.get(1).get("text"));
        row3.setText(categories.get(2).get("text"));

        // Set up columns with the next three categories
        col1.setText(categories.get(3).get("text"));
        col2.setText(categories.get(4).get("text"));
        col3.setText(categories.get(5).get("text"));
        // Indicate that categories are loaded and start the game
        categoriesLoaded = true;
        startGame();
    }

    private void showErrorDialog() {
        // Show an error dialog to the user
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Could not fetch categories from the server.")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void startGame() {
        if (categoriesLoaded) {
            Timer(); // Start the timer
            setPoints(); // Set initial points
        }
    }
    // Update the setEditTextListener to use the new checkAnswer method with a callback
    private void setEditTextListener(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // Get the relevant tag data
                    String tag = (String) editText.getTag();
                    String[] parts = tag.split(",");
                    final int row = Integer.parseInt(parts[0]);
                    final int column = Integer.parseInt(parts[1]);
                    String check1 = "";
                    String check2 = "";
                    final String userAnswer = editText.getText().toString().trim();
                    if(row == 1){
                        check1 = categories.get(0).get("keyword");
                    } else if (row == 2) {
                        check1 = categories.get(1).get("keyword");
                    }else if (row == 3){
                        check1 = categories.get(2).get("keyword");
                    }

                    if(column == 1){
                        check2 = categories.get(3).get("keyword");
                    } else if (column == 2) {
                        check2 = categories.get(4).get("keyword");
                    }else if (column == 3){
                        check2 = categories.get(5).get("keyword");
                    }

                    // Make the backend call and pass the callback
                    checkIfArtistAndSongContains(userAnswer, check1, check2, new AnswerCheckCallback() {
                        @Override
                        public void onResult(boolean isCorrect) {
                            if (isCorrect) {
                                updatePlayerBoard(editText, userAnswer);
                                changeBoxColor(editText, true);
                                editText.setEnabled(false);  // Disable the EditText
                                correctGuesses++; // Increment the counter for correct answers
                                points = points + 15;
                                setPoints();
                                if(correctGuesses == TOTAL_EDIT_TEXTS) {
                                    points = points + seconds;
                                    setPoints();
                                    endGame();  // End the game if all answers are correct
                                }
                            } else {
                                changeBoxColor(editText, false);
                                points = points - 5;
                                setPoints();
                            }
                        }
                    });
                    return true;
                }
                return false;
            }
        });
    }

    // Define a callback interface for response handling
    interface AnswerCheckCallback {
        void onResult(boolean isCorrect);
    }
    // Modified checkAnswer method with network call
    public void checkAnswer(String subject, String check, int col, int row) {
        if (subject == "Artist"){
            if(check == "with"){

            } else if (check == "features") {

            }
        }
        if(subject == "Artist"){
            if(check == "features"){

            }
        }
    }
    public void checkAnswer(String subject, String subject2, String check, String check2, int col, int row){

    }
    private void checkIfArtistAndSongContains(String userAnswer, String check1, String check2, AnswerCheckCallback callback){
        // Assuming 'userAnswer' contains the name to be checked
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + userAnswer + "/artist/" + check1 + "/songs/" + check2;
        // Create a StringRequest for the network call
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Convert the response string to a JSONObject
                            JSONObject jsonObject = new JSONObject(response);
                            // Extract the message value
                            String resultMessage = jsonObject.optString("message", "failure");
                            // Check if the message indicates a success
                            boolean result = "success".equalsIgnoreCase(resultMessage);
                            // Invoke the callback with the result
                            callback.onResult(result);
                        } catch (JSONException e) {
                            // If there's an error parsing the JSON, treat it as a failure
                            callback.onResult(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error, maybe invoke the callback with 'false'
                callback.onResult(false);
            }
        });

        // Add the request to your RequestQueue
        // Assuming 'queue' is an instance of RequestQueue
        queue.add(stringRequest);
    }
    private void checkArtistFeature(int artistId, String feature, AnswerCheckCallback callback ) { //Artist id will be changed
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + artistId + "/features/" + feature; //Artist id will be changed
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Convert the response string to a JSONObject
                            JSONObject jsonObject = new JSONObject(response);
                            // Extract the message value
                            String resultMessage = jsonObject.optString("message", "failure");
                            // Check if the message indicates a success
                            boolean result = "success".equalsIgnoreCase(resultMessage);
                            // Invoke the callback with the result
                            callback.onResult(result);
                        } catch (JSONException e) {
                            // If there's an error parsing the JSON, treat it as a failure
                            callback.onResult(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error, maybe invoke the callback with 'false'
                callback.onResult(false);
            }
        });

        // Add the request to your RequestQueue
        // Assuming 'queue' is an instance of RequestQueue
        queue.add(stringRequest);
    }
    private void checkIfArtistsHaveSongTogether(int artistId1, int artistId2, AnswerCheckCallback callback) { //Artist id will be changed
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + artistId1 + "/artists2/" + artistId2; //Artist id will be changed
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Convert the response string to a JSONObject
                            JSONObject jsonObject = new JSONObject(response);
                            // Extract the message value
                            String resultMessage = jsonObject.optString("message", "failure");
                            // Check if the message indicates a success
                            boolean result = "success".equalsIgnoreCase(resultMessage);
                            // Invoke the callback with the result
                            callback.onResult(result);
                        } catch (JSONException e) {
                            // If there's an error parsing the JSON, treat it as a failure
                            callback.onResult(false);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error, maybe invoke the callback with 'false'
                callback.onResult(false);
            }
        });

        // Add the request to your RequestQueue
        // Assuming 'queue' is an instance of RequestQueue
        queue.add(stringRequest);
    }
    private void addToLeaderboard(String username, int score) {
        // URL of the API to add a new player
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

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
            );

            // Add the request to the Volley request queue
            Volley.newRequestQueue(this).add(request);
        } catch (JSONException e) {

        }
    }

}