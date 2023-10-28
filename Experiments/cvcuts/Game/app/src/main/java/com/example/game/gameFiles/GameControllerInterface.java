package com.example.game.gameFiles;

public interface GameControllerInterface {
    /*
    begins processes of a game, initializes grid, starts counter, gets artists and side catagories
     */
    void startGame();
    /*
    Handles when a box is clicked should prompt a text window.
     */
    void handleBoxClick(int x, int y);
    void handleUserInput(int x, int y, String userInput);
    /*
    queries to backend if the entered artist name is correct or not, returns true if
    it fulfills false else.
     */
    boolean checkAnswer(int x, int y, String userAnswer);
    void endGame();
}