package tables.Artists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Conor O'Shea
 * 
 */ 

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findById(int id);

    @Transactional
    void deleteById(int id);
}
