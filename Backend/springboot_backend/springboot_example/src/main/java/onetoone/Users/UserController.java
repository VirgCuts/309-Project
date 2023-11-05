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

    //registration, should be used for making users from now on
    @PostMapping(path = "/users/{name}/{password}")
    String addSingleUserWithPassword(@PathVariable String name, @PathVariable String password)
    {
        User user = new User(name, password);
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

    @PutMapping(path = "/canChat/{name}/true")
    String unbanUserFromChat(@PathVariable String name)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return failure;
        user.setCanChat(true);
        userRepository.save(user);
        return success;
    }

    @PutMapping(path = "/canChat/{name}/false")
    String banUserFromChat(@PathVariable String name)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return failure;
        user.setCanChat(false);
        userRepository.save(user);
        return success;
    }

    @GetMapping(path = "/banStrikes/{name}")
    int getBanStrikeCountForUser(@PathVariable String name)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return 0;
        return user.getBanStrikes();
    }


    /*
    Mapping for color related requests
     */
    @GetMapping(path = "/gameColor/{name}")
    String getSelectedColor(@PathVariable String name)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return failure;
        return "{\"numSongs\":\"" + user.getSelectedColor() + "\"}";
    }

    @PutMapping(path = "/gameColor/{name}/{color}")
    String setSelectedColor(@PathVariable String name, @PathVariable String color)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return failure;
        user.setSelectedColor(color);
        userRepository.save(user);
        return success;
    }
}