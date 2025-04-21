package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.configuration.NotUndefined;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

/**
 * CommentRequest
 */
@Validated
@NotUndefined
@jakarta.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-25T14:57:37.893068418Z[GMT]")


public class CommentRequest   {
  @JsonProperty("content")

  private String content = null;

  @JsonProperty("author")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String author = null;


  public CommentRequest content(String content) { 

    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   **/
  
  @Schema(example = "Good recipe!", required = true, description = "")
  
  @NotNull
  public String getContent() {  
    return content;
  }



  public void setContent(String content) { 

    this.content = content;
  }

  public CommentRequest author(String author) { 

    this.author = author;
    return this;
  }

  /**
   * Get author
   * @return author
   **/
  
  @Schema(example = "Sam", description = "")
  
  public String getAuthor() {  
    return author;
  }



  public void setAuthor(String author) { 
    this.author = author;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CommentRequest commentRequest = (CommentRequest) o;
    return Objects.equals(this.content, commentRequest.content) &&
        Objects.equals(this.author, commentRequest.author);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, author);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CommentRequest {\n");
    
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  public String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
