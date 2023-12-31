package onetoone.Songs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

public interface SongRepository extends JpaRepository<Song, Long> {
    Song findById(int id);

    Song findBySongName(String songName);

    @Transactional
    void deleteById(int id);

    @Transactional
    void deleteBySongName(String songName);
}
