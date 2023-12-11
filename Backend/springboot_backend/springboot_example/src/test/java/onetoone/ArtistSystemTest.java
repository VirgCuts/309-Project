package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onetoone.Artists.Artist;
import onetoone.Artists.ArtistRepository;
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
public class ArtistSystemTest {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Autowired
    ArtistRepository artistRepository;

    @Test
    public void getAllArtists(){
        Response response = RestAssured.get("/artists");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void getArtistById() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/1");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        Artist artist = mapper.readValue(returnString, Artist.class);
        assertEquals("Travis Scott", artist.getName());
    }

    @Test
    public void getArtistSongs() {
        Response response = RestAssured.get("/artists/1/songs");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertTrue(returnString.contains("HYAENA"));
        assertTrue(returnString.contains("THANK GOD"));
        assertTrue(returnString.contains("FE!N"));
        assertTrue(returnString.contains("I KNOW ?"));
    }

    @Test
    public void getArtistByName() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/Travis Scott/study");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        Artist artist = mapper.readValue(returnString, Artist.class);
        assertEquals("Travis Scott", artist.getName());
    }

    @Test
    public void getArtistByRandom() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/random");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        Artist artist = mapper.readValue(returnString, Artist.class);
        assertEquals(0, artist.getNumGrammys());
        assertEquals(0, artist.getNumPlatinums());
    }

    @Test
    public void getArtistNameContains() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/Travis Scott/name/vis");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        boolean response2 = mapper.readValue(returnString, Boolean.class);
        assertTrue(response2);
    }

    @Test
    public void getArtistFeatureCheck() {
        Response response = RestAssured.get("/artists/Travis Scott/featuring/Drake");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void getArtistHaveSong() {
        Response response = RestAssured.get("/artists/Playboi Carti/with/Future");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void getArtistHaveSong2() {
        Response response = RestAssured.get("/artists/Playboi Carti/with/Lil Uzi Vert");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void getArtistHaveSong3() {
        Response response = RestAssured.get("/artists/Future/with/Playboi Carti");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void getArtistOnAlbum() {
        Response response = RestAssured.get("/artists/Kid Cudi/on/Whole Lotta Red");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void getArtistAndSongContains() {
        Response response = RestAssured.get("/artists/Drake/artist/ak/songs/GAF");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void getCategories1() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/categories");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        String[] response2 = mapper.readValue(returnString, String[].class);
    }

    @Test
    public void getCategories2() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/categories");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        String[] response2 = mapper.readValue(returnString, String[].class);
    }

    @Test
    public void getCategories3() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/categories");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        String[] response2 = mapper.readValue(returnString, String[].class);
    }

    @Test
    public void getCategories4() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/categories");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        String[] response2 = mapper.readValue(returnString, String[].class);
    }

    @Test
    public void getCategories5() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/categories");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        String[] response2 = mapper.readValue(returnString, String[].class);
    }

    @Test
    public void getCategories6() throws JsonProcessingException {
        Response response = RestAssured.get("/artists/categories");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        String[] response2 = mapper.readValue(returnString, String[].class);
    }

    @Test
    public void getArtistHasSong() {
        Response response = RestAssured.get("/artists/Drake/songs/HYAENA");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"failure\"}", returnString);
    }

    @Test
    public void getArtistHasSongNumber() {
        Response response = RestAssured.get("/artists/Travis Scott/songs/study");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"numSongs\":\"19\"}", returnString);
    }

    @Test
    public void getArtistSongList() {
        Response response = RestAssured.get("/artists/Pharrell Williams/songs/string/study");
        assertEquals(200, response.getStatusCode());
        String returnString = response.getBody().asString();
        assertEquals("{\"list\":\"Cash In Cash Out\"}", returnString);
    }

}