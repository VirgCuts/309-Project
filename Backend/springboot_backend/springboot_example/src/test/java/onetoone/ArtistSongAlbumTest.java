package onetoone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;

import onetoone.Albums.Album;
import onetoone.Artists.Artist;
import onetoone.Songs.Song;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ArtistSongAlbumTest {

    int id;
    int song_id;
    String name;
    int numGrammys;
    int numPlatinums;
    String songName;
    String genre;
    String feature;
    int album_id;
    String albumName;
    String album_genre;

    public ArtistSongAlbumTest(int id, String name, int numGrammys, int numPlatinums, int song_id,
                               String songName, String genre, String feature, int album_id, String albumName,
                               String album_genre) {
        this.id = id;
        this.name = name;
        this.numGrammys = numGrammys;
        this.numPlatinums = numPlatinums;

        this.song_id = song_id;
        this.songName = songName;
        this.genre = genre;
        this.feature = feature;

        this.album_id = album_id;
        this.albumName = albumName;
        this.album_genre = album_genre;
    }


    @Parameters
    public static Collection<Object[]> getParameters() {
        Collection<Object[]> retList = new ArrayList<Object[]>();
        // split the line using delimiter and then create the test-case object
        Object[] d = new Object[11];
        d[0] = 1;
        d[1] = "Drake";
        d[2] = 4;
        d[3] = 20;
        d[4] = 1;
        d[5] = "One Dance";
        d[6] = "Dancehall";
        d[7] = "Wizkid";
        d[8] = 0;
        d[9] = "Views";
        d[10] = "Rap";

        // add the test data into the arraylist
        retList.add(d);
        // return all the test-cases
        return retList;
    }

    @Test  // run this for each test-case in the above collection
    public void testGetterAndSetters() throws Throwable {
        Artist artist = new Artist(name, numPlatinums, numGrammys);
        Song song = new Song(songName, genre, feature);
        ArrayList<Song> songList = new ArrayList<>();
        songList.add(song);
        Album album = new Album(albumName, album_genre);

        artist.setId(id);
        assertEquals(artist.getId(), id);
        artist.setName(name);
        assertEquals(artist.getName(), name);
        artist.setNumGrammys(numGrammys);
        assertEquals(artist.getNumGrammys(), numGrammys);
        artist.setNumPlatinums(numPlatinums);
        assertEquals(artist.getNumPlatinums(), numPlatinums);
        artist.setSongs(songList);
        assertEquals(artist.getSongs(), songList);

        song.setId(song_id);
        assertEquals(song.getId(), song_id);
        song.setSongName(songName);
        assertEquals(song.getSongName(), songName);
        song.setGenre(genre);
        assertEquals(song.getGenre(), genre);
        song.setFeature(feature);
        assertEquals(song.getFeature(), feature);
        song.setArtist(artist);
        assertEquals(song.getArtist().getName(), artist.getName());
        assertEquals(song.getArtist().getId(), artist.getId());
        song.setAlbum(album);
        assertEquals(song.getAlbum().getAlbumName(), album.getAlbumName());

        album.setId(album_id);
        assertEquals(album.getId(), album_id);
        album.setAlbumName(albumName);
        assertEquals(album.getAlbumName(), albumName);
        album.setGenre(album_genre);
        assertEquals(album.getGenre(), album_genre);
        album.setSongs(songList);
        assertEquals(album.getSongs(), songList);

        //testing empty constructor, only non-null value should be songs
        Artist artist2 = new Artist();
        assertNotNull(artist2.getSongs());

        Album album2 = new Album();
        assertNotNull(album2.getSongs());
    }
}
