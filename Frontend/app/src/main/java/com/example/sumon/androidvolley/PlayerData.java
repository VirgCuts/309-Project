package com.example.sumon.androidvolley;

/**
 * The  PlayerData class represents player data in the Android application.
 * It includes the player's username and score.
 *
 */
public class PlayerData {
    private String username;
    private int highScore;
    private int highScoreMonthly;
    private int highScoreWeekly;

    public PlayerData(String username, int highScore, int highScoreMonthly, int highScoreWeekly) {
        this.username = username;
        this.highScore = highScore;
        this.highScoreMonthly = highScoreMonthly;
        this.highScoreWeekly = highScoreWeekly;
    }

    /**
     * Gets the username of the player.
     *
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the player.
     *
     * @param username The new username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the score of the player.
     *
     * @return The score of the player.
     */
    public int getHighScore() {
        return highScore;
    }
    public int getMonthlyScore() {
        return highScoreMonthly;
    }
    public int getWeeklyScore() {
        return highScoreWeekly;
    }


    /**
     * Sets the score of the player.
     *
     * @param score The new score to set.
     */
    public void setHighScore(int score) {
        this.highScore = score;
    }

    public void setMonthlyScore(int score) {
        this.highScoreMonthly = score;
    }
    public void setWeeklyScore(int score) {
        this.highScoreWeekly = score;
    }



}
