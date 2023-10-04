package onetoone.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/users")
    List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @GetMapping(path = "/leaderboard")
    List<User> getLeaderboard()
    {
        List<User> test = userRepository.findAll();
        test.sort(User::compareTo);
        return test;
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id)
    {
        userRepository.deleteById(id);
        return "success";
    }

    @DeleteMapping(path = "/leaderboard/{name}")
    String deleteUser(@PathVariable String name)
    {
        userRepository.deleteByName(name);
        return "success";
    }
}