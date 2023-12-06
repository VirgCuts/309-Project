package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.parsing.Parser;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


@RunWith(SpringRunner.class)
public class UserSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Autowired
    UserRepository userRepository;

    @Test
    public void getAllUsers(){
        Response response = RestAssured.get("/users");
        assertEquals(200, response.getStatusCode());
    }
    @Test
    public void createUserWithName() {
        // Send request and receive response
        Response response = RestAssured.post("/users/TestingName");
        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        userRepository.delete(userRepository.findByName("TestingName"));
    }
    @Test
    public void createUserWithNameAndPasswordAndEmail() {
        // Send request and receive response
        Response response = RestAssured.post("/users/TestingName/password/email");
        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        userRepository.delete(userRepository.findByName("TestingName"));
    }
    @Test
    public void getLeaderboard(){
        Response response = RestAssured.get("/leaderboard");
        assertEquals(200, response.getStatusCode());
    }
    @Test
    public void getSingleUserForLeaderboard(){
        User user = new User();
        user.setName("TestingName");
        userRepository.save(user);

        Response response = RestAssured.get("/leaderboard/TestName1");
        assertEquals(200, response.getStatusCode());

        userRepository.delete(userRepository.findById(user.getId()));
    }
    @Test
    public void updateSingleUserLeaderboard(){
        RestAssured.defaultParser = Parser.JSON;

        User user = new User();
        user.setName("TestingName");
        userRepository.save(user);

        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("").
                when().
                put("/leaderboard/TestingName/150");
        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        User returnUser = response.getBody().as(User.class);
        assertEquals(150, returnUser.getHighScore());
        assertEquals(150, returnUser.getHighScoreMonthly());
        assertEquals(150, returnUser.getHighScoreWeekly());

        userRepository.delete(userRepository.findById(user.getId()));
    }
    @Test
    public void deleteSingleUserLeaderboard(){
        User user = new User();
        user.setName("TestingName");
        userRepository.save(user);

        Response response = RestAssured.delete("leaderboard/TestingName");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }
    @Test
    public void unbanUserFromChat(){
        User user = new User();
        user.setName("TestingName");
        userRepository.save(user);

        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("").
                when().
                put("/canChat/TestingName/true");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        userRepository.delete(user);
    }
    @Test
    public void banUserFromChat(){
        User user = new User();
        user.setName("TestingName");
        userRepository.save(user);

        Response response = RestAssured.put("/canChat/TestingName/false");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        userRepository.delete(user);
    }
    @Test
    public void getBanStrikes(){
        User user = new User();
        user.setName("TestingName");
        userRepository.save(user);

        Response response = RestAssured.get("/banStrikes/TestingName");
        assertEquals(200, response.getStatusCode());
        assertEquals(0, response.getBody().as(int.class));

        userRepository.delete(user);
    }

    @Test
    public void getAndSetSelectedColor(){
        User user = new User();
        user.setName("TestingName");
        userRepository.save(user);

        Response response1 = RestAssured.put("/gameColor/TestingName/green");
        assertEquals(200, response1.getStatusCode());
        Response response2 = RestAssured.get("/gameColor/TestingName");
        assertEquals(200, response2.getStatusCode());

        String returnString1 = response1.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString1);
        String returnString2 = response2.getBody().asString();
        assertEquals("{\"color\":\"green\"}", returnString2);

        userRepository.delete(user);
    }
}

