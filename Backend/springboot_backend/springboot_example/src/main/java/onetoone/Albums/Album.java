package onetoone.Albums;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import onetoone.Artists.Artist;
import onetoone.Songs.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Conor O'Shea
 */ 

@Entity
public class Album implements Serializable {
    
    /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ApiModelProperty(notes = "Name of the Album",name="albumName",required=true,value="test name")
    private String albumName;
    @ApiModelProperty(notes = "Genre of the Album",name="genre",required=true,value="test genre")
    private String genre;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
//    @ManyToOne
//    @JoinColumns({
//            @JoinColumn(name = "artist_id", referencedColumnName = "id"),
//            @JoinColumn(name = "artist_name", referencedColumnName = "name")
//    })
//    @JsonIgnore
//    private Artist artist;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Song> songs;

    public Album(String albumName, String genre) {
        this.albumName = albumName;
        this.genre = genre;
        songs = new ArrayList<>();
    }

    public Album() {
        songs = new ArrayList<>();
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getAlbumName(){
        return albumName;
    }

    public void setAlbumName(String albumName){
        this.albumName = albumName;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void addSongs(Song song){
        this.songs.add(song);
    }

}
