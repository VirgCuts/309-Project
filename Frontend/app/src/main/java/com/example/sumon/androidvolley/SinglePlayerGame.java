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
import android.view.inputmethod.EditorInfo;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the single-player game activity where a player interacts with the game board,
 * answers questions, and competes against the timer to earn points.
 *
 * This class implements both the GameViewInterface and GameControllerInterface
 * interfaces, providing methods for handling UI updates and game logic.
 */
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


    private String Player1 = "Carter", selected="white";

    /**
     * Initializes the activity, sets up the UI elements, and fetches categories from the backend.
     *
     */
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

        playerBoard = new PlayerBoard();

        getSelectColor();


        endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });
        timerTextView = findViewById(R.id.timer);
    }
    //calls to backend to see if player has a selected background gets string of color
    /**
     * Calls the backend to retrieve the background color for the player and updates the board accordingly.
     *
     * @param color The string representation of the selected background color.
     */
    public void getBackground(String color) {
        Log.d("BCKCOL",color);
        if(color.equals("orange")) {
            changeBoardColor("orange");
        }
        else if(color.equals("purple")) {
            changeBoardColor("purple");
        }
        else if(color.equals("lightblue")) {
            changeBoardColor("lightblue");
        }
        else if(color.equals("yellow")) {
            changeBoardColor("yellow");
        }
        else if(color.equals("magenta")) {
            changeBoardColor("magenta");
        }
        else if(color.equals("green")) {
            changeBoardColor("green");
        }
    }
    /**
     * Calls the backend to get the selected background color for the player.
     */
    public void getSelectColor() {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/gameColor/"+Player1;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SINGLEGET",response.toString());
                        String value = response.toString();
                        String[] splitValue = value.split(":");
                        value = splitValue[1];
                        value = value.replace("}", "");
                        value = value.replace("\"", "");
                        Log.d("GET", value);
                        getBackground(value);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERRGET",error.toString());

                    }
                }
        );
        Volley.newRequestQueue(this).add(request);
    }
    /**
     * Sets the background color of the given EditText based on the specified color.
     *
     * @param editText The EditText to set the background color for.
     * @param color    The string representation of the selected background color.
     */
    public void setBackColor(EditText editText, String color) {
        int colorValue = 0;
        switch (color) {
            case "orange":
                colorValue = getResources().getColor(R.color.boardColorO);
                break;
            case "purple":
                colorValue = getResources().getColor(R.color.boardColorP);
                break;
            case "lightblue":
                colorValue = getResources().getColor(R.color.boardColorLB);
                break;
            case "yellow":
                colorValue = getResources().getColor(R.color.boardColorY);
                break;
            case "magenta":
                colorValue = getResources().getColor(R.color.boardColorM);
                break;
            case "green":
                colorValue = getResources().getColor(R.color.boardColorG);
                break;
        }
        int semiTransparentColor = Color.argb(128, Color.red(colorValue), Color.green(colorValue), Color.blue(colorValue));
        ColorFilter colorFilter = new PorterDuffColorFilter(semiTransparentColor, PorterDuff.Mode.SRC_ATOP);
        editText.getBackground().mutate().setColorFilter(colorFilter);
    }
    /**
     * Changes the background color of the entire board based on the specified color.
     *
     * @param color The string representation of the selected background color.
     */
    public void changeBoardColor(String color) {
        setBackColor(r1c1, color);
        setBackColor(r1c2, color);
        setBackColor(r1c3, color);
        setBackColor(r2c1, color);
        setBackColor(r2c2, color);
        setBackColor(r2c3, color);
        setBackColor(r3c1, color);
        setBackColor(r3c2, color);
        setBackColor(r3c3, color);
    }
    /**
     * Changes the background color of a single EditText based on the specified color.
     *
     * @param editText The EditText to change the background color for.
     * @param color    The string representation of the selected background color.
     */

    /**
     * Implements the countdown timer for the game, updating the UI and handling the end of the game.
     */
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
    /**
     * Retrieves the username from SharedPreferences.
     *
     * @param context The context used to access SharedPreferences.
     * @return The username stored in SharedPreferences.
     */
    public String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String username = prefs.getString(USERNAME_KEY, null); // Return null if not found

        // Debugging log
        Log.d("SharedPreferences", "Retrieving username: " + username);

        return username;
    }
    /**
     * Sets the text of the given TextView with the specified text.
     *
     * @param textView The TextView to set the text for.
     * @param text     The text to be set on the TextView.
     */
    @Override
    public void setBoxText(TextView textView, String text) {
        textView.setText(text);
    }
    /**
     * Sets the points in the UI by updating the Points TextView.
     */
    public void setPoints() {
        TextView pointView = findViewById(R.id.Points);
        pointView.setText("Points: "+ points);
    }
    /**
     * Changes the background color of the provided EditText based on whether the answer is correct.
     *
     * @param editText   The EditText to change the color of.
     * @param isCorrect  True if the answer is correct, false otherwise.
     */
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
    /**
     * Animates a flash effect on the provided EditText.
     *
     * @param editText The EditText to apply the flash animation to.
     */
    private void animateFlash(EditText editText) {
        Animation flash = AnimationUtils.loadAnimation(this, R.anim.shake_and_flash_animation);
        editText.startAnimation(flash);
    }
    /**
     * Ends the game by stopping the timer, showing the winner dialog, and updating the leaderboard.
     */
    @Override
    public void endGame() {
        handler.removeCallbacksAndMessages(null); // Remove any pending callbacks
        showWinnerDialog(username, playerBoard.getGrid());
        addToLeaderboard(username, points);

    }

    /**
     * Shows a custom dialog displaying the winner, time remaining, points, and the final game board.
     *
     * @param winner      The username of the winner.
     * @param winnerBoard The final game board of the winner.
     */
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

    /**
     * Called when the activity is being destroyed.
     * Removes any pending callbacks and messages from the handler to prevent memory leaks.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null); // Remove any callbacks and messages from the handler
    }
    /**
     * Fetches categories from the backend server and sets up the game board.
     */
    private void fetchCategories() {
        String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/categories"; // Replace with your actual backend server URL

        // StringRequest for fetching a string response from the given URL
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Parse the response to a List of Maps to represent the JSON objects
                        categories = parseCategories(response);
                        Log.d("Categories", response);
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
    /**
     * Parses the JSON response into a List of Maps representing categories.
     *
     * @param jsonResponse The JSON response from the server.
     * @return A List of Maps containing category information.
     */
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
    /**
     * Sets up the game board with the provided categories.
     *
     * @param categories A List of Maps containing category information.
     */
    private void setUpGameBoard(List<Map<String, String>> categories) {
        if (categories == null || categories.size() < 6) {
            Log.e("setUpGameBoard", "Categories have not been loaded properly.");
            showErrorDialog();
            return;
        }
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
    /**
     * Displays an error dialog to the user when categories cannot be fetched.
     */
    private void showErrorDialog() {
        // Show an error dialog to the user
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Could not fetch categories from the server.")
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    /**
     * Starts the game by initializing the timer and setting initial points.
     */
    private void startGame() {
        if (categoriesLoaded) {
            Timer(); // Start the timer
            setPoints(); // Set initial points
        }
    }
    /**
     * Sets an Listener for the given EditText.
     * Handles user input, checks answers, and updates the game state accordingly.
     *
     * @param editText The EditText to set the listener for.
     */
    private void setEditTextListener(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getAction() == KeyEvent.ACTION_DOWN && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_TAB))
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_NEXT){
                    // Get the relevant tag data
                    String tag = (String) editText.getTag();
                    String[] parts = tag.split(",");
                    final int row = Integer.parseInt(parts[0]);
                    final int column = Integer.parseInt(parts[1]);
                    final String userAnswer = editText.getText().toString().trim();
                    checkAnswer(editText, userAnswer, row, column);
                    Log.d("This","This line has been reached");
                    return true;
                }
                return false;
            }
        });
    }

    // Define a callback interface for response handling

    interface AnswerCheckCallback {
        void onResult(boolean isCorrect, EditText editText);
    }
    private void performCheck(String subject, String check, String keyword, String userAnswer, AnswerCheckCallback callback, EditText editText) {
        switch (check) {
            case "featuring":
                if (subject.equals("Artist")) {
                    checkIfArtistFeaturing(userAnswer, keyword, callback, editText);
                }
                break;
            case "on":
                if (subject.equals("Artist")) {
                    getArtistOnAlbum(userAnswer, keyword, callback, editText);
                }
                break;
            case "with":
                if (subject.equals("Artist")) {
                    checkIfArtistsHaveSongTogether(userAnswer, keyword, callback, editText);
                }
                break;
            default:
                // Handle default case or unknown check
                break;
        }
    }
    // Modified checkAnswer method with network call
    public void checkAnswer(EditText editText, String userAnswer, int row, int col) {
        String colSubject = categories.get(2 + col).get("subject");
        String rowSubject = categories.get(row).get("subject");
        String colCheck = categories.get(2 + col).get("check");
        String rowCheck = categories.get(row).get("check");
        String colKeyword = categories.get(2 + col).get("keyword");
        String rowKeyword = categories.get(row).get("keyword");
        performCheck(rowSubject, rowCheck, rowKeyword, userAnswer, new AnswerCheckCallback() {
            @Override
            public void onResult(boolean rowIsCorrect, EditText editText) {
                performCheck(colSubject, colCheck, colKeyword, userAnswer, new AnswerCheckCallback() {
                    @Override
                    public void onResult(boolean colIsCorrect, EditText editText) {
                        if (rowIsCorrect && colIsCorrect) {
                            Log.d("YES","yes");
                            changeBoxColor(editText, true);
                            editText.setEnabled(false);
                            correctGuesses++;
                            points += 15;
                            setPoints();
                            if (correctGuesses == TOTAL_EDIT_TEXTS) {
                                points += seconds;
                                setPoints();
                                endGame();
                            }
                        } else {
                            // Update the UI for incorrect answer
                            Log.d("NO","no");
                            changeBoxColor(editText, false);
                            points -= 5;
                            setPoints();
                        }
                    }
                }, editText);
            }
        }, editText);
    }

    /**
     * Checks if two artists have a song together based on their IDs.
     *
     * @param artist1  The name of the first artist.
     * @param artist2  The name of the second artist.
     * @param callback   The callback to handle the result.
     */
    private void checkIfArtistsHaveSongTogether(String artist1, String artist2, AnswerCheckCallback callback, final EditText editText) {


            // Update the URL to use names instead of IDs
            String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + artist1 + "/with/" + artist2;

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
                                callback.onResult(result, editText);
                            } catch (JSONException e) {
                                // If there's an error parsing the JSON, treat it as a failure
                                callback.onResult(false, editText);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 500) {
                        // Handle the 500 error as a wrong guess
                        callback.onResult(false, editText);
                    } else {
                        error.printStackTrace();
                        // Handle other errors
                    }

                }
            });

            // Add the request to your RequestQueue
            queue.add(stringRequest);
    }

    /**
     * Checks if two artists have a song together based on their IDs.
     *
     * @param artistName  The name of the first artist.
     * @param featuredArtist  The name of the featured artist.
     * @param callback   The callback to handle the result.
     */
    private void checkIfArtistFeaturing(String artistName, String featuredArtist, AnswerCheckCallback callback, final EditText editText) {


            // Construct the URL for the request
            String url = "http://coms-309-022.class.las.iastate.edu:8080/artists/" + artistName + "/featuring/" + featuredArtist;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                // Parse the JSON response
                                JSONObject jsonObject = new JSONObject(response);
                                // Extract the message value
                                String resultMessage = jsonObject.optString("message", "failure");
                                // Check if the message indicates a success
                                boolean result = "success".equalsIgnoreCase(resultMessage);
                                // Invoke the callback with the result
                                callback.onResult(result, editText);
                            } catch (JSONException e) {
                                // Handle JSON parsing error
                                callback.onResult(false, editText);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 500) {
                        // Handle the 500 error as a wrong guess
                        callback.onResult(false, editText);
                    } else {
                        error.printStackTrace();
                        // Handle other errors
                    }
                }
            });

            // Add the request to your RequestQueue
            queue.add(stringRequest);

    }
    private void getArtistOnAlbum(String artistName, String albumName, AnswerCheckCallback callback, final EditText editText) {

            String baseUrl = "http://coms-309-022.class.las.iastate.edu:8080";
            String endpoint = "/artists/" + artistName + "/on/" + albumName;

            String url = baseUrl + endpoint;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                // Parse the JSON response
                                JSONObject jsonObject = new JSONObject(response);
                                // Extract the message value
                                String resultMessage = jsonObject.optString("message", "failure");
                                // Check if the message indicates a success
                                boolean result = "success".equalsIgnoreCase(resultMessage);
                                // Invoke the callback with the result
                                callback.onResult(result, editText);
                            } catch (JSONException e) {
                                callback.onResult(false, editText);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error.networkResponse != null && error.networkResponse.statusCode == 500) {
                                // Handle the 500 error as a wrong guess
                                callback.onResult(false, editText);
                            } else {
                                error.printStackTrace();
                                // Handle other errors
                            }

                        }
                    }
            );
            // Add the request to the RequestQueue
            queue.add(stringRequest);

    }

    /**
     * Adds a player's score to the leaderboard.
     *
     * @param username The username of the player.
     * @param score    The score to be added to the leaderboard.
     */
    private void addToLeaderboard(String username, int score) {
        // URL of the API to add a new player
        String url = "http://coms-309-022.class.las.iastate.edu:8080/leaderboard/" + username + "/" + score;

        try {
            JSONObject requestBody = new JSONObject();
            requestBody.put("name", username);
            requestBody.put("highScore", score);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
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