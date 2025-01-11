package ro.ubbcluj.tpjad.jadbackend.services;

import ro.ubbcluj.tpjad.jadbackend.dtos.CommentGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentPutDto;

import java.util.List;

public interface CommentService {
    List<CommentGetDto> getAllCommentsForIssue(Long issueId);
    CommentGetDto createCommentForIssue(CommentPostDto commentPostDto, Long issueId, String username);
    CommentGetDto updateCommentForIssue(CommentPutDto commentPutDto, Long commentId, Long issueId);
    void deleteCommentFromIssue(Long commentId, Long issueId);
}
