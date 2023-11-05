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
        return "{ \"orange\": " + this.orange + ", \"purple\": " + this.purple + ", \"lightblue\": " + this.lightblue + ", \"yellow\": " + this.yellow + ", \"magenta\": " + this.magenta + ", \"green\": " + this.green + " }";
    }

    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }
}
