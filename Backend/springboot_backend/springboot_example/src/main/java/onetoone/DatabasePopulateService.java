package onetoone;

import onetoone.Albums.Album;
import onetoone.Albums.AlbumRepository;
import onetoone.Artists.Artist;
import onetoone.Artists.ArtistRepository;
import onetoone.Songs.Song;
import onetoone.Songs.SongRepository;
import onetoone.Users.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DatabasePopulateService {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void connectSongToArtistAndAlbum(Artist artist, Album album, Song song) {
        artist.addSongs(song);
        album.addSongs(song);
        song.setAlbum(album);
        song.setArtist(artist);
        artistRepository.save(artist);
        albumRepository.save(album);
    }

    @Transactional
    public void connectSongToArtistStringAndAlbum(String artistname, Album album, Song song) {
        Artist artist = artistRepository.findByName(artistname);
        artist.addSongs(song);
        album.addSongs(song);
        song.setAlbum(album);
        song.setArtist(artist);
        artistRepository.save(artist);
        albumRepository.save(album);
    }

    @Transactional
    public void connectSongToArtistStringAndAlbumString(String artistname, String albumname, Song song) {
        Artist artist = artistRepository.findByName(artistname);
        Album album = albumRepository.findByAlbumName(albumname);
        artist.addSongs(song);
        album.addSongs(song);
        song.setAlbum(album);
        song.setArtist(artist);
        artistRepository.save(artist);
        albumRepository.save(album);
    }
}
