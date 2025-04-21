package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;

import jakarta.persistence.*;
import org.springframework.validation.annotation.Validated;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.configuration.NotUndefined;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import jakarta.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "comments")
@Validated
@NotUndefined
@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-25T14:57:37.893068418Z[GMT]")
public class Comment {
  private static Logger log = LoggerFactory.getLogger(Comment.class);
  public Comment() {
    this(LoggerFactory.getLogger(Comment.class));
  }

  // Конструктор для тестов
  public Comment(Logger logger) {
    Comment.log = logger;
  }
  @JsonProperty("id")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSetter(nulls = Nulls.FAIL)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonProperty("content")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSetter(nulls = Nulls.FAIL)
  @NotBlank(message = "Content cannot be blank")
  private String content;

  @JsonProperty("author")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSetter(nulls = Nulls.FAIL)
  private String author;

  @JsonProperty("createdAt")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSetter(nulls = Nulls.FAIL)
  @NotNull
  private OffsetDateTime createdAt;

  @JsonProperty("updatedAt")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSetter(nulls = Nulls.FAIL)
  @NotNull
  private OffsetDateTime updatedAt;

  // Жизненный цикл
  @PostLoad
  public void postLoad() {
    log.debug("Comment loaded: {}", this.toSafeString());
  }

  @PrePersist
  public void prePersist() {
    log.info("Creating new comment: {}", this.toSafeString());
  }

  @PreUpdate
  public void preUpdate() {
    log.debug("Updating comment ID: {}", id);
  }

  public Comment id(Long id) {
    log.debug("Setting comment ID: {}", id);
    this.id = id;
    return this;
  }

  @Schema(example = "10", description = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    log.debug("Changing comment ID: {} → {}", this.id, id);
    this.id = id;
  }

  public Comment content(String content) {
    log.debug("Setting content (length: {})", content != null ? content.length() : 0);
    this.content = content;
    return this;
  }

  @Schema(example = "Good recipe!", description = "")
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    if (content == null || content.isBlank()) {
      log.warn("Attempt to set empty content");
      throw new IllegalArgumentException("Content cannot be blank");
    }
    log.debug("Updating content (length: {} chars)", content.length());
    this.content = content;
  }

  public Comment author(String author) {
    log.debug("Setting author: {}", maskSensitive(author));
    this.author = author;
    return this;
  }

  @Schema(example = "Sam", description = "")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    log.debug("Changing author: {} → {}", maskSensitive(this.author), maskSensitive(author));
    this.author = author;
  }

  public Comment createdAt(OffsetDateTime createdAt) {
    log.debug("Setting creation date: {}", createdAt);
    this.createdAt = createdAt;
    return this;
  }

  @Schema(example = "2025-03-25T15:30Z", description = "")
  @Valid
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    if (createdAt == null) {
      log.error("Attempt to set null creation date");
      throw new IllegalArgumentException("Creation date cannot be null");
    }
    log.debug("Updating creation date: {}", createdAt);
    this.createdAt = createdAt;
  }

  public Comment updatedAt(OffsetDateTime updatedAt) {
    log.debug("Setting update date: {}", updatedAt);
    this.updatedAt = updatedAt;
    return this;
  }

  @Schema(example = "2025-03-25T15:35:11Z", description = "")
  @Valid
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    if (updatedAt == null) {
      log.error("Attempt to set null update date");
      throw new IllegalArgumentException("Update date cannot be null");
    }
    log.debug("Updating modification date: {}", updatedAt);
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Comment comment = (Comment) o;
    return Objects.equals(id, comment.id) &&
            Objects.equals(content, comment.content) &&
            Objects.equals(author, comment.author) &&
            Objects.equals(createdAt, comment.createdAt) &&
            Objects.equals(updatedAt, comment.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content, author, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    return "Comment{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", author='" + maskSensitive(author) + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
  }

  public String toSafeString() {
    return String.format(
            "Comment[id=%d, author=%s, content_length=%d, created=%s]",
            id,
            maskSensitive(author),
            content != null ? content.length() : 0,
            createdAt
    );
  }

  public String maskSensitive(String value) {
    if (value == null) return null;
    if (value.length() <= 2) return value.charAt(0) + "*";
    return value.charAt(0) + "***" + value.charAt(value.length() - 1);
  }
}