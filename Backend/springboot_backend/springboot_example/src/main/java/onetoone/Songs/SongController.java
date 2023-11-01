package onetoone.Songs;

import java.util.List;

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
 * @author Vivek Bengre
 * 
 */ 

@RestController
public class SongController {

    @Autowired
    SongRepository songRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/songs")
    List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    @GetMapping(path = "/songs/{id}")
    Song getSongById(@PathVariable int id){
        return songRepository.findById(id);
    }

    @PostMapping(path = "/songs")
    String createSong(@RequestBody Song song){
        if (song == null)
            return failure;
        songRepository.save(song);
        return success;
    }

    @PutMapping(path = "/songs/{id}")
    Song updateSong(@PathVariable int id, @RequestBody Song request){
        Song song = songRepository.findById(id);
        if(song == null)
            return null;
        songRepository.save(request);
        return songRepository.findById(id);
    }

    @DeleteMapping(path = "/songs/{id}")
    String deleteSong(@PathVariable int id){
        songRepository.deleteById(id);
        return success;
    }

    // for the game directly
    @GetMapping(path = "/songsname/{name}/game/{check}")
    boolean checkIfSongContains(@PathVariable String name, @PathVariable String check){
        Song song = songRepository.findByName(name);
        return song.getSongName().contains(check);
    }
}
