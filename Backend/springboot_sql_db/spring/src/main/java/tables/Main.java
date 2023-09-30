package tables;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import manytoone.Artists.Artist;
import manytoone.Artists.ArtistRepository;
import onetomany.Songs.Song;
import onetomany.Songs.SongRepository;

/**
 * 
 * @author Conor O'Shea
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 Songs with their machines
    /**
     * 
     * @param SongRepository repository for the Song entity
     * @param ArtistRepository repository for the Artist entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in Song.java just associating the Artist object with the Song will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initSong(SongRepository songRepository, ArtistRepository artistRepository) {
        return args -> {
            Artist artist1 = new Artist("Kanye", 7, 15, {}, {});
            Artist artist2 = new Artist("Carti", 1, 1, {}, {});
            Artist artist3 = new Artist("Fivio", 0, 2, {}, {});
            Song song1 = new Song("Stop Breathing", {}, "Rap");
            Song song2 = new Song("Off the Grid", {Carti, Fivio}, "Rap");
            Song song3 = new Song("City of Gods", {Kanye}, "Rap");
            song1.setArtist(artist1);
            song2.setArtist(artist2);
            song3.setArtist(artist3);
            songRepository.save(song1);
            songRepository.save(song2);
            songRepository.save(song3);

        };
    }

}
