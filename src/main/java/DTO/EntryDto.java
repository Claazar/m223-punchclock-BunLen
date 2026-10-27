package DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public class EntryDto {
  @JsonProperty(access = 
    JsonProperty.Access.READ_ONLY)
  @Schema(readOnly = true)
  private Long id;

  @NotNull(message = "Check-In is required")
  private LocalDateTime checkIn;

  @NotNull(message = "Check-Out is required")
  private LocalDateTime checkOut;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCheckIn() {
    return checkIn;
  }

  public void setCheckIn(LocalDateTime checkIn) {
    this.checkIn = checkIn;
  }

  public LocalDateTime getCheckOut() {
    return checkOut;
  }

  public void setCheckOut(LocalDateTime checkOut) {
    this.checkOut = checkOut;
  }

  @JsonIgnore
  @AssertTrue(message = "Check-In must be before Check-Out")
  public boolean isCheckInBeforeCheckOut() {
    return checkIn == null || checkOut == null || checkIn.isBefore(checkOut);
  }
}