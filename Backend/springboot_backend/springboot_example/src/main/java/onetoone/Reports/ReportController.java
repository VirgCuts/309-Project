package onetoone.Reports;

import onetoone.Users.UserRepository;
import onetoone.Users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReportController {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/reports")
    List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @GetMapping(path = "/reports/{reportedUsername}")
    List<Report> getAllReportsForUser(@PathVariable String reportedUsername) {
        return reportRepository.findByReportedUser(reportedUsername);
    }

    @PostMapping(path = "/report/{username}/{reported}")
    String makeReport(@PathVariable String username, @PathVariable String reported, @RequestBody String reportInfo) {
        int index;
        String reportedUser;
        String reportedMessage;
        if (reported.contains("[DM from")) {
            index = reported.indexOf(":");
            int dmindex = reported.indexOf("m");
            reportedUser = reported.substring(dmindex + 2, index - 1);
            reportedMessage = reported.substring(index + 2);
        } else {
            index = reported.indexOf(":");
            reportedUser = reported.substring(0, index);
            reportedMessage = reported.substring(index + 2);
        }
        User user = userRepository.findByName(reportedUser);
        if (user == null)
            return failure;
        Report report = new Report(username, reportedUser, reportedMessage, reportInfo);
        user.addReport(report);
        userRepository.save(user);
        return success;
    }
}
