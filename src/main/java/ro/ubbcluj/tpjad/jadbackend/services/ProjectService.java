package ro.ubbcluj.tpjad.jadbackend.services;

import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectPutDto;

import java.util.List;

public interface ProjectService {
    List<ProjectGetDto> getAllProjectsForUser(String username);
    ProjectGetDto createProject(ProjectPostDto projectPostDto, String username);
    ProjectGetDto updateProject(ProjectPutDto projectPutDto, Long projectId);
    void deleteProject(Long projectId);
}
