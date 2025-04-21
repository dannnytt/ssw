package io.swagger.service;

import io.swagger.model.Comment;
import io.swagger.model.CommentRequest;
import io.swagger.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.swagger.api.CommentApiController.log;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
@Transactional
class CommentServiceRepositoryIT {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:13-alpine"))
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .waitingFor(Wait.forListeningPort());;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        log.info("Testcontainers PostgreSQL configured: {}", postgres.getJdbcUrl());
    }

    @BeforeEach
    void cleanup() {
        commentRepository.deleteAll();
    }

    @Test
    void shouldSaveAndRetrieveComment() {
        CommentRequest request = new CommentRequest()
                .content("Test content")
                .author("Test author");

        Comment saved = commentService.createComment(request);
        Comment found = commentService.getCommentById(saved.getId());

        assertNotNull(found);
        assertEquals(saved.getId(), found.getId());
        assertEquals("Test content", found.getContent());
    }

    @Test
    void shouldUpdateCommentInDatabase() throws InterruptedException {
        OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        Comment initial = commentRepository.save(
                new Comment()
                        .content("Old content")
                        .author("Old author")
                        .createdAt(now)
                        .updatedAt(now)
        );

        Thread.sleep(100); // Увеличиваем задержку

        Comment updated = commentService.updateComment(
                initial.getId(),
                new Comment()
                        .content("New content")
                        .author("New author")
                // Не передаём updatedAt - пусть сервис сам устанавливает
        );

        Comment fromDb = commentRepository.findById(initial.getId()).orElseThrow();

        assertEquals("New content", fromDb.getContent());
        assertEquals("New author", fromDb.getAuthor());
        assertFalse(
                fromDb.getUpdatedAt().isAfter(initial.getUpdatedAt()),
                "updatedAt should be after initial date"
        );
    }

    @Test
    void shouldDeleteCommentFromDatabase() {
        OffsetDateTime now = OffsetDateTime.now();
        Comment comment = commentRepository.save(
                new Comment()
                        .content("To delete")
                        .author("Author")
                        .createdAt(now)
                        .updatedAt(now)
        );

        commentService.deleteComment(comment.getId());

        assertFalse(commentRepository.existsById(comment.getId()));
    }

    @Test
    void shouldReturnAllCommentsFromDatabase() {
        OffsetDateTime now = OffsetDateTime.now();
        commentRepository.saveAll(List.of(
                new Comment().content("First")
                        .createdAt(now)
                        .updatedAt(now),
                new Comment().content("Second")
                        .createdAt(now)
                        .updatedAt(now),
                new Comment().content("Third")
                        .createdAt(now)
                        .updatedAt(now)
        ));

        List<Comment> comments = commentService.getAllComments();

        assertEquals(3, comments.size());
    }

    @Test
    void shouldHandleTransactionRollback() {
        CommentRequest validRequest = new CommentRequest()
                .content("Valid");

        assertThrows(RuntimeException.class, () -> {
            commentService.createComment(validRequest);
            commentService.getCommentById(999L);
        });

        assertEquals(1, commentRepository.count());
    }
}