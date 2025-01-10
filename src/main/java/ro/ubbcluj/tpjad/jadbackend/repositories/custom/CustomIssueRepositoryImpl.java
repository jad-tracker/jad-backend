package ro.ubbcluj.tpjad.jadbackend.repositories.custom;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import ro.ubbcluj.tpjad.jadbackend.repositories.CommentRepository;

public class CustomIssueRepositoryImpl implements CustomIssueRepository<Long> {
    private final EntityManager entityManager;
    private final CommentRepository commentRepository;

    public CustomIssueRepositoryImpl(EntityManager entityManager, CommentRepository commentRepository) {
        this.entityManager = entityManager;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void deleteByIdCascade(Long issueId) {
        commentRepository.deleteAllByIssueId(issueId);

        entityManager
            .createQuery("delete from Issue i where i.id=:issueId")
            .setParameter("issueId", issueId)
            .executeUpdate();
    }
}
