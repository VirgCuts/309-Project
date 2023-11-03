package onetoone.Albums;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Album findById(int id);

    @Transactional
    void deleteById(int id);
}
