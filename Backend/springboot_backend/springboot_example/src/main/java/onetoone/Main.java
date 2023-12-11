package onetoone;

import onetoone.Inventory.InventoryRepository;
import onetoone.Reports.ReportRepository;
import onetoone.Websocket.MessageRepository;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;

/**
 * 
 * @author Conor O'Shea
 * 
 */ 

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    // Create 3 users with their machines
    /**
     * 
     * @param artistRepository repository for the Artist entity
     * @param songRepository repository for the Song entity
     * @param albumRepository repository for the Album entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Laptop object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(ArtistRepository artistRepository, SongRepository songRepository, UserRepository userRepository,
                               AlbumRepository albumRepository, MessageRepository messageRepository, ReportRepository reportRepository,
                               InventoryRepository inventoryRepository, DatabasePopulateService dbService) {
        return args -> {
            userRepository.deleteAll();
//            Album album1 = new Album("Graduation", "Rap");
//            albumRepository.save(album1);
            User user1 = new User("Sam", "password1", "sdl21@iastate.edu");
            User user2 = new User("Keenan", "password1", "kcjacobs@iastate.edu");
            User user3 = new User("Conor", "password1", "coshea@iastate.edu");
            User user4 = new User("Carter", "password1", "cvcuts@iastate.edu");
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            try {
//                BufferedReader reader = new BufferedReader(new FileReader("km203_song_db.csv"));
                BufferedReader reader = new BufferedReader(new FileReader("cs309newsongdb.csv"));
                HashMap<String, Artist> hash = new HashMap<>();
                HashMap<String, Album> albumhash = new HashMap<>();
                String[] categories = reader.readLine().split(",");
                if (artistRepository.findAll().isEmpty()) {
                    for (int i = 0; i < 101; i++) {
                        String[] input = reader.readLine().split(",");
                        String artist_name = input[0];
                        String song_name = input[1];
                        String genre = input[2];
                        String features = "";
                        String album_name = "";
                        if (input.length == 5) {
                            features = input[3];
                            album_name = input[4];
                        }
                        else {
                            for (int j = 3; j < input.length - 1; j++) {
                                features = features + "," + input[j];
                            }
                            features = features.substring(2, features.length() - 1);
                            album_name = input[input.length - 1];
                        }
                        Song song = new Song(song_name, genre, features);

                        if (!hash.containsKey(artist_name)) {
                            Artist artist = new Artist(artist_name, 0, 0);
                            hash.put(artist_name, artist);
                            Album album = new Album(album_name, genre);
                            albumhash.put(album_name, album);
                            dbService.connectSongToArtistAndAlbum(artist, album, song);
                        }
                        else if (!albumhash.containsKey(album_name)) {
                            Album album = new Album(album_name, genre);
                            albumhash.put(album_name, album);
                            dbService.connectSongToArtistStringAndAlbum(artist_name, album, song);
                        }
                        else {
                            dbService.connectSongToArtistStringAndAlbumString(artist_name, album_name, song);
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
