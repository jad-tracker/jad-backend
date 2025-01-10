package ro.ubbcluj.tpjad.jadbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectPutDto;
import ro.ubbcluj.tpjad.jadbackend.services.ProjectService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectGetDto>> getAllProjectsForUser(Principal principal) {
        String username = principal.getName();

        List<ProjectGetDto> projects = projectService.getAllProjectsForUser(username);

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectGetDto> createProject(@RequestBody ProjectPostDto projectPostDto, Principal principal) {
        String username = principal.getName();

        ProjectGetDto createdProject = projectService.createProject(projectPostDto, username);

        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectGetDto> updateProject(@PathVariable Long projectId, @RequestBody ProjectPutDto projectPutDto) {
        ProjectGetDto updatedProject = projectService.updateProject(projectPutDto, projectId);

        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
