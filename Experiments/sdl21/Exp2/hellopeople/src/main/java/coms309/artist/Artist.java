package coms309.artist;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Artist {

    private String artistName;

    private char gender;

    private int numGrammies;

    private int numPlatinums;

    public Artist(){
        
    }

    public Artist(String artistName, char gender, int numGrammies, int numPlatinums){
        this.artistName = artistName;
        this.gender = gender;
        this.numGrammies = numGrammies;
        this.numPlatinums = numPlatinums;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public char getGender() {
        return this.gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getNumGrammies() {
        return this.numGrammies;
    }

    public void setNumGrammies(int numGrammies) {
        this.numGrammies = numGrammies;
    }

    public int getNumPlatinums() {
        return this.numPlatinums;
    }

    public void setNumPlatinums(int numPlatinums) {
        this.numPlatinums = numPlatinums;
    }

    @Override
    public String toString() {
        return artistName + " " 
               + gender + " "
               + numGrammies + " "
               + numPlatinums;
    }
}
