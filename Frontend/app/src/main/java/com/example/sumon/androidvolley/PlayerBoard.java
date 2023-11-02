package com.example.sumon.androidvolley;

public class PlayerBoard {
    private final int BOARD_SIZE = 3;
    private String[][] grid;

    public PlayerBoard() {
        grid = new String[BOARD_SIZE][BOARD_SIZE];
        // Initialize the grid with empty strings
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                grid[i][j] = "";
            }
        }
    }

    public PlayerBoard(PlayerBoard original) {
        this.grid = new String[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.grid[i][j] = original.grid[i][j];
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
            return grid[row][col];
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
            grid[row][col] = value;
        } else {
            // Handle invalid positions (e.g., throw an exception or print an error)
            System.out.println("Invalid position!");
        }
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
        return grid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(grid[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

