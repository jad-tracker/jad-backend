package ro.ubbcluj.tpjad.jadbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.ubbcluj.tpjad.jadbackend.models.Project;
import ro.ubbcluj.tpjad.jadbackend.repositories.custom.CustomProjectRepository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, CustomProjectRepository<Long> {
    @Query("select p from Project p where p.lead.username=:username or p.id in (select pm.project.id from ProjectMember pm where pm.user.username=:username)")
    List<Project> findProjectsForUser(String username);
}
