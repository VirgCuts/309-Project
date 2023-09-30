package tables.Songs;

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
 * @author Conor O'Shea
 * 
 */ 

@RestController
public class SongController {

    @Autowired
    SongRepository songRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/song")
    List<Song> getAllSongs(){
        return songRepository.findAll();
    }

    @GetMapping(path = "/song/{id}")
    Song getSongById(@PathVariable int id){
        return songRepository.findById(id);
    }

    @PostMapping(path = "/song")
    String createSong(@RequestBody Song song){
        if (song == null)
            return failure;
        songRepository.save(song);
        return success;
    }

    @PutMapping(path = "/song/{id}")
    Song updateSong(@PathVariable int id, @RequestBody Song request){
        Song song = songRepository.findById(id);
        if(song == null)
            return null;
        songRepository.save(request);
        return songRepository.findById(id);
    }

    @DeleteMapping(path = "/song/{id}")
    String deleteSong(@PathVariable int id){
        songRepository.deleteById(id);
        return success;
    }
}
