package onetoone.Artists;

import java.util.List;

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

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<Artist> getAllUsers(){
        return artistRepository.findAll();
    }

    @GetMapping(path = "/users/{id}")
    Artist getUserById( @PathVariable int id){
        return artistRepository.findById(id);
    }

    @PostMapping(path = "/users")
    String createUser(@RequestBody Artist artist){
        if (artist == null)
            return failure;
        artistRepository.save(artist);
        return success;
    }

    @PutMapping("/users/{id}")
    Artist updateUser(@PathVariable int id, @RequestBody Artist request){
        Artist artist = artistRepository.findById(id);
        if(artist == null)
            return null;
        artistRepository.save(request);
        return artistRepository.findById(id);
    }   
    
    @PutMapping("/users/{userId}/laptops/{laptopId}")
    String assignLaptopToUser(@PathVariable int userId,@PathVariable int laptopId){
        Artist artist = artistRepository.findById(userId);
        Song song = songRepository.findById(laptopId);
        if(artist == null || song == null)
            return failure;
        song.setUser(artist);
        artist.setLaptop(song);
        artistRepository.save(artist);
        return success;
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        artistRepository.deleteById(id);
        return success;
    }
}
