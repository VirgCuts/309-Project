package com.example.game.gameFiles;

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
    /*
        Method that sets the side value of a box so example would be has a platinum etc.
     */
    void setBoxText(int x, int y, String text);
    /*
        Method to handle when a artist is found and is correct, should change the box
        from whatever the base color is to another
     */
    void changeBoxColor(int x, int y, boolean isCorrect);
    /*
        Method to handle when a box is clicked for text to be entered, should bring up a text
        prompt and likely call changeBoxColor
     */
    void showMessageBox(String message);

}
