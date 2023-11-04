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

    @PostMapping(path = "/report/{username}/{reportedUsername}")
    String makeReport(@PathVariable String username, @PathVariable String reportedUsername, @RequestBody String content) {
        User user = userRepository.findByName(reportedUsername);
        if (user == null)
            return failure;
        Report report = new Report(username, reportedUsername, content);
        user.addReport(report);
        userRepository.save(user);
        return success;
    }
}
