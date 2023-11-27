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
    @GetMapping(path = "/artists/{name}/featuring/{feature}")
    String getArtistFeatureCheck( @PathVariable String name, @PathVariable String feature) {
        Artist artist = artistRepository.findByName(name);
        List<Song> songList = artist.getSongs();
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getFeature().contains(feature)) {
                return success;
            }
        }
        return failure;
    }

    @ApiOperation(value = "CHeck if two artists have any song together", response = Boolean.class, tags = "artist-controller")
    @GetMapping(path = "/artists/{name1}/with/{name2}")
    String getArtistHaveSongTogether( @PathVariable String name1, @PathVariable String name2) {
        Artist artist1 = artistRepository.findByName(name1);
        String artist1name = artist1.getName();
        List<Song> songList1 = artist1.getSongs();
        Artist artist2 = artistRepository.findByName(name2);
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
        if (returner) {
            return success;
        }
        else {
            return failure;
        }
    }

    @GetMapping(path = "/artists/{name}/on/{album}")
    String getArtistOnAlbum( @PathVariable String name, @PathVariable String album) {
        Artist artist = artistRepository.findByName(name);
        Album album_object = albumRepository.findByName(album);
        List<Song> songList = album_object.getSongs();
        boolean returner = false;
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getFeature().contains(name)) {
                returner =  true;
            }
        }
        return success;
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
        String[] jsonArray = {
                "{\"text\":\"Artist with 'lil' in their name\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"lil\"}",
                "{\"text\":\"Artist with 'ill' in their name\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"ill\"}",
                "{\"text\":\"Artist with 'x' in their name\",\"subject\":\"Artist\",\"check\":\"with\",\"keyword\":\"x\"}",
                "{\"text\":\"Song with 'her' in their name\",\"subject\":\"Song\",\"check\":\"with\",\"keyword\":\"her\"}",
                "{\"text\":\"Song with 'men' in their name\",\"subject\":\"Song\",\"check\":\"with\",\"keyword\":\"men\"}",
                "{\"text\":\"Song with 'hip hop' in their name\",\"subject\":\"Song\",\"check\":\"with\",\"keyword\":\"hip hop\"}"
        };

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

