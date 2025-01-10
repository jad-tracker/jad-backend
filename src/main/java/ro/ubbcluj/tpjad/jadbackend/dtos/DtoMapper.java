package ro.ubbcluj.tpjad.jadbackend.dtos;

import org.springframework.stereotype.Component;
import ro.ubbcluj.tpjad.jadbackend.models.Issue;
import ro.ubbcluj.tpjad.jadbackend.models.Project;
import ro.ubbcluj.tpjad.jadbackend.models.ProjectMember;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class DtoMapper {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ProjectGetDto convertProject(Project project) {
        return new ProjectGetDto(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getLead().getUsername()
        );
    }

    public List<ProjectGetDto> convertProjects(List<Project> projects) {
        return projects.stream()
            .map(this::convertProject)
            .toList();
    }

    public ProjectMemberGetDto convertProjectMember(ProjectMember projectMember) {
        return new ProjectMemberGetDto(
            projectMember.getUser().getId(),
            projectMember.getUser().getUsername(),
            projectMember.getRole()
        );
    }

    public List<ProjectMemberGetDto> convertProjectMembers(List<ProjectMember> projectMembers) {
        return projectMembers.stream()
            .map(this::convertProjectMember)
            .toList();
    }

    public IssueGetDto convertIssue(Issue issue) {
        return new IssueGetDto(
                issue.getId(),
                issue.getSummary(),
                issue.getDescription(),
                issue.getType().name(),
                issue.getStatus().name(),
                formatter.format(issue.getDate()),
                issue.getAssignee().getUsername()
        );
    }

    public List<IssueGetDto> convertIssues(List<Issue> issues) {
        return issues.stream()
                .map(this::convertIssue)
                .toList();
    }
}
