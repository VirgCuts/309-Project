package onetoone.Inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import onetoone.Users.User;

import javax.persistence.*;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean orange;
    private boolean purple;
    private boolean lightblue;
    private boolean yellow;
    private boolean magenta;
    private boolean green;

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

    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user;}

    public void setOrange(boolean orange) {this.orange = orange; }
    public void setPurple(boolean purple) {this.purple = purple; }
    public void setLightblue(boolean lightblue) {this.lightblue = lightblue; }
    public void setYellow(boolean yellow) {this.yellow = yellow; }
    public void setMagenta(boolean magenta) {this.magenta = magenta; }
    public void setGreen(boolean green) {this.green = green; }

}
