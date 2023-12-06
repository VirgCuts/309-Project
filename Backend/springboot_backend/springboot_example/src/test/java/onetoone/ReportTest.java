package onetoone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import onetoone.Reports.Report;
import onetoone.Users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ReportTest {

    Long id;
    String username;
    String reportedUsername;
    String content;
    String reportedMessage;
    Date sent;
    User reportedUser;

    public ReportTest(Long id, String username, String reportedUsername, String content, String reportedMessage, Date sent, User reportedUser) {
        this.id = id;
        this.username = username;
        this.reportedUsername = reportedUsername;
        this.content = content;
        this.reportedMessage = reportedMessage;
        this.sent = sent;
        this.reportedUser = reportedUser;
    }


    @Parameters
    public static Collection<Object[]> getParameters() {
        Collection<Object[]> retList = new ArrayList<Object[]>();
        // split the line using delimiter and then create the test-case object
        Object[] d = new Object[7];
        d[0] = 1L;
        d[1] = "Sam";
        d[2] = "TestingName";
        d[3] = "report testing";
        d[4] = "message that was reported";
        d[5] = new Date();
        d[6] = new User("TestingName", "", "");

        // add the test data into the arraylist
        retList.add(d);
        // return all the test-cases
        return retList;
    }

    @Test  // run this for each test-case in the above collection
    public void testGetterAndSetters() throws Throwable {
        Report report = new Report();
        Report constructor = new Report(username, reportedUsername, content, reportedMessage);

        report.setId(id);
        assertEquals(report.getId(), id);
        report.setUsername(username);
        assertEquals(report.getUsername(), username);
        report.setReportedUsername(reportedUsername);
        assertEquals(report.getReportedUsername(), reportedUsername);
        report.setContent(content);
        assertEquals(report.getContent(), content);
        report.setReportedMessage(reportedMessage);
        assertEquals(report.getReportedMessage(), reportedMessage);

        report.setSent(sent);
        assertEquals(report.getSent(), sent);
        report.setReportedUser(reportedUser);
        assertEquals(report.getReportedUser(), reportedUser);
    }
}
