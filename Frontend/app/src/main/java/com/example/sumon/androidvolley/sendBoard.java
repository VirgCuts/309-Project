package com.example.sumon.androidvolley;

import android.util.Log;

/**
 * The sendBoard class represents a board object used in the Android application.
 * It provides methods to retrieve, edit, and manipulate the board's grid, columns, and rows.
 * This class is designed for managing a 3x3 grid of booleans to be send to another user in Multiplayer.
 *
 */
public class sendBoard {
    private final int BOARD_SIZE = 3;
    private String[][] griddle;
    private String[][] col;

    private String[][] row;

    /**
     * Default constructor for the sendBoard class.
     * Initializes the grid with default values.
     */
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

//
//    /**
//     * Edits a column with the specified values.
//     *
//     * @param c       The column index to edit.
//     * @param text    The text value for the column.
//     * @param subject The subject value for the column.
//     * @param check   The check value for the column.
//     * @param keyword The keyword value for the column.
//     */
//    public void editCol(int c, String text, String subject, String check, String keyword) {
//        col[c][0] = text;
//        col[c][1] = subject;
//        col[c][2] = check;
//        col[c][3] = keyword;
//    }
//
//    /**
//     * Edits a row with the specified values.
//     *
//     * @param c       The row index to edit.
//     * @param text    The text value for the row.
//     * @param subject The subject value for the row.
//     * @param check   The check value for the row.
//     * @param keyword The keyword value for the row.
//     */
//    public void editRow(int c, String text, String subject, String check, String keyword) {
//        row[c][0] = text;
//        row[c][1] = subject;
//        row[c][2] = check;
//        row[c][3] = keyword;
//    }
//    /**
//     * Retrieves a value from a specific column at a given check index.
//     *
//     * @param co    The column index.
//     * @param check The check index.
//     * @return The value at the specified column and check index.
//     */
//    public String getCol(int co, int check) {
//        return col[co][check];
//    }
//    /**
//     * Retrieves a value from a specific row at a given check index.
//     *
//     * @param ro    The row index.
//     * @param check The check index.
//     * @return The value at the specified row and check index.
//     */
//    public String getRow(int ro, int check) {
//        return row[ro][check];
//    }


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
//    /**
//     * Retrieves the entire grid of the board.
//     *
//     * @return The 2D array representing the board's grid.
//     */
//    public String[][] getGrid() {
//
//        return griddle;
//    }
    /**
     * Generates a string representation of the board's grid.
     *
     * @return A string representation of the board's grid.
     */
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

