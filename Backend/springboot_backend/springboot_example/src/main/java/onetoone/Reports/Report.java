package onetoone.Reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import onetoone.Artists.Artist;

import javax.persistence.*;
import java.util.Date;
import onetoone.Users.User;
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String reportedUsername;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent")
    private Date sent = new Date();

    @ManyToOne
    @JoinColumn(name = "reportedUser_id")
    @JsonIgnore
    private User reportedUser;

    public Report() {};

    public Report(String username, String reportedUsername, String content) {
        this.username = username;
        this.reportedUsername = reportedUsername;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReportedUsername() {
        return reportedUsername;
    }

    public void setReportedUsername(String reportedUsername) {
        this.reportedUsername = reportedUsername;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public User getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(User user) {
        this.reportedUser = user;
    }
}
