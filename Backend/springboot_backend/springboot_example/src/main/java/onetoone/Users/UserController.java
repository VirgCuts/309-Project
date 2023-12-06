package onetoone.Users;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *
 * @author Sam Lickteig
 *
 */

@Api(value = "UserController", description = "REST APIs related to User Entity, created by Sam Lickteig")
@RestController
public class UserController {

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Autowired
    UserRepository userRepository;

    @ApiOperation(value = "Get all users")
    @GetMapping(path = "/users")
    List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @ApiOperation(value = "Add user with provided name")
    @PostMapping(path = "/users/{name}")
    String addSingleUser(@PathVariable String name)
    {
        User user = new User();
        user.setName(name);
        userRepository.save(user);
        return success;
    }

    @ApiOperation(value = "Register user with provided name, password, and email")
    @PostMapping(path = "/users/{name}/{password}/{email}")
    String createAccount(@PathVariable String name, @PathVariable String password, @PathVariable String email)
    {
        User user = new User(name, password, email);
        if(userRepository.findByName(name) != null)
            return failure;
        userRepository.save(user);
        return success;
    }

    @ApiOperation(value = "Check if username and password are correct")
    @GetMapping(path = "/users/{name}/{password}")
    boolean login(@PathVariable String name, @PathVariable String password)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return false;
        return user.getPassword().equals(password);
    }

    @GetMapping(path = "/forgot/{email}")
    String forgotPassword(@PathVariable String email) throws MessagingException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return failure;
        return user.getPassword();
    }

    //mappings made for current and/or original leaderboard design
    @ApiOperation(value = "Get all users and sort by high score")
    @GetMapping(path = "/leaderboard")
    List<User> getLeaderboard()
    {
        List<User> test = userRepository.findAll();
        test.sort(User::compareTo);
        return test;
    }

    @ApiOperation(value = "Find individual user information for leaderboard")
    @GetMapping(path = "/leaderboard/{name}")
    User getUserLeaderboardInfo(@PathVariable String name)
    {
        return userRepository.findByName(name);
    }

    @ApiOperation(value = "Update user score with provided name")
    @PutMapping(path = "/leaderboard/{name}/{score}")
    User putUserLeaderboard(@PathVariable String name, @PathVariable int score)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return null;
        user.setAllHighScores(score);
        userRepository.save(user);
        return userRepository.findByName(name);
    }

    @ApiOperation(value = "Delete user with provided name")
    @DeleteMapping(path = "/leaderboard/{name}")
    String deleteUser(@PathVariable String name)
    {
        userRepository.deleteByName(name);
        return success;
    }
    //end of leaderboard mappings


    @ApiOperation(value = "Update user so that they are unbanned from chat")
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

    @ApiOperation(value = "Update user so that they are banned from chat")
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

    @ApiOperation(value = "Get amount of ban strikes user has")
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
    @ApiOperation(value = "Get user's selected color for game")
    @GetMapping(path = "/gameColor/{name}")
    String getSelectedColor(@PathVariable String name)
    {
        User user = userRepository.findByName(name);
        if (user == null)
            return failure;
        return "{\"color\":\"" + user.getSelectedColor() + "\"}";
    }

    @ApiOperation(value = "Update user's selected color for game")
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