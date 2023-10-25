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
    CommandLineRunner initUser(ArtistRepository artistRepository, SongRepository songRepository, UserRepository userRepository) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("km203_song_db.csv"));
            String[] artist_names = reader.readLine().split(",");
            String[] song_names = reader.readLine().split(",");
            String[] date = reader.readLine().split(",");
            String[] genres = reader.readLine().split(",");
            Artist[] artist_list = new Artist[artist_names.length];
            HashMap<String, Artist> hash = new HashMap<>();
            for(int i = 0; i < artist_names.length; i++) {
                if (!hash.containsKey(artist_names[i])) {
                    artist_list[i] = new Artist(artist_names[i], 0, 0);
                    hash.put(artist_names[i], artist_list[i]);
                    Song song_input = new Song(song_names[i], genres[i]);
                    artist_list[i].addSongs(song_input);
                    artistRepository.save(artist_list[i]);
                }
                else {
                    Artist getter = hash.get(artist_names[i]);
                    Song song_input = new Song(song_names[i], genres[i]);
                    getter.addSongs(song_input);
                    artistRepository.save(artist_list[i]);
                }
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return args -> {
            Artist artist1 = new Artist("John", 1, 2);
            Artist artist2 = new Artist("Jane", 2, 5);
            Artist artist3 = new Artist("Justin", 5, 7);
            Song song1 = new Song("Just Dance", "Pop");
            Song song2 = new Song("Off the Grid", "Rap");
            Song song3 = new Song("Fast Car", "Country");

            artist1.addSongs(song1);
            artist2.addSongs(song2);
            artist3.addSongs(song3);
            Album album1 = new Album("Graduation", "Rap");
            artist1.setAlbum(album1);

            artistRepository.save(artist1);
            artistRepository.save(artist2);
            artistRepository.save(artist3);

            User user1 = new User("Sam", 1000);
            User user2 = new User("Keenan", 33);
            User user3 = new User("Conor", 49);
            User user4 = new User("Carter", 36);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);

        };
    }

}
