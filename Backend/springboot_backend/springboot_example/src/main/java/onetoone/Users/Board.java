package onetoone.Users;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Board {
    private static final int gameSize = 3;
    private Boolean won;
    private int[][] game;

    public void initializeBoard() {
        this.game = new int[gameSize][gameSize];
        for (int i = 0; i < gameSize; i++)
            for (int j = 0; j < gameSize; j++)
                this.game[i][j] = 0;
        this.won = false;
    }

    //user has won if no zeros
    private boolean winCheck() {
        for (int i = 0; i < gameSize; i++)
            for (int j = 0; j < gameSize; j++)
                if (this.game[i][j] == 0){
                    return false;
                }
        return true;
    }

    //only call when user guesses correctly
    public void updateBoard(int x, int y) {
        this.game[x][y] = 1;
        this.won = this.winCheck();
    }

    public int[][] getGame() { return game; }
    public boolean getWon() { return this.won; }
}
