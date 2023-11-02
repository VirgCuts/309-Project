package com.example.sumon.androidvolley;

public class GameState {
    private String lobbyName;
    private String player1Username;
    private String player2Username;
    private PlayerBoard player1Board;
    private PlayerBoard player2Board;
    private boolean isGameWon;
    private String[] Columns;

    private String[] Rows;




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

    // Getters and setters for each attribute:

    public String getLobbyName() {
        return lobbyName;
    }

    public String column(int i) {
        return Columns[i];
    }

    public String row(int i) {
        return Rows[i];
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getPlayer1Username() {
        return player1Username;
    }

    public void setPlayer1Username(String player1Username) {
        this.player1Username = player1Username;
    }

    public String getPlayer2Username() {
        return player2Username;
    }

    public void setPlayer2Username(String player2Username) {
        this.player2Username = player2Username;
    }

    public PlayerBoard getPlayer1Board() {
        return player1Board;
    }

    public void setPlayer1Board(PlayerBoard player1Board) {
        this.player1Board = player1Board;
    }

    public PlayerBoard getPlayer2Board() {
        return player2Board;
    }

    public void setPlayer2Board(PlayerBoard player2Board) {
        this.player2Board = player2Board;
    }

    public boolean isGameWon() {
        return isGameWon;
    }

    public void setGameWon(boolean gameWon) {
        isGameWon = gameWon;
    }

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

