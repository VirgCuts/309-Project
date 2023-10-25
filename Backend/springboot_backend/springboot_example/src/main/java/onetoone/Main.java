package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import onetoone.Albums.Album;
import onetoone.Albums.AlbumRepository;
import onetoone.Songs.Song;
import onetoone.Songs.SongRepository;
import onetoone.Artists.Artist;
import onetoone.Artists.ArtistRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"onetoone.Websocket"})
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
    CommandLineRunner initUser(ArtistRepository artistRepository, SongRepository songRepository, UserRepository userRepository, AlbumRepository albumRepository) {
        return args -> {
            Album album1 = new Album("Graduation", "Rap");
            albumRepository.save(album1);
            User user1 = new User("Sam", 1000);
            User user2 = new User("Keenan", 33);
            User user3 = new User("Conor", 49);
            User user4 = new User("Carter", 36);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            try {
                BufferedReader reader = new BufferedReader(new FileReader("km203_song_db.csv"));
                HashMap<String, Artist> hash = new HashMap<>();
                String[] categories = reader.readLine().split(",");
                for (int i = 0; i < 28370; i++) {
                    String[] input = reader.readLine().split(",");
                    String artist_name = input[1];
                    String song_name = input[2];
                    String genre = input[4];
                    if (genre.equals("hip hop")) {
                        Song song = new Song(song_name, genre);
                        if (!hash.containsKey(artist_name)) {
                            Artist artist = new Artist(artist_name, 0, 0);
                            hash.put(artist_name, artist);
                            artist.addSongs(song);
                            artistRepository.save(artist);
                        } else {
                            Artist getter = hash.get(artist_name);
                            getter.addSongs(song);
                            songRepository.save(song);
                        }
                    }
                }
                reader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
