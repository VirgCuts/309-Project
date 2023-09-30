package tables.Songs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tables.Artists.Artist;

/**
 * 
 * @author Conor O'Shea
 */ 

@Entity
public class Song {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String songName;
    private List<Artist> features;
    private String genre;

    /*
     * @OneToOne creates a relation between the current entity/table(Song) with the entity/table defined below it(Artist)
     * @JsonIgnore is to assure that there is no infinite loop while returning either Artist/Song objects (Song->Artist->Song->...)
     */
    @ManyToOne
    @JoinColumn(name = "artist_id")
    @JsonIgnore
    private Artist artist;
    // will also need album onetoone in the future

    public Song(String songName, List<Artist> features, String genre) {
        this.songName = songName;
        this.features = features;
        this.genre = genre;
    }

    public Song() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getSongName(){
        return songName;
    }

    public void setSongName(String songName){
        this.songName = songName;
    }

    public int getFeatures(){
        return Features;
    }

    public void setFeatures(List<Artist> features){
        this.features = features;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public Artist getArtist(){
        return artist;
    }

    public void setArtist(Artist artist){
        this.artist = artist;
    }

}
