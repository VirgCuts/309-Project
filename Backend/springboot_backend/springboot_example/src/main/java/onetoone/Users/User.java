package onetoone.Users;

import onetoone.Songs.Song;

import javax.persistence.*;
import java.util.*;

@Entity
public class User implements Comparator<User>, Comparable<User> {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int highScore;

    private List<String> chatLogs;


    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    public User(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    public User() {
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

    public int getHighScore(){
        return highScore;
    }

    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    public List<String> getChatLogs(){
        return chatLogs;
    }

    public void addToChatLogs(String message){

    }

    @Override
    public int compareTo(User o) {
        return o.highScore - this.highScore;
    }

    @Override
    public int compare(User o1, User o2) {
        return o2.highScore - o1.highScore;
    }
}
