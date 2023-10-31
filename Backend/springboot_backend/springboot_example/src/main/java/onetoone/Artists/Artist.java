package onetoone.Artists;

import javax.persistence.*;

import onetoone.Songs.Song;
import onetoone.Albums.Album;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Vivek Bengre
 * 
 */ 

@Entity
//@IdClass(Artist.class)
//public class Artist implements Serializable {
public class Artist {

     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Id
    private String name;
    private int numPlatinums;
    private int numGrammys;
//    private String emailId;
//    private boolean ifActive;


    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "song_id")
//    private Song song;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Song> songs;

    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "album_id")
    private List<Album> albums;

    public Artist(String name, int numPlatinums, int numGrammys) {
        this.name = name;
        this.numPlatinums = numPlatinums;
        this.numGrammys = numGrammys;
        songs = new ArrayList<>();
        albums = new ArrayList<>();
    }

    public Artist() {
        songs = new ArrayList<>();
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getNumPlatinums(){
        return numPlatinums;
    }

    public void setNumPlatinums(int numPlatinums){
        this.numPlatinums = numPlatinums;
    }

    public int getNumGrammys(){
        return numGrammys;
    }

    public void setNumGrammys(int numGrammys){
        this.numGrammys = numGrammys;
    }

//    public Song getSong(){
//        return song;
//    }
//
//    public void setSong(Song song){
//        this.song = song;
//    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSongs(Song song){
        this.songs.add(song);
    }

//    public Album getAlbum(){
//        return album;
//    }
//
//    public void setAlbum(Album album){
//        this.album = album;
//    }
    public List<Album> getAlbums() {
    return albums;
}

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    public void addAlbums(Album album){
        this.albums.add(album);
    }
    
}
