package onetoone.Artists;

import java.util.List;
import java.util.Random;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import onetoone.Songs.Song;
import onetoone.Songs.SongRepository;
import onetoone.Albums.Album;
import onetoone.Albums.AlbumRepository;

/**
 * 
 * @author Conor O'Shea
 * 
 */

@Api(value = "ArtistController", description = "REST APIs for artist controller, created by Conor O'Shea")
@RestController
public class ArtistController {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    SongRepository songRepository;

    @Autowired
    AlbumRepository albumRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiOperation(value = "Get all Artists in the database", response = Iterable.class, tags = "artist-controller")
    @GetMapping(path = "/artists")
    List<Artist> getAllArtists(){
        return artistRepository.findAll();
    }

    @ApiOperation(value = "Get Artists by id in the database", response = Artist.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{id}")
    Artist getArtistById( @PathVariable int id){
        return artistRepository.findById(id);
    }

    @ApiOperation(value = "Get all Songs by an Artist in the database", response = Iterable.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{id}/songs")
    List<Song> getArtistSongs( @PathVariable int id) {
        Artist artist = artistRepository.findById(id);
        return artist.getSongs();
    }

    @ApiOperation(value = "Get all Artists by name in the database", response = Artist.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/study")
    Artist getArtistByName( @PathVariable String name) {
        return artistRepository.findByName(name);
    }

    @ApiOperation(value = "Get a random Artist from the database", response = Iterable.class, tags = "artist-controller")
    @GetMapping(path = "/artists/random")
    Artist getRandomArtist() {
        Random rand = new Random();
        int randomNum = rand.nextInt(437);
        return artistRepository.findById(randomNum);
    }

    @ApiOperation(value = "Check if an Artist's name contains a string", response = Boolean.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/name/{check}")
    boolean getArtistNameContains( @PathVariable String name, @PathVariable String check) {
        Artist artist = artistRepository.findByName(name);
        return artist.getName().contains(check);
    }

    @ApiOperation(value = "Check if an Artist has a song featuring another specified Artist", response = Boolean.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{id}/features/{feature}")
    boolean getArtistFeatureCheck( @PathVariable int id, @PathVariable String feature) {
        Artist artist = artistRepository.findById(id);
        List<Song> songList = artist.getSongs();
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getFeature().contains(feature)) {
                return true;
            }
        }
        return false;
    }

    @ApiOperation(value = "CHeck if two artists have any song together", response = Boolean.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{id}/artists2/{id2}")
    boolean getArtistHaveSongTogether( @PathVariable int id, @PathVariable int id2) {
        Artist artist1 = artistRepository.findById(id);
        String artist1name = artist1.getName();
        List<Song> songList1 = artist1.getSongs();
        Artist artist2 = artistRepository.findById(id2);
        String artist2name = artist2.getName();
        List<Song> songList2 = artist2.getSongs();
        boolean returner = false;
        for (int i = 0; i < songList1.size(); i++) {
            if (songList1.get(i).getFeature().contains(artist2name)) {
                returner =  true;
            }
        }
        for (int i = 0; i < songList2.size(); i++) {
            if (songList2.get(i).getFeature().contains(artist1name)) {
                returner =  true;
            }
        }
        return returner;
    }


    @ApiOperation(value = "Create an Artist and add it to the database", response = String.class, tags = "artist-controller")
    @PostMapping(path = "/artists")
    String createArtist(@RequestBody Artist artist){
        if (artist == null)
            return failure;
        artistRepository.save(artist);
        return success;
    }

    @ApiOperation(value = "Update an Artist in the database", response = Artist.class, tags = "artist-controller")
    @PutMapping("/artists/{id}")
    Artist updateArtist(@PathVariable int id, @RequestBody Artist request){
        Artist artist = artistRepository.findById(id);
        if(artist == null)
            return null;
        artistRepository.save(request);
        return artistRepository.findById(id);
    }

    @ApiOperation(value = "Assign a Song to an Artist in the database", response = String.class, tags = "artist-controller")
    @PutMapping("/artists/{artistId}/songs/{songId}")
    String assignSongToArtist(@PathVariable int artistId,@PathVariable int songId){
        Artist artist = artistRepository.findById(artistId);
        Song song = songRepository.findById(songId);
        if(artist == null || song == null)
            return failure;
        song.setArtist(artist);
        artist.addSongs(song);
        artistRepository.save(artist);
        return success;
    }

    @ApiOperation(value = "Assign an Album to an Artist in the database", response = String.class, tags = "artist-controller")
    @PutMapping("/artists/{artistId}/albums/{albumId}")
    String assignAlbumToArtist(@PathVariable int artistId,@PathVariable int albumId){
        Artist artist = artistRepository.findById(artistId);
        Album album = albumRepository.findById(albumId);
        if(artist == null || album == null)
            return failure;
        album.setArtist(artist);
        artist.addAlbums(album);
        artistRepository.save(artist);
        return success;
    }

    @ApiOperation(value = "Delete an Artist from the database", response = String.class, tags = "artist-controller")
    @DeleteMapping(path = "/artists/{id}")
    String deleteArtist(@PathVariable int id){
        artistRepository.deleteById(id);
        return success;
    }

    // for the game directly
    @ApiOperation(value = "Check if an Artist name contains a given string for the game", response = Boolean.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/game/{check}")
    boolean checkIfArtistContains(@PathVariable String name, @PathVariable String check){
        Artist artist = artistRepository.findByName(name);
        return artist.getName().contains(check);
    }

    @ApiOperation(value = "Check if a song by an artist contains a given string", response = Boolean.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/songs/{songId}/game/{check}")
    boolean checkIfSongContains(@PathVariable String name, @PathVariable int songId,
                                @PathVariable String check){
        Artist artist = artistRepository.findByName(name);
        List<Song> songList = artist.getSongs();
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getSongName().contains(check)) {
                return true;
            }
        }
        return false;
    }

    @ApiOperation(value = "Check if an Artist and a Song contain strings for the game", response = String.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/artist/{check1}/songs/{check2}")
    String checkIfSongAndArtistContains(@PathVariable String name, @PathVariable String check1,
                                        @PathVariable String check2){
        Artist artist = artistRepository.findByName(name);
        if (artist != null) {
            if (artist.getName().contains(check1)) {
                List<Song> songList = artist.getSongs();
                for (int i = 0; i < songList.size(); i++) {
                    if (songList.get(i).getSongName().contains(check2)) {
                        return success;
                    }
                }
            }
        }
        return failure;
    }

    @ApiOperation(value = "Return categories to be used by the game", response = String.class, tags = "artist-controller")
    @GetMapping(path = "/artists/categories")
    String[] getCategories(){
        String[] jsonArray = new String[6];
        Random random = new Random();
        int selector1 = random.nextInt(3);
        if (selector1 == 0) {
            int selector2 = random.nextInt(3);
            if (selector2 == 0) {
                jsonArray[0] = "{\"text\":\"Artist with a Song featuring Teezo Touchdown\",\"subject\":\"Artist\",\"check\":\"featuring\",\"keyword\":\"Teezo Touchdown\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song with Travis Scott\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Travis Scott\"}";
                jsonArray[2] = "{\"text\":\"Artist that has a Song with Playboi Carti\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Playboi Carti\"}";

                jsonArray[3] = "{\"text\":\"Artist that has a Song with Kanye West\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Kanye West\"}";
                jsonArray[4] = "{\"text\":\"Artist that has a Song with Yeat\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Yeat\"}";
                jsonArray[5] = "{\"text\":\"Artist that has a Song with 21 Savage\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"21 Savage\"}";
            }
            else if (selector2 == 1) {
                jsonArray[2] = "{\"text\":\"Artist with a Song featuring Teezo Touchdown\",\"subject\":\"Artist\",\"check\":\"featuring\",\"keyword\":\"Teezo Touchdown\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song with Travis Scott\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Travis Scott\"}";
                jsonArray[0] = "{\"text\":\"Artist that has a Song with Playboi Carti\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Playboi Carti\"}";

                jsonArray[4] = "{\"text\":\"Artist that has a Song with Kanye West\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Kanye West\"}";
                jsonArray[5] = "{\"text\":\"Artist that has a Song with Yeat\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Yeat\"}";
                jsonArray[3] = "{\"text\":\"Artist that has a Song with 21 Savage\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"21 Savage\"}";
            }
            else {
                jsonArray[0] = "{\"text\":\"Artist with a Song featuring Teezo Touchdown\",\"subject\":\"Artist\",\"check\":\"featuring\",\"keyword\":\"Teezo Touchdown\"}";
                jsonArray[2] = "{\"text\":\"Artist that has a Song with Travis Scott\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Travis Scott\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song with Playboi Carti\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Playboi Carti\"}";

                jsonArray[5] = "{\"text\":\"Artist that has a Song with Kanye West\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Kanye West\"}";
                jsonArray[4] = "{\"text\":\"Artist that has a Song with Yeat\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Yeat\"}";
                jsonArray[3] = "{\"text\":\"Artist that has a Song with 21 Savage\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"21 Savage\"}";
            }
        }
        else if (selector1 == 1) {
            int selector2 = random.nextInt(3);
            if (selector2 == 0) {
                jsonArray[0] = "{\"text\":\"Artist that has a Song with Tyler The Creator\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Tyler The Creator\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song on HEROES & VILLAINS\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"HEROES & VILLAINS\"}";
                jsonArray[2] = "{\"text\":\"Artist that has a Song on UTOPIA\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"UTOPIA\"}";

                jsonArray[3] = "{\"text\":\"Artist that has a Song with Swae Lee\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Swae Lee\"}";
                jsonArray[4] = "{\"text\":\"Artist that has a Song with Playboi Carti\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Playboi Carti\"}";
                jsonArray[5] = "{\"text\":\"Artist that has a Song on Spider-Man: Across the Spiderverse\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"Spider-Man: Across the Spiderverse\"}";
            }
            else if (selector2 == 1) {
                jsonArray[2] = "{\"text\":\"Artist that has a Song with Tyler The Creator\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Tyler The Creator\"}";
                jsonArray[0] = "{\"text\":\"Artist that has a Song on HEROES & VILLAINS\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"HEROES & VILLAINS\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song on UTOPIA\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"UTOPIA\"}";

                jsonArray[3] = "{\"text\":\"Artist that has a Song with Swae Lee\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Swae Lee\"}";
                jsonArray[5] = "{\"text\":\"Artist that has a Song with Playboi Carti\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Playboi Carti\"}";
                jsonArray[4] = "{\"text\":\"Artist that has a Song on Spider-Man: Across the Spiderverse\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"Spider-Man: Across the Spiderverse\"}";
            }
            else {
                jsonArray[1] = "{\"text\":\"Artist that has a Song with Tyler The Creator\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Tyler The Creator\"}";
                jsonArray[0] = "{\"text\":\"Artist that has a Song on HEROES & VILLAINS\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"HEROES & VILLAINS\"}";
                jsonArray[2] = "{\"text\":\"Artist that has a Song on UTOPIA\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"UTOPIA\"}";

                jsonArray[5] = "{\"text\":\"Artist that has a Song with Swae Lee\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Swae Lee\"}";
                jsonArray[4] = "{\"text\":\"Artist that has a Song with Playboi Carti\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Playboi Carti\"}";
                jsonArray[3] = "{\"text\":\"Artist that has a Song on Spider-Man: Across the Spiderverse\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"Spider-Man: Across the Spiderverse\"}";
            }
        }
        else {
            int selector2 = random.nextInt(3);
            if (selector2 == 0) {
                jsonArray[0] = "{\"text\":\"Artist that has a Song on UTOPIA\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"UTOPIA\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song with Drake\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Drake\"}";
                jsonArray[2] = "{\"text\":\"Artist that has a Song with 21 Savage\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"21 Savage\"}";

                jsonArray[3] = "{\"text\":\"Artist that has a Song on Spider-Man: Across the Spiderverse\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"Spider-Man: Across the Spiderverse\"}";
                jsonArray[4] = "{\"text\":\"Artist that has a Song with Metro Boomin\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Metro Boomin\"}";
                jsonArray[5] = "{\"text\":\"Artist that has a Song with Lil Uzi Vert\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Lil Uzi Vert\"}";
            }
            else if (selector2 == 1) {
                jsonArray[0] = "{\"text\":\"Artist that has a Song on UTOPIA\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"UTOPIA\"}";
                jsonArray[2] = "{\"text\":\"Artist that has a Song with Drake\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Drake\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song with 21 Savage\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"21 Savage\"}";

                jsonArray[5] = "{\"text\":\"Artist that has a Song on Spider-Man: Across the Spiderverse\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"Spider-Man: Across the Spiderverse\"}";
                jsonArray[4] = "{\"text\":\"Artist that has a Song with Metro Boomin\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Metro Boomin\"}";
                jsonArray[3] = "{\"text\":\"Artist that has a Song with Lil Uzi Vert\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Lil Uzi Vert\"}";
            }
            else {
                jsonArray[2] = "{\"text\":\"Artist that has a Song on UTOPIA\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"UTOPIA\"}";
                jsonArray[1] = "{\"text\":\"Artist that has a Song with Drake\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Drake\"}";
                jsonArray[0] = "{\"text\":\"Artist that has a Song with 21 Savage\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"21 Savage\"}";

                jsonArray[4] = "{\"text\":\"Artist that has a Song on Spider-Man: Across the Spiderverse\",\"subject\":\"Artist\",\"check\":\"on\",\"keyword\":\"Spider-Man: Across the Spiderverse\"}";
                jsonArray[3] = "{\"text\":\"Artist that has a Song with Metro Boomin\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Metro Boomin\"}";
                jsonArray[5] = "{\"text\":\"Artist that has a Song with Lil Uzi Vert\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"Lil Uzi Vert\"}";
            }
        }
        return jsonArray;

    }

    // for study
    @ApiOperation(value = "Check if an Artist has a song by song name", response = String.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/songs/{songName}")
    String getArtistHasSong( @PathVariable String name, @PathVariable String songName) {
        Artist artist = artistRepository.findByName(name);
        List<Song> songList = artist.getSongs();
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getSongName().equals(songName)) {
                return success;
            }
        }
        return failure;
    }

    @ApiOperation(value = "Check how many songs an Artist has in the database", response = String.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/songs/study")
    String getArtistSongNumber( @PathVariable String name) {
        Artist artist = artistRepository.findByName(name);
        int returner = artist.getSongs().size();
        return "{\"numSongs\":\"" + returner + "\"}";
    }

    @ApiOperation(value = "Get the song list of an Artist in the database", response = String.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name}/songs/string/study")
    String getArtistSongsStringForm( @PathVariable String name) {
        Artist artist = artistRepository.findByName(name);
        List<Song> songList = artist.getSongs();
        String returner = "";
        for (int i = 0; i < songList.size() - 1; i++) {
            returner = returner + songList.get(i).getSongName() + ", ";
        }
        returner = returner + songList.get(songList.size() - 1).getSongName();
        return "{\"list\":\"" + returner + "\"}";
    }
}

