package ro.ubbcluj.tpjad.jadbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentPutDto;
import ro.ubbcluj.tpjad.jadbackend.services.CommentService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/issues/{issueId}/comments")
@CrossOrigin
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentGetDto>> getAllCommentsForIssue(@PathVariable Long issueId) {
        List<CommentGetDto> comments = commentService.getAllCommentsForIssue(issueId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentGetDto> createCommentForIssue(
        @RequestBody CommentPostDto commentPostDto, @PathVariable Long issueId, Principal principal
    ) {
        String username = principal.getName();

        CommentGetDto createdComment = commentService.createCommentForIssue(commentPostDto, issueId, username);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentGetDto> updateCommentForIssue(
        @PathVariable Long commentId, @PathVariable Long issueId, @RequestBody CommentPutDto commentPutDto
    ) {
        CommentGetDto updatedComment = commentService.updateCommentForIssue(commentPutDto, commentId, issueId);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteCommentFromIssue(@PathVariable Long commentId, @PathVariable Long issueId) {
        commentService.deleteCommentFromIssue(commentId, issueId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
