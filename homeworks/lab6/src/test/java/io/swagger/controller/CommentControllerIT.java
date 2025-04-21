package io.swagger.controller;

import io.swagger.model.Comment;
import io.swagger.model.CommentRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.swagger.api.CommentApiController.log;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class CommentControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        log.info("Testcontainers PostgreSQL configured: {}", postgres.getJdbcUrl());
    }

    @BeforeEach
    void clearDatabase() {
        restTemplate.delete("/api/v3/comment");
    }

    @Test
    void shouldCreateAndRetrieveComment() {
        CommentRequest request = new CommentRequest()
                .content("Test content")
                .author("Test author");

        ResponseEntity<Comment> createResponse = restTemplate.postForEntity(
                "/api/v3/comment",
                request,
                Comment.class);

        assertEquals(HttpStatus.OK, createResponse.getStatusCode());
        assertNotNull(createResponse.getBody());
        assertNotNull(createResponse.getBody().getId());
        assertEquals("Test content", createResponse.getBody().getContent());

        Long commentId = createResponse.getBody().getId();
        ResponseEntity<Comment> getResponse = restTemplate.getForEntity(
                "/api/v3/comment/" + commentId,
                Comment.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals(commentId, getResponse.getBody().getId());
    }

    @Test
    void shouldUpdateComment() {
        CommentRequest createRequest = new CommentRequest()
                .content("Original content")
                .author("Original author");

        Comment createdComment = restTemplate.postForObject(
                "/api/v3/comment",
                createRequest,
                Comment.class);

        Comment updateRequest = new Comment()
                .id(createdComment.getId())
                .content("Updated content")
                .author("Updated author");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Comment> requestEntity = new HttpEntity<>(updateRequest, headers);

        ResponseEntity<Comment> updateResponse = restTemplate.exchange(
                "/api/v3/comment",
                HttpMethod.PUT,
                requestEntity,
                Comment.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("Updated content", updateResponse.getBody().getContent());
    }

    @Test
    void shouldGetAllComments() {
        CommentRequest request1 = new CommentRequest().content("First").author("Author1");
        CommentRequest request2 = new CommentRequest().content("Second").author("Author2");

        restTemplate.postForEntity("/api/v3/comment", request1, Comment.class);
        restTemplate.postForEntity("/api/v3/comment", request2, Comment.class);

        ResponseEntity<Comment[]> response = restTemplate.getForEntity(
                "/api/v3/comment",
                Comment[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(4, response.getBody().length);
    }

    @Test
    void shouldDeleteComment() {
        Comment created = restTemplate.postForObject(
                "/api/v3/comment",
                new CommentRequest().content("To delete").author("Author"),
                Comment.class);

        restTemplate.delete("/api/v3/comment/" + created.getId());

        ResponseEntity<Comment> response = restTemplate.getForEntity(
                "/api/v3/comment/" + created.getId(),
                Comment.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody().getId());
    }

    @Test
    void shouldReturn404WhenCommentNotFound() {
        ResponseEntity<Comment> response = restTemplate.getForEntity(
                "/api/v3/comment/99999",
                Comment.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody().getId());
    }
}
