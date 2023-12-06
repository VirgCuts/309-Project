package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import onetoone.Inventory.Inventory;
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
public class InventorySystemTest {

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
    public void getInventoryForUser(){
        User user = new User();
        user.setName("TestingInventory");
        userRepository.save(user);

        Response response = RestAssured.get("/inventory/TestingInventory");
        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("false, false, false, false, false, false", returnString);

        userRepository.delete(userRepository.findById(user.getId()));
    }
    @Test
    public void addColorToInventory() {
        User user = new User();
        user.setName("TestingInventory");
        userRepository.save(user);
        Inventory inventory = user.getInventory();
        inventory.setUser(user);
        assertEquals(inventory.getUser(), user);

        Response response = RestAssured.put("/inventory/TestingInventory/orange");
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());

        response = RestAssured.put("/inventory/TestingInventory/purple");
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());

        response = RestAssured.put("/inventory/TestingInventory/lightblue");
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());

        response = RestAssured.put("/inventory/TestingInventory/yellow");
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());

        response = RestAssured.put("/inventory/TestingInventory/magenta");
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());

        response = RestAssured.put("/inventory/TestingInventory/green");
        assertEquals(200, response.getStatusCode());
        assertEquals("{\"message\":\"success\"}", response.getBody().asString());

        response = RestAssured.get("/inventory/TestingInventory");
        assertEquals(200, response.getStatusCode());
        assertEquals("true, true, true, true, true, true", response.getBody().asString());

        userRepository.delete(userRepository.findById(user.getId()));
    }

}

