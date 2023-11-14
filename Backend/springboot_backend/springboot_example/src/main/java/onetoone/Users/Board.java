package onetoone.Users;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;

public class Board {
    private static final int gameSize = 3;
    @ApiModelProperty(notes = "Whether the user has won or not", name="won")
    private boolean won;
    @ApiModelProperty(notes = "2D array of the game board", name="game")
    private int[][] game;
    @ApiModelProperty(notes = "Number of board spots filled correctly", name="score")
    private int score;

    public Board() {
        this.game = new int[gameSize][gameSize];
        for (int i = 0; i < gameSize; i++)
            for (int j = 0; j < gameSize; j++)
                this.game[i][j] = 0;
        this.won = false;
        this.score = 0;
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
        this.score++;
        this.won = this.winCheck();
    }

    public int getScore() {
        return this.score;
    }

    public int[][] getGame() { return game; }
    public boolean getWon() { return this.won; }
}
