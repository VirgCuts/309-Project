package onetoone.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
public class UserController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/users")
    List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @PostMapping(path = "/users/{name}")
    String addSingleUser(@PathVariable String name)
    {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return success;
    }

    @GetMapping(path = "/leaderboard")
    List<User> getLeaderboard()
    {
        List<User> test = userRepository.findAll();
        test.sort(User::compareTo);
        return test;
    }

    @PostMapping(path = "/leaderboard/{name}/{score}")
    String addUserLeaderboard(@PathVariable String name, @PathVariable int score)
    {
        User user = new User(name, score);
        userRepository.save(user);
        return success;
    }

    @PutMapping(path = "/leaderboard/{name}/{score}")
    User putUserLeaderboard(@PathVariable String name, @PathVariable int score)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return null;
        user.setHighScore(score);
        userRepository.save(user);
        return userRepository.findByName(name);
    }

    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id)
    {
        userRepository.deleteById(id);
        return success;
    }

    @DeleteMapping(path = "/leaderboard/{name}")
    String deleteUser(@PathVariable String name)
    {
        userRepository.deleteByName(name);
        return success;
    }
}