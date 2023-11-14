package com.example.sumon.androidvolley;

/**
 * The  PlayerData class represents player data in the Android application.
 * It includes the player's username and score.
 *
 */
public class PlayerData {
    private String username;
    private int score;
    /**
     * Constructs a new PlayerData object with the specified username and score.
     *
     * @param username The username of the player.
     * @param score    The score of the player.
     */
    public PlayerData(String username, int score) {
        this.username = username;
        this.score = score;
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
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the player.
     *
     * @param score The new score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }


}
