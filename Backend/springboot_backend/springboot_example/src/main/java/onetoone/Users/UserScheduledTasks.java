package onetoone.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public class UserScheduledTasks {

    @Autowired
    UserRepository userRepository;

    @Scheduled(fixedRate = 3600000)
    public void hourlyUpdate() {
        for (User user: userRepository.findAll()) {
            Date now = new Date();

        }
    }
}
