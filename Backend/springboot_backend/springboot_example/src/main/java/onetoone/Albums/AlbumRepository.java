package onetoone.Albums;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Conor O'Shea
 * 
 */ 

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findById(int id);

    Album findByName(String albumName);

    @Transactional
    void deleteById(int id);
}
