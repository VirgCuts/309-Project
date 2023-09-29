package onetoone.Artist;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import onetoone.Song.Song;

/**
 * 
 * @author Vivek Bengre
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
    private String[] hasFeatures;
    private String[] featuredOn;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * @JsonIgnore is to assure that there is no infinite loop while returning either user/laptop objects (laptop->user->laptop->...)
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "artist_id")
    private Song song;

    public Artist(int id, String artistName, int numPlatinums, int numGrammys, String[] hasFeatures,
                   String[] featuredOn) {
        this.id = id;
        this.artistName = artistName;
        this.numPlatinums = numPlatinums;
        this.numGrammys = numGrammys;
        this.hasFeatures = hasFeatures;
        this.featuredOn = featuredOn;
    }

    public Artist() {
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

    public String[] getHasFeatures(){
        return hasFeatures;
    }

    public void setHasFeatures(String[] hasFeatures){
        this.hasFeatures = hasFeatures;
    }

    public String[] getFeaturedOn(){
        return featuredOn;
    }

    public void setFeaturedOn(String[] featuredOn){
        this.featuredOn = featuredOn;
    }

}
