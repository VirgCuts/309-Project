package onetoone.Inventory;

import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/inventory/{name}")
    String getInventoryForUser(@PathVariable String name) {
        User user = userRepository.findByName(name);
        if (user == null)
            return failure;
        return user.getInventory().inventoryToString();
    }

    @PutMapping(path = "/inventory/{name}/{color}")
    String addColorToInventory(@PathVariable String name, @PathVariable String color)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return failure;
        Inventory inventory = user.getInventory();
        if (color.equals("orange"))
            inventory.setOrange(true);
        if (color.equals("purple"))
            inventory.setPurple(true);
        if (color.equals("lightblue"))
            inventory.setLightblue(true);
        if (color.equals("yellow"))
            inventory.setYellow(true);
        if (color.equals("magenta"))
            inventory.setMagenta(true);
        if (color.equals("green"))
            inventory.setGreen(true);
        user.setInventory(inventory);
        userRepository.save(user);
        return success;
    }
}
