package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to Immaculate Taste, the music grid guessing game where you must" +
                "fill in the grid in only 9 guesses!";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        String returner = "Hello and welcome " + name + " to Immaculate Taste, the music grid guessing" +
                "game where you must fill in the grid in only 9 guesses!";
        return name;
    }
}
