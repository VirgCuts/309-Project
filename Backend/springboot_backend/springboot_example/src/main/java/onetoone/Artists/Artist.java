package onetoone.Artists;

import javax.persistence.*;

import io.swagger.annotations.ApiModelProperty;
import onetoone.Songs.Song;
import onetoone.Albums.Album;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Conor O'Shea
 * 
 */ 

@Entity
public class Artist implements Serializable {

     /* 
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ApiModelProperty(notes = "Name of the Artist",name="name",required=true,value="test name")
    private String name;

    @ApiModelProperty(notes = "Number of Platinums for the Artist",name="numPlatinums",required=true,value="test numPlatinums")
    private int numPlatinums;
    @ApiModelProperty(notes = "Number of Grammys for the Artist",name="numGrammys",required=true,value="test numGrammys")
    private int numGrammys;



    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    @OneToMany(cascade = CascadeType.ALL)
    private List<Song> songs;

//    @OneToMany(cascade = CascadeType.ALL)
////    @JoinColumn(name = "album_id")
//    private List<Album> albums;

    public Artist(String name, int numPlatinums, int numGrammys) {
        this.name = name;
        this.numPlatinums = numPlatinums;
        this.numGrammys = numGrammys;
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
