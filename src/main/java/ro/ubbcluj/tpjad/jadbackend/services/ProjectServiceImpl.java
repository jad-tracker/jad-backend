package ro.ubbcluj.tpjad.jadbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubbcluj.tpjad.jadbackend.dtos.DtoMapper;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectPutDto;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.EntityNotFoundException;
import ro.ubbcluj.tpjad.jadbackend.models.Project;
import ro.ubbcluj.tpjad.jadbackend.models.User;
import ro.ubbcluj.tpjad.jadbackend.repositories.ProjectRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.UserRepository;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final DtoMapper dtoMapper;

    @Autowired
    public ProjectServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<ProjectGetDto> getAllProjectsForUser(String username) {
        List<Project> projects = projectRepository.findProjectsForUser(username);

        return dtoMapper.convertProjects(projects);
    }

    @Override
    public ProjectGetDto createProject(ProjectPostDto projectPostDto, String username) {
        User leadUser = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("username", username, User.class.getSimpleName()));

        Project project = new Project(
            null,
            projectPostDto.getName(),
            projectPostDto.getDescription(),
            leadUser
        );
        Project savedProject = projectRepository.save(project);

        return dtoMapper.convertProject(savedProject);
    }

    @Override
    public ProjectGetDto updateProject(ProjectPutDto projectPutDto, Long projectId) {
        Project foundProject = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(projectId, Project.class.getSimpleName()));

        foundProject.setName(projectPutDto.getName());
        Project updatedProject = projectRepository.save(foundProject);

        return dtoMapper.convertProject(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(projectId, Project.class.getSimpleName()));

        projectRepository.deleteByIdCascade(projectId);
    }
}
