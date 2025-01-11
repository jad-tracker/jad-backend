package ro.ubbcluj.tpjad.jadbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.CommentPutDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.DtoMapper;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.EntityNotFoundException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.InvalidEntityException;
import ro.ubbcluj.tpjad.jadbackend.models.Comment;
import ro.ubbcluj.tpjad.jadbackend.models.Issue;
import ro.ubbcluj.tpjad.jadbackend.models.User;
import ro.ubbcluj.tpjad.jadbackend.repositories.CommentRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.IssueRepository;
import ro.ubbcluj.tpjad.jadbackend.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;
    private final CommentRepository commentRepository;
    private final DtoMapper dtoMapper;

    @Autowired
    public CommentServiceImpl(UserRepository userRepository, IssueRepository issueRepository, CommentRepository commentRepository, DtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.commentRepository = commentRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<CommentGetDto> getAllCommentsForIssue(Long issueId) {
        List<Comment> comments = commentRepository.findCommentsForIssue(issueId);

        return dtoMapper.convertComments(comments);
    }

    @Override
    public CommentGetDto createCommentForIssue(CommentPostDto commentPostDto, Long issueId, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException("username", username, User.class.getSimpleName()));
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new EntityNotFoundException(issueId, Issue.class.getSimpleName()));

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(commentPostDto.getDate(), DtoMapper.formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidEntityException("Invalid date provided");
        }

        Comment comment = new Comment(
            null,
            commentPostDto.getContent(),
            date,
            user,
            issue
        );
        Comment savedComment = commentRepository.save(comment);

        return dtoMapper.convertComment(savedComment);
    }

    @Override
    public CommentGetDto updateCommentForIssue(CommentPutDto commentPutDto, Long commentId, Long issueId) {
        issueRepository.findById(issueId)
            .orElseThrow(() -> new EntityNotFoundException(issueId, Issue.class.getSimpleName()));

        LocalDateTime date;
        try {
            date = LocalDateTime.parse(commentPutDto.getDate(), DtoMapper.formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidEntityException("Invalid date provided");
        }

        Comment foundComment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException(commentId, Comment.class.getSimpleName()));

        foundComment.setContent(commentPutDto.getContent());
        foundComment.setDate(date);
        Comment updatedComment = commentRepository.save(foundComment);

        return dtoMapper.convertComment(updatedComment);
    }

    @Override
    public void deleteCommentFromIssue(Long commentId, Long issueId) {
        issueRepository.findById(issueId)
            .orElseThrow(() -> new EntityNotFoundException(issueId, Issue.class.getSimpleName()));
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException(commentId, Comment.class.getSimpleName()));

        commentRepository.delete(comment);
    }
}
