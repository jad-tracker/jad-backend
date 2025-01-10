package ro.ubbcluj.tpjad.jadbackend.repositories.custom;

public interface CustomProjectRepository<ID> {
    void deleteByIdCascade(ID projectId);
}
