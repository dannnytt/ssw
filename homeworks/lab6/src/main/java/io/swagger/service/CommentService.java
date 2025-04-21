package io.swagger.service;

import io.swagger.model.Comment;
import io.swagger.model.CommentRequest;
import io.swagger.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

import static io.swagger.api.CommentApiController.log;

@Slf4j
@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
        log.info("CommentService initialized with CommentRepository");
    }

    public Comment createComment(CommentRequest commentRequest) {
        log.info("Creating new comment. Content length: {}, Author: {}",
                commentRequest.getContent().length(),
                commentRequest.getAuthor() != null ? "provided" : "anonymous");

        Comment comment = new Comment()
                .content(commentRequest.getContent())
                .author(commentRequest.getAuthor())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        log.debug("Created comment with ID: {}", savedComment.getId());

        return savedComment;
    }

    public Comment updateComment(Long id, Comment commentDetails) {
        log.info("Updating comment ID: {}", id);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Comment not found for update. ID: {}", id);
                    return new RuntimeException("Comment not found with id: " + id);
                });

        log.debug("Original content: '{}', New content: '{}'",
                comment.getContent(),
                commentDetails.getContent());

        comment.setContent(commentDetails.getContent());
        comment.setAuthor(commentDetails.getAuthor());
        comment.setUpdatedAt(OffsetDateTime.now());

        Comment updatedComment = commentRepository.save(comment);
        log.info("Successfully updated comment ID: {}", id);

        return updatedComment;
    }

    public List<Comment> getAllComments() {
        log.debug("Fetching all comments");
        List<Comment> comments = commentRepository.findAll();
        log.info("Retrieved {} comments", comments.size());
        return comments;
    }

    public Comment getCommentById(Long id) {
        log.debug("Fetching comment by ID: {}", id);
        return commentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Comment not found. ID: {}", id);
                    return new RuntimeException("Comment not found with id: " + id);
                });
    }

    public void deleteComment(Long id) {
        log.info("Deleting comment ID: {}", id);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Comment not found for deletion. ID: {}", id);
                    return new RuntimeException("Comment not found with id: " + id);
                });

        commentRepository.delete(comment);
        log.info("Successfully deleted comment ID: {}", id);
    }
}