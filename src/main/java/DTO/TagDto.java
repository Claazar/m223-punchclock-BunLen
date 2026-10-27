package DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

public class TagDto {
  @JsonProperty(access = 
    JsonProperty.Access.READ_ONLY)
  @Schema(readOnly = true)
  private Long id;

  @NotNull(message = "Title is required")
  private String title;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}