package onetoone.Artists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findById(int id);

    Artist findByName(String name);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteByName(String name);
}
