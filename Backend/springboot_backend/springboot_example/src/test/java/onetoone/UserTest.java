package onetoone;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import onetoone.Users.Board;
import onetoone.Users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserTest {

    int id;
    String name;
    String password;
    String email;
    int highestscore;
    int higherscore;
    int highscore;
    boolean canChat;
    int banStrikes;

    public UserTest(int id, String name, String password, String email, int highestscore, int higherscore, int highscore, boolean canChat, int banStrikes) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.highestscore = highestscore;
        this.higherscore = higherscore;
        this.highscore = highscore;
        this.canChat = canChat;
        this.banStrikes = banStrikes;
    }


    @Parameters
    public static Collection<Object[]> getParameters() {
        Collection<Object[]> retList = new ArrayList<Object[]>();
        // split the line using delimiter and then create the test-case object
        Object[] d = new Object[9];
        d[0] = 1;
        d[1] = "Sam";
        d[2] = "password";
        d[3] = "sdl21@iastate.edu";
        d[4] = 1000;
        d[5] = 500;
        d[6] = 250;
        d[7] = true;
        d[8] = 0;

        // add the test data into the arraylist
        retList.add(d);
        // return all the test-cases
        return retList;
    }

    @Test  // run this for each test-case in the above collection
    public void testGetterAndSetters() throws Throwable {
        User user = new User();

        user.setName(name);
        assertEquals(user.getName(), name);
        user.setPassword(password);
        assertEquals(user.getPassword(), password);
        user.setEmail(email);
        assertEquals(email, user.getEmail());
        user.setId(id);
        assertEquals(user.getId(), id);

        user.setHighScore(highscore);
        assertEquals(user.getHighScore(), highscore);
        user.setHighScoreWeekly(highscore);
        assertEquals(user.getHighScoreWeekly(), highscore);
        user.setHighScoreMonthly(highscore);
        assertEquals(user.getHighScoreMonthly(), highscore);

        user.setBanStrikes(banStrikes);
        assertEquals(user.getBanStrikes(), banStrikes);
        user.setCanChat(canChat);
        assertEquals(user.getCanChat(), canChat);

        Board board = new Board();
        user.setBoard(board);
        assertEquals(user.boardToString(), "{\"won\":false,\"game\":[[0,0,0],[0,0,0],[0,0,0]],\"score\":0}");
        board.updateBoard(0, 0);
        assertFalse(user.hasWon());
        assertEquals(user.boardToString(), "{\"won\":false,\"game\":[[1,0,0],[0,0,0],[0,0,0]],\"score\":1}");
        board.updateBoard(0, 1);
        board.updateBoard(0, 2);
        board.updateBoard(1, 0);
        board.updateBoard(1, 1);
        board.updateBoard(1, 2);
        board.updateBoard(2, 0);
        board.updateBoard(2, 1);
        board.updateBoard(2, 2);
        assertTrue(user.hasWon());
    }

    @Test
    public void testSettingDifferentScores() throws Throwable {
        User user = new User(name, password, email);
        user.setAllHighScores(highestscore);
        user.resetWeeklyScore();
        user.setAllHighScores(highscore);
        assertEquals(user.getHighScore(), highestscore);
        assertEquals(user.getHighScoreWeekly(), highscore);
        assertEquals(user.getHighScoreMonthly(), highestscore);
        user.resetMonthlyScore();
        user.setAllHighScores(higherscore);
        assertEquals(user.getHighScore(), highestscore);
        assertEquals(user.getHighScoreWeekly(), higherscore);
        assertEquals(user.getHighScoreMonthly(), higherscore);
        User user2 = new User(name, password, email);
        assertTrue((user.compare(user, user2) < 0));
    }
}
