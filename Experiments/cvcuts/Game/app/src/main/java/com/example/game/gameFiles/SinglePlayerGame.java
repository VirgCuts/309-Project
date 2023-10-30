package com.example.game.gameFiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.R;

public class SinglePlayerGame extends AppCompatActivity implements GameViewInterface, GameControllerInterface {
    private int correctGuesses;
    private int totalGuesses;
    private long startTime;
    private long endTime;
    private TextView timerTextView;

    private Button endGameButton;
    private Handler handler = new Handler();
    private int seconds = 0;

    private int points = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_player);

        EditText r1c2 = findViewById(R.id.r1c2);
        EditText r1c3 = findViewById(R.id.r1c3);
        EditText r2c1 = findViewById(R.id.r2c1);
        EditText r2c2 = findViewById(R.id.r2c2);
        EditText r2c3 = findViewById(R.id.r2c3);
        EditText r3c1 = findViewById(R.id.r3c1);
        EditText r3c2 = findViewById(R.id.r3c2);
        EditText r3c3 = findViewById(R.id.r3c3);
        EditText r1c1 = findViewById(R.id.r1c1);



        //This is just used to test the end screen
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
    public void initializeGrid() {
        // Initialize the 3x3 grid with random data so setting sides.
    }

    @Override
    public void Timer() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                seconds++;

                int minutes = seconds / 60;
                int remainingSeconds = seconds % 60;

                // Format the time as "mm:ss"
                String time = String.format("%d:%02d", minutes, remainingSeconds);
                timerTextView.setText(time);

                if (seconds < 300) {
                    handler.postDelayed(this, 1000); // Continue updating every second
                }
                else {
                    timerTextView.setText("Time Over");
                }
            }
        });
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
            // Set the background color to green for a correct answer
            editText.setBackgroundResource(R.drawable.edit_text_correct);
        } else {
            // If we want some sort of incorrect would change color here
        }
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
        // End the game, display final score, stop the timer, etc.
        //The following board is just for testing
        String[][] board = {{"Kanye","Kanye","Kanye"},{"Kanye","Kanye","Kanye"},{"Kanye","Kanye","Kanye"}};
        showWinnerDialog("1",board);
    }
    @Override
    public void showEndGameScore(int score) {
        // Show the player's final score
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
                alertDialog.dismiss();
            }
        });
    }
}