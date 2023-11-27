package onetoone.Albums;

import onetoone.Artists.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findById(int id);

    Album findByName(String name);

    @Transactional
    void deleteById(int id);
}
