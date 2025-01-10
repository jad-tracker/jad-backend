package ro.ubbcluj.tpjad.jadbackend.repositories.custom;

import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import ro.ubbcluj.tpjad.jadbackend.repositories.CommentRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.IssueRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.ProjectMemberRepository;

public class CustomProjectRepositoryImpl implements CustomProjectRepository<Long> {
    private final EntityManager entityManager;
    private final ProjectMemberRepository projectMemberRepository;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;

    public CustomProjectRepositoryImpl(
        EntityManager entityManager,
        ProjectMemberRepository projectMemberRepository,
        IssueRepository issueRepository,
        CommentRepository commentRepository
    ) {
        this.entityManager = entityManager;
        this.projectMemberRepository = projectMemberRepository;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void deleteByIdCascade(Long projectId) {
        commentRepository.deleteAllByProjectId(projectId);
        issueRepository.deleteAllByProjectId(projectId);
        projectMemberRepository.deleteAllByProjectId(projectId);

        entityManager
            .createQuery("delete from Project p where p.id=:projectId")
            .setParameter("projectId", projectId)
            .executeUpdate();
    }
}
