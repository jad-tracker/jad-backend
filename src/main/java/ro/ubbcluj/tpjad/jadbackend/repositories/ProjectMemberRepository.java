package ro.ubbcluj.tpjad.jadbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubbcluj.tpjad.jadbackend.models.ProjectMember;
import ro.ubbcluj.tpjad.jadbackend.models.ProjectMemberKey;

import java.util.List;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberKey> {
    @Query("select pm from ProjectMember pm where pm.project.id=:projectId")
    List<ProjectMember> findProjectMembersByProject(Long projectId);

    @Query("delete from ProjectMember pm where pm.project.id=:projectId")
    @Modifying
    void deleteAllByProjectId(Long projectId);
}
