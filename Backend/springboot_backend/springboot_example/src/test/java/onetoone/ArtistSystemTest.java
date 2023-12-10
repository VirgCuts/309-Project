package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}