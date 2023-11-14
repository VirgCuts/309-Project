package onetoone.Inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import onetoone.Users.User;

import javax.persistence.*;

@Entity
public class Inventory {
    @ApiModelProperty(notes = "Id provided for database", name="id", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ApiModelProperty(notes = "Whether or not the user has purchased the orange color", name="orange")
    private boolean orange;
    @ApiModelProperty(notes = "Whether or not the user has purchased the purple color", name="purple")
    private boolean purple;
    @ApiModelProperty(notes = "Whether or not the user has purchased the light blue color", name="lightblue")
    private boolean lightblue;
    @ApiModelProperty(notes = "Whether or not the user has purchased the yellow color", name="yellow")
    private boolean yellow;
    @ApiModelProperty(notes = "Whether or not the user has purchased the magenta color", name="magenta")
    private boolean magenta;
    @ApiModelProperty(notes = "Whether or not the user has purchased the green color", name="green")
    private boolean green;

    @ApiModelProperty(notes = "User of this inventory", name="user")
    @OneToOne
    @JsonIgnore
    private User user;

    public Inventory() {
        orange = false;
        purple = false;
        lightblue = false;
        yellow = false;
        magenta = false;
        green = false;
    }

    public String inventoryToString() {
        return "" + this.orange + ", " + this.purple + ", " + this.lightblue + ", " + this.yellow + ", " + this.magenta + ", " + this.green;
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user;}
    public boolean getOrange() { return this.orange; }
    public void setOrange(boolean orange) {this.orange = orange; }
    public boolean getPurple() { return this.purple; }
    public void setPurple(boolean purple) {this.purple = purple; }
    public boolean getLightblue() { return this.lightblue; }
    public void setLightblue(boolean lightblue) {this.lightblue = lightblue; }
    public boolean getYellow() { return this.yellow; }
    public void setYellow(boolean yellow) {this.yellow = yellow; }
    public boolean getMagenta() { return this.magenta; }
    public void setMagenta(boolean magenta) {this.magenta = magenta; }
    public boolean getGreen() { return this.green; }
    public void setGreen(boolean green) {this.green = green; }

}
