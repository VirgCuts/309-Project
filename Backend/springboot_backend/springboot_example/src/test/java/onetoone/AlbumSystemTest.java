package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onetoone.Albums.Album;
import onetoone.Albums.AlbumRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)


@RunWith(SpringRunner.class)
public class AlbumSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Autowired
    AlbumRepository albumRepository;

    @Test
    public void getAllAlbums() {
        Response response = RestAssured.get("/albums");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void getAlbumById() throws JsonProcessingException {
        Response response = RestAssured.get("/albums/1");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        Album album = mapper.readValue(returnString, Album.class);
        assertEquals("UTOPIA", album.getAlbumName());
    }

    @Test
    public void createAlbum() {
        Map<String, Object> map = new HashMap<>();
        map.put("albumName", "TestingName");
        map.put("genre", "Test");

        Response response =
                RestAssured.given().
                        header("Content-Type", "application/JSON").
                        body(map).
                        when().
                        post("/albums");
        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        albumRepository.deleteByAlbumName("TestingName");
    }

    @Test
    public void deleteAlbum() {
        Map<String, Object> map = new HashMap<>();
        map.put("albumName", "TestingName");
        map.put("genre", "Test");

        Response response =
                RestAssured.given().
                        header("Content-Type", "application/JSON").
                        body(map).
                        when().
                        post("/albums");
        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        Response response1 = RestAssured.delete("/albums/21");
        assertEquals(200, response1.getStatusCode());

        String returnString2 = response1.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString2);
    }

}