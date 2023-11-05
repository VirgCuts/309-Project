package onetoone.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import onetoone.Inventory.Inventory;
import onetoone.Reports.Report;
import onetoone.Songs.Song;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;

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
    private String password;
    private int highScore;
    private int highScoreMonthly;
    private int highScoreWeekly;
    private int highScoreTime;
    private int highScoreTimeMonthly;
    private int highScoreTimeWeekly;
    private boolean canChat;
    private int banStrikes;
    private String selectedColor;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Report> reports;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Transient
    public Board board;


    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(User)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a user object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the user table will have a field called laptop_id
     */

    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.highScore = 0;
        this.highScoreTime = 0;
        this.highScoreMonthly = 0;
        this.highScoreTimeMonthly = 0;
        this.highScoreWeekly = 0;
        this.highScoreTimeWeekly = 0;
        this.board = new Board();
        this.canChat = true;
        this.banStrikes = 0;
        inventory = new Inventory();
        this.selectedColor = "white";
    }

    public User(String name, int highScore) {
        this.name = name;
        this.password = "";
        this.highScore = highScore;
        this.highScoreTime = 0;
        this.highScoreMonthly = 0;
        this.highScoreTimeMonthly = 0;
        this.highScoreWeekly = 0;
        this.highScoreTimeWeekly = 0;
        this.board = new Board();
        this.canChat = true;
        this.banStrikes = 0;
        inventory = new Inventory();
        this.selectedColor = "white";
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

    public Board getBoard(){
        return board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getHighScore(){
        return highScore;
    }

    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    public int getHighScoreTime(){
        return highScore;
    }

    public void setHighScoreTime(int highScoreTime){
        this.highScoreTime = highScoreTime;
    }
    public int getHighScoreTimeMontly(){
        return highScoreMonthly;
    }

    public void setHighScoreTimeMonthly(int highScoreTimeMonthly){
        this.highScoreTimeMonthly = highScoreTimeMonthly;
    }

    public int getHighScoreTimeWeekly(){
        return highScoreWeekly;
    }

    public void setHighScoreTimeWeekly(int highScoreTimeWeekly){
        this.highScoreTimeWeekly = highScoreTimeWeekly;
    }

    public void setAllHighScores(int highScore, int highScoreTime) {
        if (this.highScore < highScore){
            this.highScore = highScore;
            this.highScoreTime = highScoreTime;
        }
        if (this.highScoreMonthly < highScore) {
            this.highScoreMonthly = highScore;
            this.highScoreTimeMonthly = highScoreTime;
        }
        if (this.highScoreWeekly < highScore) {
            this.highScoreWeekly = highScore;
            this.highScoreTimeWeekly = highScoreTime;
        }
    }

    public void resetWeeklyScore() {
        this.highScoreWeekly = 0;
        this.highScoreTimeWeekly = 0;
    }

    public void resetMonthlyScore() {
        this.highScoreMonthly = 0;
        this.highScoreTimeMonthly = 0;
    }

    public boolean getCanChat(){
        return canChat;
    }

    public void setCanChat(boolean canChat){
        this.canChat = canChat;
    }

    public int getBanStrikes() { return this.banStrikes; }

    public void setBanStrikes(int banStrikes) { this.banStrikes = banStrikes; }

    public String getSelectedColor() { return this.selectedColor; }

    public void setSelectedColor(String selectedColor) { this.selectedColor = selectedColor; }

    public List<Report> getReports() { return this.reports; }

    public void setReports(List<Report> reports) { this.reports = reports; }

    public void addReport(Report report) {this.reports.add(report); }

    public Inventory getInventory() { return this.inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }


    @Override
    public int compareTo(User o) {
        return o.highScore - this.highScore;
    }

    @Override
    public int compare(User o1, User o2) {
        return o2.highScore - o1.highScore;
    }

    public boolean hasWon() { return this.board.getWon(); }

    public String boardToString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.board);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
