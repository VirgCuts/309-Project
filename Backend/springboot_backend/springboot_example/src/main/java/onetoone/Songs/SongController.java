package onetoone.Songs;

import java.util.List;

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

/**
 * 
 * @author Conor O'Shea
 * 
 */

@Api(value = "SongController", description = "REST APIs for song controller, created by Conor O'Shea")
@RestController
public class SongController {

    @Autowired
    SongRepository songRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiOperation(value = "Get all Songs in the database", response = Iterable.class, tags = "song-controller")
    @GetMapping(path = "/songs")
    List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    @ApiOperation(value = "Get a Song by id from the database", response = Song.class, tags = "song-controller")
    @GetMapping(path = "/songs/{id}")
    Song getSongById(@PathVariable int id){
        return songRepository.findById(id);
    }

    @ApiOperation(value = "Create a Song and add it to the database", response = String.class, tags = "song-controller")
    @PostMapping(path = "/songs")
    String createSong(@RequestBody Song song){
        if (song == null)
            return failure;
        songRepository.save(song);
        return success;
    }

    @ApiOperation(value = "Update a Song in the database", response = Song.class, tags = "song-controller")
    @PutMapping(path = "/songs/{id}")
    Song updateSong(@PathVariable int id, @RequestBody Song request){
        Song song = songRepository.findById(id);
        if(song == null)
            return null;
        songRepository.save(request);
        return songRepository.findById(id);
    }

    @ApiOperation(value = "Delete a song from the database", response = String.class, tags = "song-controller")
    @DeleteMapping(path = "/songs/{id}")
    String deleteSong(@PathVariable int id){
        songRepository.deleteById(id);
        return success;
    }

    // for the game directly
    @ApiOperation(value = "Check if a song contains a string for the game", response = Boolean.class, tags = "song-controller")
    @GetMapping(path = "/songsname/{name}/game/{check}")
    boolean checkIfSongContains(@PathVariable String name, @PathVariable String check){
        Song song = songRepository.findBySongName(name);
        return song.getSongName().contains(check);
    }
}
