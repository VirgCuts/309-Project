package onetoone.Artists;

import java.util.List;
import java.util.Random;

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
 * @author Vivek Bengre
 * 
 */ 

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

    @GetMapping(path = "/artists")
    List<Artist> getAllArtists(){
        return artistRepository.findAll();
    }

    @GetMapping(path = "/artists/{id}")
    Artist getArtistById( @PathVariable int id){
        return artistRepository.findById(id);
    }

    @GetMapping(path = "/artists/{id}/songs")
    List<Song> getArtistSongs( @PathVariable int id) {
        Artist artist = artistRepository.findById(id);
        return artist.getSongs();
    }

    @GetMapping(path = "/artists/{name}/study")
    Artist getArtistByName( @PathVariable String name) {
        return artistRepository.findByName(name);
    }

    @GetMapping(path = "/artists/random")
    Artist getRandomArtist() {
        Random rand = new Random();
        int randomNum = rand.nextInt(437);
        return artistRepository.findById(randomNum);
    }

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

    @PostMapping(path = "/artists")
    String createArtist(@RequestBody Artist artist){
        if (artist == null)
            return failure;
        artistRepository.save(artist);
        return success;
    }

    @PutMapping("/artists/{id}")
    Artist updateArtist(@PathVariable int id, @RequestBody Artist request){
        Artist artist = artistRepository.findById(id);
        if(artist == null)
            return null;
        artistRepository.save(request);
        return artistRepository.findById(id);
    }   
    
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

    @DeleteMapping(path = "/artists/{id}")
    String deleteArtist(@PathVariable int id){
        artistRepository.deleteById(id);
        return success;
    }

    // for the game directly
    @GetMapping(path = "/artists/{name}/game/{check}")
    boolean checkIfArtistContains(@PathVariable String name, @PathVariable String check){
        Artist artist = artistRepository.findByName(name);
        return artist.getName().contains(check);
    }

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

    @GetMapping(path = "/artists/{name}/artist/{check1}/songs/{songName}/song/{check2}")
    boolean checkIfSongAndArtistContains(@PathVariable String name, @PathVariable String check1,
                                @PathVariable String songName, @PathVariable String check2){
        Artist artist = artistRepository.findByName(name);
        if (artist.getName().contains(check1)) {
            List<Song> songList = artist.getSongs();
            for (int i = 0; i < songList.size(); i++) {
                if (songList.get(i).getSongName().equals(songName)) {
                    if (songList.get(i).getSongName().contains(check2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

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
}
