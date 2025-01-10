package ro.ubbcluj.tpjad.jadbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssueGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssuePostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.IssuePutDto;
import ro.ubbcluj.tpjad.jadbackend.services.IssueService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class IssueController {
    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("{projectId}/issues")
    public ResponseEntity<List<IssueGetDto>> getAllIssuesForProject(@PathVariable Long projectId) {
        var issues = issueService.getAllIssuesForProject(projectId);

        return new ResponseEntity<>(issues, HttpStatus.OK);
    }

    @PostMapping("{projectId}/issues")
    public ResponseEntity<IssueGetDto> createIssueInProject(@PathVariable Long projectId, @RequestBody IssuePostDto issuePostDto) {
        IssueGetDto createdIssue = issueService.createIssueInProject(issuePostDto, projectId);

        return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
    }

    @PutMapping("{projectId}/issues/{issueId}")
    public ResponseEntity<IssueGetDto> updateIssueInProject(
            @PathVariable Long projectId, @PathVariable Long issueId, @RequestBody IssuePutDto issuePutDto) {
        IssueGetDto updatedIssue = issueService.updateIssueInProject(issuePutDto, projectId, issueId);

        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    @DeleteMapping("{projectId}/issues/{issueId}")
    public ResponseEntity<Void> deleteIssueFromProject(
            @PathVariable Long projectId, @PathVariable Long issueId) {
        issueService.deleteIssueFromProject(issueId, projectId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
