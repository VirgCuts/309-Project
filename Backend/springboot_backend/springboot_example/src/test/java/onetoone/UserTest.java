package onetoone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import onetoone.Users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class UserTest {

    String name;

    String password;

    int highscore;

    public UserTest(String name, String password, int highscore) {
        name = name;
        password = password;
        highscore = highscore;
    }

    // @Parameters(name = "Run {index}: loanAmount={0}, downPayment={1},
    // availableFunds={2}, expectApproved={3}, expectedMessage={4}")
    // public static Iterable<Object[]> data() throws Throwable
    // {
    // return Arrays.asList(new Object[][] {
    // { 1000.0f, 200.0f, 250.0f, true, null },
    // { 1000.0f, 200.0f, 250.0f, true, null },
    // { 1000.0f, 50.0f, 250.0f, false, "error.insufficient.down.payment" },
    // { 1000.0f, 200.0f, 150.0f, false,
    // "error.insufficient.funds.for.down.payment" }
    //
    // });
    // }
    @Parameters
    public static Collection<Object[]> getParameters() {
        Collection<Object[]> retList = new ArrayList<Object[]>();
        // split the line using delimiter and then create the test-case object
        Object[] d = new Object[3];
        d[0] = "Sam";
        d[1] = "password";
        d[2] = 1000;


        // add the test data into the arraylist
        retList.add(d);
        // return all the test-cases
        return retList;
    }

    @Test  // run this for each test-case in the above collection
    public void testRequestLoan() throws Throwable {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setAllHighScores(highscore);
        assertEquals(user.getHighScore(), highscore);
    }
}
