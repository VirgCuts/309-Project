package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Conor O'Shea
 */

public class Song {

    private String songName;

    private String artist;

    private String album;

    private String genre;

    public Song(){
        
    }

    public Song(String songName, String artist, String album, String genre){
        this.songName = songName;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) { this.songName = songName; }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setTelephone(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return songName + " "
               + artist + " "
               + album + " "
               + genre;
    }
}
