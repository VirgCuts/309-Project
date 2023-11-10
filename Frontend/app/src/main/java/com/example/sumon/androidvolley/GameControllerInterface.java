package com.example.sumon.androidvolley;

import android.widget.EditText;
/**
 * Interface for controlling game flow in an Android application.
 * This interface can be implemented by classes responsible for game logic.
 */
public interface GameControllerInterface {



    /**
     * Method to end the game.
     * Implementations should define the specific actions to be taken when the game ends,
     * such as displaying a score, updating the user interface, or cleaning up resources.
     */
    void endGame();
}