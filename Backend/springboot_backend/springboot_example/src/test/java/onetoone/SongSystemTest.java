package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onetoone.Songs.Song;
import onetoone.Songs.SongRepository;
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
public class SongSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Autowired
    SongRepository songRepository;

    @Test
    public void getAllSongs() {
        Response response = RestAssured.get("/songs");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void getSongById() throws JsonProcessingException {
        Response response = RestAssured.get("/songs/1");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        Song song = mapper.readValue(returnString, Song.class);
        assertEquals("HYAENA", song.getSongName());
    }

    @Test
    public void createSong() {
        Map<String, Object> map = new HashMap<>();
        map.put("songName", "TestingName");
        map.put("genre", "Test");
        map.put("feature", "");

        Response response =
                RestAssured.given().
                        header("Content-Type", "application/JSON").
                        body(map).
                        when().
                        post("/songs");
        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        songRepository.deleteBySongName("TestingName");
    }

    @Test
    public void deleteSong() {
        Map<String, Object> map = new HashMap<>();
        map.put("songName", "TestingName");
        map.put("genre", "Test");
        map.put("feature", "");

        Response response =
                RestAssured.given().
                        header("Content-Type", "application/JSON").
                        body(map).
                        when().
                        post("/songs");
        assertEquals(200, response.getStatusCode());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        Response response1 = RestAssured.delete("/songs/102");
        assertEquals(200, response.getStatusCode());

        String returnString2 = response1.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString2);

        songRepository.deleteBySongName("TestingName");
    }

    @Test
    public void getSongNameContains() throws JsonProcessingException {
        Response response = RestAssured.get("/songsname/HYAENA/game/YAE");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        boolean response2 = mapper.readValue(returnString, Boolean.class);
        assertTrue(response2);
    }
}