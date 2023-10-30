package com.example.game.gameFiles;

import android.widget.EditText;

public interface GameControllerInterface {
    /*
    begins processes of a game, initializes grid, starts counter, gets artists and side categories
     */
    void startGame();
    /*
    Handles when a box is clicked should prompt a text window.
     */
    void handleBoxClick(EditText editText);
    void handleUserInput(EditText editText, String userInput);
    /*
    queries to backend if the entered artist name is correct or not, returns true if
    it fulfills false else.
     */
    boolean checkAnswer(EditText editText, String userAnswer);
    void endGame();
}