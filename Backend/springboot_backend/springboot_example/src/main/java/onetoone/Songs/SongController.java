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

    @GetMapping(path = "/laptops")
    List<Song> getAllLaptops(){
        return songRepository.findAll();
    }

    @GetMapping(path = "/laptops/{id}")
    Song getLaptopById(@PathVariable int id){
        return songRepository.findById(id);
    }

    @PostMapping(path = "/laptops")
    String createLaptop(@RequestBody Song song){
        if (song == null)
            return failure;
        songRepository.save(song);
        return success;
    }

    @PutMapping(path = "/laptops/{id}")
    Song updateLaptop(@PathVariable int id, @RequestBody Song request){
        Song song = songRepository.findById(id);
        if(song == null)
            return null;
        songRepository.save(request);
        return songRepository.findById(id);
    }

    @DeleteMapping(path = "/laptops/{id}")
    String deleteLaptop(@PathVariable int id){
        songRepository.deleteById(id);
        return success;
    }
}
