package onetoone.Reports;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import onetoone.Users.UserRepository;
import onetoone.Users.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Sam Lickteig
 *
 */

@Api(value = "ReportController", description = "REST APIs related to Report Entity, created by Sam Lickteig")
@RestController
public class ReportController {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @ApiOperation(value = "Get all reports made")
    @GetMapping(path = "/reports")
    List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @ApiOperation(value = "Get all reports made about provided user")
    @GetMapping(path = "/reports/{reportedUsername}")
    List<Report> getAllReportsForUser(@PathVariable String reportedUsername) {
        return reportRepository.findByReportedUsername(reportedUsername);
    }

    @ApiOperation(value = "Post report where username is the reporter and reported contains reported user and message")
    @PostMapping(path = "/report/{username}/{reported}")
    String makeReport(@PathVariable String username, @PathVariable @NotNull String reported, @RequestBody String reportInfo) {
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
        report.setReportedUser(user);
        userRepository.save(user);
        return success;
    }
}
