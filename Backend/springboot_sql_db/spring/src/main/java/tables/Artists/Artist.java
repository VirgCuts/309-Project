package tables.Artists;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tables.Songs.Song;

/**
 * 
 * @author Conor O'Shea
 */ 

@Entity
public class Artist {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String artistName;
    private int numPlatinums;
    private int numGrammys;
    private List<Artist> hasFeatures;
    private List<Song> featuredOn;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @OneToMany(cascade = CascadeType.ALL)
    private List<Song> songs;

    public Artist(String artistName, int numPlatinums, int numGrammys, List<Artist> hasFeatures,
                   List<Song> featuredOn) {
        this.artistName = artistName;
        this.numPlatinums = numPlatinums;
        this.numGrammys = numGrammys;
        this.hasFeatures = hasFeatures;
        this.featuredOn = featuredOn;
        songs = new ArrayList<>();
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

    public String getArtistName(){
        return artistName;
    }

    public void setArtistName(String artistName){
        this.artistName = artistName;
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

    public List<Artist> getHasFeatures(){
        return hasFeatures;
    }

    public void setHasFeatures(List<Artist> hasFeatures){
        this.hasFeatures = hasFeatures;
    }

    public List<Song> getFeaturedOn(){
        return featuredOn;
    }

    public void setFeaturedOn(List<Song> featuredOn){
        this.featuredOn = featuredOn;
    }

    public void addFeature(Song song){
        this.featuredOn.add(song);
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSong(Song song){
        this.songs.add(song);
    }


}
