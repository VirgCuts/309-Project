package onetoone.Reports;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import onetoone.Artists.Artist;

import javax.persistence.*;
import java.util.Date;
import onetoone.Users.User;
@Entity
public class Report {
    @ApiModelProperty(notes = "Id provided for database", name="id", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(notes = "Name of the reporting user", name="username")
    @Column
    private String username;

    @ApiModelProperty(notes = "Name of the reported user", name="reportedUsername")
    @Column
    private String reportedUsername;

    @ApiModelProperty(notes = "Reason provided by reporting user for why the report was made", name="content")
    @Lob
    private String content;

    @ApiModelProperty(notes = "Message that was reported", name="reportedMessage")
    @Lob
    private String reportedMessage;

    @ApiModelProperty(notes = "Date report was sent", name="sent")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent")
    private Date sent = new Date();

    @ApiModelProperty(notes = "User Entity that was reported", name="reportedUser")
    @ManyToOne
    @JoinColumn(name = "reportedUser_id", referencedColumnName = "id")
    @JsonIgnore
    private User reportedUser;

    public Report() {};

    public Report(String username, String reportedUsername, String content, String reportedMessage) {
        this.username = username;
        this.reportedUsername = reportedUsername;
        this.content = content;
        this.reportedMessage = reportedMessage;
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
