package ro.ubbcluj.tpjad.jadbackend.services;

import ro.ubbcluj.tpjad.jadbackend.dtos.IssueGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssuePostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssuePutDto;

import java.util.List;

public interface IssueService {
    List<IssueGetDto> getAllIssuesForProject(Long projectId);
    IssueGetDto createIssueInProject(IssuePostDto issue, Long projectId);
    IssueGetDto updateIssueInProject(IssuePutDto issue, Long projectId, Long issueId);
    void deleteIssueFromProject(Long issueId, Long projectId);
}
