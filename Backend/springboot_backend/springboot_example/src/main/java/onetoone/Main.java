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
import onetoone.Users.User;
import onetoone.Users.UserRepository;

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
    CommandLineRunner initUser(ArtistRepository artistRepository, SongRepository songRepository, UserRepository userRepository) {
        return args -> {
            Artist artist1 = new Artist("John", 1, 2);
            Artist artist2 = new Artist("Jane", 2, 5);
            Artist artist3 = new Artist("Justin", 5, 7);
            Song song1 = new Song("Just Dance", "Pop");
            Song song2 = new Song("Off the Grid", "Rap");
            Song song3 = new Song("Fast Car", "Country");
            artist1.setLaptop(song1);
            artist2.setLaptop(song2);
            artist3.setLaptop(song3);
            artistRepository.save(artist1);
            artistRepository.save(artist2);
            artistRepository.save(artist3);

            User user1 = new User("Sam", 1000);
            User user2 = new User("Keenan", 50);
            User user3 = new User("Conor", 49);
            User user4 = new User("Carter", 30);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

        };
    }

}
