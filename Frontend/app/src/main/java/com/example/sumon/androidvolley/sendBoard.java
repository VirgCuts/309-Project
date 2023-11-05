package com.example.sumon.androidvolley;

import android.util.Log;

public class sendBoard {
    private final int BOARD_SIZE = 3;
    private String[][] griddle;
    private String[][] col;

    private String[][] row;

    public sendBoard() {
        griddle = new String[BOARD_SIZE][BOARD_SIZE];
        // Initialize the grid with empty strings
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(j == 0) {
                    griddle[i][j] = "[0,";
                }
                else if(i== 2 && j == 2) {
                    griddle[i][j] = "0]";
                }
                else if(j == 2) {
                    griddle[i][j] = "0],";
                }
                else {
                    griddle[i][j] = "0,";
                }
            }
        }

        col = new String[3][4];
        row = new String[3][4];
    }

    public sendBoard(PlayerBoard original) {
        this.griddle = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.griddle[i][j] = original.griddle[i][j];
            }
        }
    }

    /**
     * Returns the value at a specific position in the grid.
     *
     * @param row - The row of the desired position.
     * @param col - The column of the desired position.
     * @return The value at the specified position.
     */
    public String get(int row, int col) {
        if (isValidPosition(row, col)) {
            return griddle[row][col];
        }
        return null; // Or throw an exception
    }

    /**
     * Edits the value at a specific position in the grid.
     *
     * @param row - The row of the desired position.
     * @param col - The column of the desired position.
     * @param value - The new value to set.
     */

    public void edit(int row, int col, String value) {
        if (isValidPosition(row, col)) {
            if(col == 0) {
                griddle[row][col] = "[1,";
            }
            else if(row == 2 && col == 2) {
                griddle[row][col] = "1]";
            }
            else if(col == 2) {
                griddle[row][col] = "1],";
            }
            else {
                griddle[row][col] = "1,";
            }
        } else {
            // Handle invalid positions (e.g., throw an exception or print an error)
            System.out.println("Invalid position!");
        }
    }

    public void editCol(int c, String text, String subject, String check, String keyword) {
        col[c][0] = text;
        col[c][1] = subject;
        col[c][2] = check;
        col[c][3] = keyword;
    }

    public void editRow(int c, String text, String subject, String check, String keyword) {
        row[c][0] = text;
        row[c][1] = subject;
        row[c][2] = check;
        row[c][3] = keyword;
    }

    public String getCol(int co, int check) {
        return col[co][check];
    }

    public String getRow(int ro, int check) {
        return row[ro][check];
    }


    /**
     * Checks if the given position is valid in the grid.
     *
     * @param row - The row to check.
     * @param col - The column to check.
     * @return true if valid, false otherwise.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }

    public String[][] getGrid() {

        return griddle;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                String newValue = griddle[i][j];
                sb.append(newValue).append("");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}

