package com.example.sumon.androidvolley;
/**
 * Represents the state of a game, including details about the lobby, players, and their respective game boards.
 * It also tracks whether the game has been won or not.
 */
public class GameState {
    private String lobbyName;
    private String player1Username;
    private String player2Username;
    private PlayerBoard player1Board;
    private PlayerBoard player2Board;
    private boolean isGameWon;
    private String[] Columns;

    private String[] Rows;



    /**
     * Constructs a new GameState object.
     *
     * @param lobbyName Name of the game lobby.
     * @param player1Username Username of the first player.
     * @param player2Username Username of the second player.
     * @param columns Array representing the columns of the game board.
     * @param rows Array representing the rows of the game board.
     */
    public GameState(String lobbyName, String player1Username, String player2Username, String[] columns, String[] rows) {
        this.lobbyName = lobbyName;
        this.player1Username = player1Username;
        this.player2Username = player2Username;
        this.isGameWon = false;
        for(int i=0;i<columns.length;i++){
            Columns[i]=columns[i];
        }
        for(int i=0;i<rows.length;i++){
            Rows[i]=rows[i];
        }
    }

    /**
     * Gets the lobby name.
     * @return The name of the lobby.
     */
    public String getLobbyName() {
        return lobbyName;
    }
    /**
     * Returns a column based on the given index.
     * @param i The index of the column.
     * @return The column at the specified index.
     */
    public String column(int i) {
        return Columns[i];
    }
    /**
     * Returns a row based on the given index.
     * @param i The index of the row.
     * @return The row at the specified index.
     */
    public String row(int i) {
        return Rows[i];
    }
    /**
     * Sets the lobby name.
     * @param lobbyName The name to set for the lobby.
     */
    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }
    /**
     * Gets the username of player 1.
     * @return The username of player 1.
     */
    public String getPlayer1Username() {
        return player1Username;
    }
    /**
     * Sets the username of player 1.
     * @param player1Username The username to set for player 1.
     */
    public void setPlayer1Username(String player1Username) {
        this.player1Username = player1Username;
    }
    /**
     * Gets the username of player 2.
     * @return The username of player 2.
     */
    public String getPlayer2Username() {
        return player2Username;
    }
    /**
     * Sets the username of player 2.
     * @param player2Username The username to set for player 2.
     */
    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }
    /**
     * Gets the board of player 1.
     * @return player1Board The board for player 1.
     */
    public PlayerBoard getPlayer1Board() {
        return player1Board;
    }
    /**
     * Sets the board of player 1.
     * @param player1Board The board for player 1.
     */
    public void setPlayer1Board(PlayerBoard player1Board) {
        this.player1Board = player1Board;
    }
    /**
     * Gets the board of player 2.
     * @return player1Board The board for player 2.
     */
    public PlayerBoard getPlayer2Board() {
        return player2Board;
    }
    /**
     * Sets the board of player 2.
     * @param player2Board The board for player 2.
     */
    public void setPlayer2Board(PlayerBoard player2Board) {
        this.player2Board = player2Board;
    }
    /**
     * Checks if the game has been won.
     * @return true if the game has been won, false otherwise.
     */
    public boolean isGameWon() {
        return isGameWon;
    }
    /**
     * Sets the game won status.
     * @param gameWon The status to set for the game.
     */
    public void setGameWon(boolean gameWon) {
        isGameWon = gameWon;
    }
    /**
     * Provides a string representation of the GameState.
     * @return String representation of the GameState.
     */
    @Override
    public String toString() {
        return "GameState{" +
                "lobbyName='" + lobbyName + '\'' +
                ", player1Username='" + player1Username + '\'' +
                ", player2Username='" + player2Username + '\'' +
                ", player1Board=\n" + player1Board +
                ", player2Board=\n" + player2Board +
                ", isGameWon=" + isGameWon +
                '}';
    }

}

