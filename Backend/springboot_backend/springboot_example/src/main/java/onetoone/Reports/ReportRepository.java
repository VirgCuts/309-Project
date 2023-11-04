package onetoone.Reports;

import onetoone.Reports.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReportedUser(String reportedUser);
}
