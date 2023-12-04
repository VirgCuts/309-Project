package onetoone.Users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiModelProperty;
import onetoone.Inventory.Inventory;
import onetoone.Reports.Report;
import onetoone.Songs.Song;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Entity
public class User implements Comparator<User>, Comparable<User> {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @ApiModelProperty(notes = "Id provided for database", name="id", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ApiModelProperty(notes = "Name/Username of the User", name="name", required = true)
    private String name;
    @ApiModelProperty(notes = "Password of the User", name="password", required = true)
    private String password;
    @ApiModelProperty(notes = "High score of the User", name="highScore")
    private int highScore;
    @ApiModelProperty(notes = "This months high score of the User", name="highScoreMonthly")
    private int highScoreMonthly;
    @ApiModelProperty(notes = "This weeks high score of the User", name="highScoreWeekly")
    private int highScoreWeekly;
    @ApiModelProperty(notes = "This weeks high score of the User", name="highScoreWeekly")
    private Date highScoreTime;
    @ApiModelProperty(notes = "Boolean value of users ability to chat", name="canChat")
    private boolean canChat;
    @ApiModelProperty(notes = "Number of ban strikes user currently has", name="banStrikes")
    private int banStrikes;
    @ApiModelProperty(notes = "User's currently selected color", name="selectedColor")
    private String selectedColor;
    @ApiModelProperty(notes = "Reports made about user", name="reports")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Report> reports;

    @ApiModelProperty(notes = "Inventory belonging to user", name="inventory")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;

    @ApiModelProperty(notes = "Board used during game", name="board")
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
        this.highScoreTime = new Date();
        this.highScoreMonthly = 0;
        this.highScoreWeekly = 0;
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
        this.highScoreTime = new Date();
        this.highScoreMonthly = highScore;
        this.highScoreWeekly = highScore;
        this.board = new Board();
        this.canChat = true;
        this.banStrikes = 0;
        inventory = new Inventory();
        this.selectedColor = "white";
    }

    public User()
    {
        this.highScoreTime = new Date();
        this.board = new Board();
        this.inventory = new Inventory();
        this.selectedColor = "white";
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

    public Date getHighScoreTime(){
        return highScoreTime;
    }

    public void setHighScoreTime(Date highScoreTime){
        this.highScoreTime = highScoreTime;
    }
    public int getHighScoreMontly(){
        return highScoreMonthly;
    }

    public void setHighScoreMonthly(int highScore) { this.highScoreMonthly = highScore; }

    public int getHighScoreWeekly(){
        return highScoreWeekly;
    }

    public void setHighScoreWeekly(int highScore) { this.highScoreWeekly = highScore; }

    public void setAllHighScores(int highScore) {
        Date now = new Date();
        if (this.highScore < highScore){
            this.highScore = highScore;
            this.highScoreTime = new Date();
        }
        if (this.highScoreMonthly < highScore) {
            this.highScoreMonthly = highScore;
        }
        if (this.highScoreWeekly < highScore) {
            this.highScoreWeekly = highScore;
        }
    }

    public void resetWeeklyScore() {
        this.highScoreWeekly = 0;
    }

    public void resetMonthlyScore() {
        this.highScoreMonthly = 0;
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
