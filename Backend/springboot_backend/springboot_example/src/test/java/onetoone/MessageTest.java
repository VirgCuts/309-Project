package onetoone;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import onetoone.Websocket.Message;
import onetoone.Users.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class MessageTest {

    Long id;
    String userName;
    String content;
    Date sent;
    int likes;

    public MessageTest(Long id, String userName, String content, Date sent, int likes) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.sent = sent;
        this.likes = likes;
    }


    @Parameters
    public static Collection<Object[]> getParameters() {
        Collection<Object[]> retList = new ArrayList<Object[]>();
        // split the line using delimiter and then create the test-case object
        Object[] d = new Object[5];
        d[0] = 1L;
        d[1] = "Sam";
        d[2] = "message testing";
        d[3] = new Date();
        d[4] = 0;

        // add the test data into the arraylist
        retList.add(d);
        // return all the test-cases
        return retList;
    }

    @Test  // run this for each test-case in the above collection
    public void testGetterAndSetters() throws Throwable {
        Message message = new Message();
        Message constructor = new Message(userName, content);

        message.setId(id);
        assertEquals(message.getId(), id);
        message.setUserName(userName);
        assertEquals(message.getUserName(), userName);
        message.setContent(content);
        assertEquals(message.getContent(), content);
        message.setSent(sent);
        assertEquals(message.getSent(), sent);
        message.setLikes(likes);
        assertEquals(message.getLikes(), likes);

        message.addLike();
        message.addLike();
        assertEquals(message.getLikes(), likes + 2);
        message.removeLike();
        message.removeLike();
        assertEquals(message.getLikes(), likes);
    }
}
