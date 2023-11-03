package com.example.sumon.androidvolley;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.Handler;
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
    private GameState gameState;
    private PlayerBoard playerBoard;
    private int correctGuesses = 0;
    private final int TOTAL_EDIT_TEXTS = 9;

    private int[] cellIds;

    private EditText[] allEditTexts;

    private RequestQueue queue;

    private EditText r1c1,r1c2,r1c3,r2c1,r2c2,r2c3,r3c1,r3c2,r3c3;

    private TextView col1,col2,col3,row1,row2,row3;

    String[] col;
    String[] row;
    private boolean categoriesLoaded = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player);

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


        fetchCategories();

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

        allEditTexts = new EditText[9];
        allEditTexts[0] = r1c1;
        allEditTexts[1] = r1c2;
        allEditTexts[2] = r1c3;
        allEditTexts[3] = r2c1;
        allEditTexts[4] = r2c2;
        allEditTexts[5] = r2c3;
        allEditTexts[6] = r3c1;
        allEditTexts[7] = r3c2;
        allEditTexts[8] = r3c3;


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
    private void updatePlayerBoard(EditText editText, String answer) {
        // Here, we'll determine the position of the editText and update the playerBoard.
        // For simplicity, I'm assuming the EditTexts' ids are stored in an array.
        int[] cellIds = {
                R.id.r1c1, R.id.r1c2, R.id.r1c3,
                R.id.r2c1, R.id.r2c2, R.id.r2c3,
                R.id.r3c1, R.id.r3c2, R.id.r3c3
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(cellIds[i * 3 + j] == editText.getId()) {
                    playerBoard.edit(i, j, answer);
                    return;
                }
            }
        }
    }
    //CheckAnswer with no backend
    public boolean checkAnswer(EditText editText, String userAnswer) {
        String enteredText = editText.getText().toString().trim(); // Get the text entered in the EditText and remove leading/trailing spaces
        return enteredText.equalsIgnoreCase(userAnswer); // Compare the entered text with the userAnswer (case-insensitive)
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
        showWinnerDialog("User", playerBoard.getGrid());
    }
    //SetEditText with no backend
    private void setEditTextListener(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // you may need a way to determine which answer to check against based on the EditText
                    String tag = (String) editText.getTag();
                    String[] parts = tag.split(",");
                    int row = Integer.parseInt(parts[0]);
                    int column = Integer.parseInt(parts[1]);
                    String userAnswer = "1";

                    if (checkAnswer(editText, userAnswer)) {
                        updatePlayerBoard(editText, userAnswer);
                        changeBoxColor(editText, true);
                        editText.setEnabled(false);  // Disable the EditText
                        correctGuesses++; // Increment the counter for correct answers
                        if(correctGuesses == TOTAL_EDIT_TEXTS) {
                            endGame();  // End the game if all answers are correct
                        }
                    } else {
                        changeBoxColor(editText, false);
                    }

                    return true;  // Consume the event
                }
                return false;  // Let system handle the event
            }
        });
    }
    private String extractKeyword(String category) {
        Pattern pattern = Pattern.compile("'(.*?)'");
        Matcher matcher = pattern.matcher(category);
        if (matcher.find()) {
            return matcher.group(1); // This will return the word between the single quotes
        }
        return ""; // Return empty if no match is found
    }


    //NOTE: winnerBoard will be changed to a new custom class called Board
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
                        List<Map<String, String>> categories = parseCategories(response);
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
        for (int i = 0; i < categories.size(); i++) {
            Map<String, String> category = categories.get(i);
            // Here, use the category map to set up your game board
            row1.setText(category.get("text"));
            row2.setText(category.get("text"));
            row3.setText(category.get("text"));
            col1.setText(category.get("text"));
            col2.setText(category.get("text"));
            col3.setText(category.get("text"));

        }
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
}