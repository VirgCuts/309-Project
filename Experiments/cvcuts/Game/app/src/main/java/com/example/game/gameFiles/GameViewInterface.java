package com.example.game.gameFiles;

import android.widget.EditText;
import android.widget.TextView;

public interface GameViewInterface {

    /*
    Should be called on game start to initialize the grid view
     */
    void initializeGrid();
    /*
    handles when box is clicked
     */
    void Timer();
    /*
    Displays final points and time to user also likely ceases the game.
     */
    void showEndGameScore(int score);

    // Implement methods from BackendInterface
    boolean checkAnswer(EditText editText, String userAnswer);

    /*
            Method that sets the side value of a box so example would be has a platinum etc.
         */
    void setBoxText(TextView textView, String text);
    /*
        Method to handle when a artist is found and is correct, should change the box
        from whatever the base color is to another
     */
    void changeBoxColor(EditText editText, boolean isCorrect);
    /*
        Method to handle when a box is clicked for text to be entered, should bring up a text
        prompt and likely call changeBoxColor
     */
    void showMessageBox(String message);

}
