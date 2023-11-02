package com.example.sumon.androidvolley;

import android.app.AlertDialog;
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



public class SinglePlayerGame extends AppCompatActivity implements GameViewInterface, GameControllerInterface {

    private int totalGuesses;
    private long startTime;
    private long endTime;
    private TextView timerTextView;

    private Button endGameButton;
    private Handler handler = new Handler();
    private int seconds = 240;

    private int points = 0;

    private GameState gameState;

    private PlayerBoard playerBoard;
    private int correctGuesses = 0;
    private final int TOTAL_EDIT_TEXTS = 9;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player);

        //NOTE: All of this information will have to come from websocket
        //connection to the backend
        String[] col = {"Has a grammy","Has a platinum record","Has a song with Kanye"};
        String[] row = {"Has a song with Drake","Has a song with Kid Cudi","Has a song produced my Pharrel Williams"};

        EditText r1c2 = findViewById(R.id.r1c2);
        EditText r1c3 = findViewById(R.id.r1c3);
        EditText r2c1 = findViewById(R.id.r2c1);
        EditText r2c2 = findViewById(R.id.r2c2);
        EditText r2c3 = findViewById(R.id.r2c3);
        EditText r3c1 = findViewById(R.id.r3c1);
        EditText r3c2 = findViewById(R.id.r3c2);
        EditText r3c3 = findViewById(R.id.r3c3);
        EditText r1c1 = findViewById(R.id.r1c1);

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

        playerBoard = new PlayerBoard();

        endGameButton = findViewById(R.id.endGameButton);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endGame();
            }
        });

        timerTextView = findViewById(R.id.timer);
        // Start the timer
        Timer();
        setPoints();
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
    @Override
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
            }, 280);
        }

        // Set a delay to revert the color

    }
    private void animateFlash(EditText editText) {
        Animation flash = AnimationUtils.loadAnimation(this, R.anim.shake_and_flash_animation);
        editText.startAnimation(flash);
    }

    @Override
    public void showMessageBox(String message) {
        // Show a message box to the user
    }

    @Override
    public void startGame() {
        // Start the game, initialize variables, calls timer, initialize grid, etc
    }

    @Override
    public void handleBoxClick(EditText editText) {
        // Handle grid box clicks
    }

    @Override
    public void handleUserInput(EditText editText, String userInput) {
        // Handle user's input, check correctness, update score
    }

    @Override
    public void endGame() {
        handler.removeCallbacksAndMessages(null); // Remove any pending callbacks
        showWinnerDialog("User", playerBoard.getGrid());
    }
    @Override
    public void showEndGameScore(int score) {
        // Show the player's final score
    }
    private void setEditTextListener(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    // you may need a way to determine which answer to check against based on the EditText
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

}