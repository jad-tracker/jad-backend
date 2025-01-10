package ro.ubbcluj.tpjad.jadbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubbcluj.tpjad.jadbackend.models.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("delete from Comment c where c.issue.project.id=:projectId")
    @Modifying
    void deleteAllByProjectId(Long projectId);

    @Query("delete from Comment c where c.issue.id=:issueId")
    @Modifying
    void deleteAllByIssueId(Long issueId);

    @Query("select c from Comment c where c.issue.id=:issueId")
    List<Comment> findCommentsForIssue(Long issueId);
}
