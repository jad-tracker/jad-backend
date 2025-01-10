package ro.ubbcluj.tpjad.jadbackend.services;

import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectMemberGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectMemberPostDto;

import java.util.List;

public interface ProjectMemberService {
    List<ProjectMemberGetDto> getAllProjectMembersForProject(Long projectId);
    ProjectMemberGetDto addProjectMemberToProject(ProjectMemberPostDto projectMemberPostDto, Long projectId);
    void deleteProjectMemberFromProject(Long memberId, Long projectId);
}
