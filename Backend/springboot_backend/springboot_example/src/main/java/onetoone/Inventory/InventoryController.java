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

    @GetMapping(path = "/inventory/{name}")
    String getInventoryForUser(@PathVariable String name) {
        User user = userRepository.findByName(name);
        return user.getInventory().inventoryToString();
    }

    @PutMapping(path = "/inventory/{name}/{color}")
    String addColorToInventory(@PathVariable String name, @PathVariable String color)
    {
        return "filler";
    }
}
