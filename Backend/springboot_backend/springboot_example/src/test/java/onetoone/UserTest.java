package onetoone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import onetoone.Users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class UserTest {

    int id;
    String name;
    String password;
    int highestscore;
    int higherscore;
    int highscore;
    Date date;
    boolean canChat;
    int banStrikes;

    public UserTest(int id, String name, String password, int highestscore, int higherscore, int highscore, Date date, boolean canChat, int banStrikes) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.highestscore = highestscore;
        this.higherscore = higherscore;
        this.highscore = highscore;
        this.date = date;
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
        d[3] = 1000;
        d[4] = 500;
        d[5] = 250;
        d[6] = new Date();
        d[7] = true;
        d[8] = 0;

        // add the test data into the arraylist
        retList.add(d);
        // return all the test-cases
        return retList;
    }

    @Test  // run this for each test-case in the above collection
    public void testGetterAndSetters() throws Throwable {
        User user = new User(name, highscore);

        user.setName(name);
        assertEquals(user.getName(), name);
        user.setPassword(password);
        assertEquals(user.getPassword(), password);
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
    }

    @Test
    public void testSettingDifferentScores() throws Throwable {
        User user = new User(name, password);
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
    }
}
