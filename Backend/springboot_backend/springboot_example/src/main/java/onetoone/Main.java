package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import onetoone.Songs.Song;
import onetoone.Songs.SongRepository;
import onetoone.Artists.Artist;
import onetoone.Artists.ArtistRepository;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 users with their machines
    /**
     * 
     * @param artistRepository repository for the User entity
     * @param songRepository repository for the Laptop entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Laptop object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(ArtistRepository artistRepository, SongRepository songRepository) {
        return args -> {
            Artist user1 = new Artist("John", 1, 2);
            Artist user2 = new Artist("Jane", 2, 5);
            Artist user3 = new Artist("Justin", 5, 7);
            Song laptop1 = new Song("Just Dance", "Pop");
            Song laptop2 = new Song("Off the Grid", "Rap");
            Song laptop3 = new Song("Fast Car", "Country");
            user1.setLaptop(laptop1);
            user2.setLaptop(laptop2);
            user3.setLaptop(laptop3);            
            artistRepository.save(user1);
            artistRepository.save(user2);
            artistRepository.save(user3);

        };
    }

}
