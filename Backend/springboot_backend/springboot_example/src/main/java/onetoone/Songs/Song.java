package onetoone.Songs;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import onetoone.Artists.Artist;

/**
 * 
 * @author Vivek Bengre
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

    @ApiModelProperty(notes = "Name of the Song",name="songName",required=true,value="test name")
    private String songName;
    @ApiModelProperty(notes = "Genre of the Song",name="genre",required=true,value="test genre")
    private String genre;
    @ApiModelProperty(notes = "Features on the Song",name="feature",required=true,value="test feature")
    private String feature;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
//    @OneToOne
//    @JsonIgnore
//    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "id")
    @JoinColumn(name = "artist_name", referencedColumnName = "name")
    @JsonIgnore
    private Artist artist;


    public Song(String songName, String genre, String feature) {
        this.songName = songName;
        this.genre = genre;
        this.feature = feature;
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

    public String getGenre(){
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public String getFeature(){
        return feature;
    }

    public void setFeature(String feature){
        this.feature = feature;
    }

    public Artist getArtist(){
        return artist;
    }

    public void setArtist(Artist artist){
        this.artist = artist;
    }

}
