package ro.ubbcluj.tpjad.jadbackend.services;

import org.springframework.stereotype.Service;
import ro.ubbcluj.tpjad.jadbackend.dtos.DtoMapper;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssueGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssuePostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssuePutDto;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.EntityNotFoundException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.InvalidEntityException;
import ro.ubbcluj.tpjad.jadbackend.models.*;
import ro.ubbcluj.tpjad.jadbackend.repositories.IssueRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.ProjectRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class IssueServiceImpl implements IssueService{
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final DtoMapper dtoMapper;

    public IssueServiceImpl(IssueRepository issueRepository, UserRepository userRepository,
                            ProjectRepository projectRepository, DtoMapper dtoMapper) {
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<IssueGetDto> getAllIssuesForProject(Long projectId) {
        var issues = issueRepository.findIssuesByProject(projectId);
        return dtoMapper.convertIssues(issues);
    }

    @Override
    public IssueGetDto createIssueInProject(IssuePostDto issuePostDto, Long projectId) {
        var project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException(projectId, Project.class.getSimpleName()));

        var username = issuePostDto.getAssignee();
        var assignee = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidEntityException("Invalid assignee provided"));

        IssueStatus issueStatus;
        try {
            issueStatus = IssueStatus.valueOf(issuePostDto.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException("Invalid status provided", e);
        }

        IssueType issueType;
        try {
            issueType = IssueType.valueOf(issuePostDto.getType());
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException("Invalid type provided", e);
        }

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(issuePostDto.getDate(), DtoMapper.formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidEntityException("Invalid date provided", e);
        }

        var issue = new Issue(
                null,
                issuePostDto.getSummary(),
                issuePostDto.getDescription(),
                issueType,
                issueStatus,
                date,
                project,
                assignee
        );

        Issue savedIssue = issueRepository.save(issue);

        return dtoMapper.convertIssue(savedIssue);
    }

    @Override
    public IssueGetDto updateIssueInProject(IssuePutDto issuePutDto, Long projectId, Long issueId) {
        var issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new EntityNotFoundException(issueId, Issue.class.getSimpleName()));

        var username = issuePutDto.getAssignee();
        var assignee = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidEntityException("Invalid assignee provided"));

        IssueStatus issueStatus;
        try {
            issueStatus = IssueStatus.valueOf(issuePutDto.getStatus());
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException("Invalid status provided", e);
        }

        IssueType issueType;
        try {
            issueType = IssueType.valueOf(issuePutDto.getType());
        } catch (IllegalArgumentException e) {
            throw new InvalidEntityException("Invalid type provided", e);
        }

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(issuePutDto.getDate(), DtoMapper.formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidEntityException("Invalid date provided", e);
        }

        issue.setSummary(issuePutDto.getSummary());
        issue.setDescription(issuePutDto.getDescription());
        issue.setType(issueType);
        issue.setStatus(issueStatus);
        issue.setDate(date);
        issue.setAssignee(assignee);

        Issue updatedIssue = issueRepository.save(issue);
        return dtoMapper.convertIssue(updatedIssue);
    }

    @Override
    public void deleteIssueFromProject(Long issueId, Long projectId) {
        issueRepository.findById(issueId)
                .orElseThrow(() -> new EntityNotFoundException(issueId, Issue.class.getSimpleName()));

        issueRepository.deleteByIdCascade(issueId);
    }
}
