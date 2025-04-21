package io.swagger.controller;

import io.swagger.model.Comment;
import io.swagger.model.CommentRequest;
import io.swagger.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.swagger.api.CommentApiController.log;

@Slf4j
@RestController
@RequestMapping("/api/v3")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
        log.debug("CommentController initialized with CommentService");
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest commentRequest) {
        log.info("POST /api/v3/comment | Request: {}", commentRequest);
        Comment comment = commentService.createComment(commentRequest);
        log.debug("Created comment with ID: {}", comment.getId());
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/comment")
    public ResponseEntity<Comment> updateComment(@RequestBody Comment comment) {
        log.info("PUT /api/v3/comment | Updating comment ID: {}", comment.getId());
        Comment updatedComment = commentService.updateComment(comment.getId(), comment);
        log.debug("Successfully updated comment ID: {}", comment.getId());
        return ResponseEntity.ok(updatedComment);
    }

    @GetMapping("/comment")
    public ResponseEntity<List<Comment>> getAllComments() {
        log.info("GET /api/v3/comment | Fetching all comments");
        List<Comment> comments = commentService.getAllComments();
        log.debug("Found {} comments", comments.size());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        log.info("GET /api/v3/comment/{} | Fetching comment by ID", commentId);
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            log.warn("Comment with ID {} not found", commentId);
        } else {
            log.debug("Found comment: {}", comment);
        }
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        log.info("DELETE /api/v3/comment/{} | Deleting comment", commentId);
        commentService.deleteComment(commentId);
        log.debug("Comment with ID {} successfully deleted", commentId);
        return ResponseEntity.noContent().build();
    }
}