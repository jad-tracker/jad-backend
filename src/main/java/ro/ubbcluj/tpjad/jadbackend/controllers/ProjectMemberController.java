package ro.ubbcluj.tpjad.jadbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectMemberGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectMemberPostDto;
import ro.ubbcluj.tpjad.jadbackend.services.ProjectMemberService;

import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@CrossOrigin
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    @Autowired
    public ProjectMemberController(ProjectMemberService projectMemberService) {
        this.projectMemberService = projectMemberService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectMemberGetDto>> getAllProjectForUser(@PathVariable Long projectId) {
        List<ProjectMemberGetDto> projectMembers = projectMemberService.getAllProjectMembersForProject(projectId);

        return new ResponseEntity<>(projectMembers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectMemberGetDto> addProjectMemberToProject(@PathVariable Long projectId, @RequestBody ProjectMemberPostDto projectMemberPostDto) {
       ProjectMemberGetDto createdProjectMember = projectMemberService.addProjectMemberToProject(projectMemberPostDto, projectId);

       return new ResponseEntity<>(createdProjectMember, HttpStatus.CREATED);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteProjectMemberFromProject(@PathVariable Long projectId, @PathVariable Long memberId) {
        projectMemberService.deleteProjectMemberFromProject(memberId, projectId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
