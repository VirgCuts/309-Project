package com.example.sumon.androidvolley;

import android.widget.EditText;
import android.widget.TextView;
/**
 * Interface defining the required methods for a game view.
 * It includes methods for handling timer, setting text in a box, and changing the color of a box based on game logic.
 */
public interface GameViewInterface {

    /**
     * Handles the game timer functionality.
     * This method should implement the logic to manage the timer in the game, like starting, stopping, or updating the timer display.
     */
    void Timer();



    /**
     * Sets the text content of a specified TextView box.
     * This method is typically used to set or update the value or information displayed in a box in the game view.
     * @param textView The TextView box whose text is to be set.
     * @param text The string to be displayed in the TextView box.
     */
    void setBoxText(TextView textView, String text);
    /**
     * Changes the color of a specified EditText box based on the correctness of the player's action.
     * This method can be used to visually indicate whether an action performed by the player is correct or incorrect.
     * @param editText The EditText box whose color is to be changed.
     * @param isCorrect A boolean indicating whether the player's action was correct (true) or incorrect (false).
     */
    void changeBoxColor(EditText editText, boolean isCorrect);


}
