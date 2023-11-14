package onetoone.Albums;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;

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

@Api(value = "AlbumController", description = "REST APIs for album controller, created by Conor O'Shea")
@RestController
public class AlbumController {

    @Autowired
    AlbumRepository albumRepository;
    
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiOperation(value = "Get all Albums in the database", response = Iterable.class, tags = "album-controller")
    @GetMapping(path = "/albums")
    List<Album> getAllAlbums(){
        return albumRepository.findAll();
    }

    @ApiOperation(value = "Get Album by id from the database", response = Album.class, tags = "album-controller")
    @GetMapping(path = "/albums/{id}")
    Album getAlbumById(@PathVariable int id){
        return albumRepository.findById(id);
    }

    @ApiOperation(value = "Create an album and save it to the database", response = String.class, tags = "album-controller")
    @PostMapping(path = "/albums")
    String createAlbum(@RequestBody Album album){
        if (album == null)
            return failure;
        albumRepository.save(album);
        return success;
    }

    @ApiOperation(value = "Update an Album by id in the database", response = Album.class, tags = "album-controller")
    @PutMapping(path = "/albums/{id}")
    Album updateAlbum(@PathVariable int id, @RequestBody Album request){
        Album album = albumRepository.findById(id);
        if(album == null)
            return null;
        albumRepository.save(request);
        return albumRepository.findById(id);
    }

    @ApiOperation(value = "Delete Album by id from the database", response = String.class, tags = "album-controller")
    @DeleteMapping(path = "/albums/{id}")
    String deleteAlbum(@PathVariable int id){
        albumRepository.deleteById(id);
        return success;
    }
}
