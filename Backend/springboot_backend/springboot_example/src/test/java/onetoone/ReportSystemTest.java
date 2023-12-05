package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
public class ReportSystemTest {

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
    public void getAllReports(){
        Response response = RestAssured.get("/reports");
        assertEquals(200, response.getStatusCode());
    }
    @Test
    public void getAllReportsForUser(){
        User user = new User();
        user.setName("TestingReport");
        userRepository.save(user);

        Response response = RestAssured.get("/reports/TestingReport");
        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("[]", returnString);

        userRepository.delete(userRepository.findById(user.getId()));
    }
    @Test
    public void makeReport() {
        User user = new User();
        user.setName("TestingReport1");
        userRepository.save(user);
        User user2 = new User();
        user2.setName("TestingReport2");
        userRepository.save(user2);

        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("testing controller").
                when().
                post("/report/TestingReport1/TestingReport2: controllerreport");

        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);


        userRepository.delete(userRepository.findById(user.getId()));
        userRepository.delete(userRepository.findById(user2.getId()));
    }
    @Test
    public void makeDMReport() {
        User user = new User();
        user.setName("TestingReport1");
        userRepository.save(user);
        User user2 = new User();
        user2.setName("TestingReport2");
        userRepository.save(user2);

        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset","utf-8").
                body("testing controller").
                when().
                post("/report/TestingReport1/[DM from TestingReport2]: controllerreport");

        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);


        userRepository.delete(userRepository.findById(user.getId()));
        userRepository.delete(userRepository.findById(user2.getId()));
    }
}

