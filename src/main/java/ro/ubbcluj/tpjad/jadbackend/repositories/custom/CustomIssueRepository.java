package ro.ubbcluj.tpjad.jadbackend.repositories.custom;

public interface CustomIssueRepository<ID> {
    void deleteByIdCascade(ID issueId);
}
