package ro.ubbcluj.tpjad.jadbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubbcluj.tpjad.jadbackend.models.Issue;
import ro.ubbcluj.tpjad.jadbackend.repositories.custom.CustomIssueRepository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>, CustomIssueRepository<Long> {
    @Query("delete from Issue i where i.project.id=:projectId")
    @Modifying
    void deleteAllByProjectId(Long projectId);

    @Query("select i from Issue as i where i.project.id=:projectId")
    List<Issue> findIssuesByProject(Long projectId);
}
