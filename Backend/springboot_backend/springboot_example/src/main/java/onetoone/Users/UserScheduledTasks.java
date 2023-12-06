package onetoone.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserScheduledTasks {
    @Autowired
    UserRepository userRepository;

//    @Scheduled(fixedDelay = 3600000)
//    public void hourlyUpdate() {
//        for (User user: userRepository.findAll()) {
//            Date now = new Date();
//            if (now.getTime() - user.getHighScoreTime().getTime() > 604800000)
//                user.resetWeeklyScore();
//            if (now.getTime() - user.getHighScoreTime().getTime() > 2628000000L)
//                user.resetMonthlyScore();
//            userRepository.save(user);
//        }
//    }

    @Scheduled(fixedDelay = 604800000)
    public void weeklyUpdate(){
        for (User user: userRepository.findAll()){
            user.resetWeeklyScore();
        }
    }

    @Scheduled(fixedDelay = 2628000000L)
    public void monthlyUpdate(){
        for (User user: userRepository.findAll()){
            user.resetMonthlyScore();
        }
    }
}
