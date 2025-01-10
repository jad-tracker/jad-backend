package ro.ubbcluj.tpjad.jadbackend.services;

import org.springframework.stereotype.Service;
import ro.ubbcluj.tpjad.jadbackend.dtos.DtoMapper;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectMemberGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.ProjectMemberPostDto;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.EntityNotFoundException;
import ro.ubbcluj.tpjad.jadbackend.models.Project;
import ro.ubbcluj.tpjad.jadbackend.models.ProjectMember;
import ro.ubbcluj.tpjad.jadbackend.models.ProjectMemberKey;
import ro.ubbcluj.tpjad.jadbackend.models.User;
import ro.ubbcluj.tpjad.jadbackend.repositories.ProjectMemberRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.ProjectRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.UserRepository;

import java.util.List;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final DtoMapper dtoMapper;

    public ProjectMemberServiceImpl(UserRepository userRepository, ProjectRepository projectRepository, ProjectMemberRepository projectMemberRepository, DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectMemberRepository = projectMemberRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<ProjectMemberGetDto> getAllProjectMembersForProject(Long projectId) {
        List<ProjectMember> projectMembers = projectMemberRepository.findProjectMembersByProject(projectId);

        return dtoMapper.convertProjectMembers(projectMembers);
    }

    @Override
    public ProjectMemberGetDto addProjectMemberToProject(ProjectMemberPostDto projectMemberPostDto, Long projectId) {
        User user = userRepository.findByUsername(projectMemberPostDto.getUsername())
            .orElseThrow(() -> new EntityNotFoundException("username", projectMemberPostDto.getUsername(), User.class.getSimpleName()));
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(projectId, Project.class.getSimpleName()));

        ProjectMember projectMember = new ProjectMember(
            new ProjectMemberKey(user.getId(), projectId),
            user,
            project,
            projectMemberPostDto.getRole()
        );
        ProjectMember savedProjectMember = projectMemberRepository.save(projectMember);

        return dtoMapper.convertProjectMember(savedProjectMember);
    }

    @Override
    public void deleteProjectMemberFromProject(Long memberId, Long projectId) {
        User user = userRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException(memberId, User.class.getSimpleName()));
        Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException(projectId, Project.class.getSimpleName()));

        ProjectMember foundProjectMember = projectMemberRepository
            .findById(new ProjectMemberKey(user.getId(), project.getId()))
            .orElseThrow(() -> new EntityNotFoundException(memberId, User.class.getSimpleName()));

        projectMemberRepository.delete(foundProjectMember);
    }
}
