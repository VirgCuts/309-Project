package onetoone.Albums;

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
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/albums")
    List<Album> getAllAlbums(){
        return albumRepository.findAll();
    }

    @GetMapping(path = "/albums/{id}")
    Album getAlbumById(@PathVariable int id){
        return albumRepository.findById(id);
    }

    @PostMapping(path = "/albums")
    String createAlbum(@RequestBody Album album){
        if (album == null)
            return failure;
        albumRepository.save(album);
        return success;
    }

    @PutMapping(path = "/albums/{id}")
    Album updateAlbum(@PathVariable int id, @RequestBody Album request){
        Album album = albumRepository.findById(id);
        if(album == null)
            return null;
        albumRepository.save(request);
        return albumRepository.findById(id);
    }

    @DeleteMapping(path = "/albums/{id}")
    String deleteAlbum(@PathVariable int id){
        albumRepository.deleteById(id);
        return success;
    }
}
