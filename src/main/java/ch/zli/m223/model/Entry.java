package ch.zli.m223.model;

import jakarta.persistence.*;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity // Marks class as DB entity
public class Entry { // Entry is the TABLE NAME in DB
  @Id // makes the following variable the primary key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment, 1, 2, 3, ...
  @Schema(readOnly = true)
  private Long id;

  @Column(nullable = false) // NOT NULL
  private LocalDateTime checkIn;

  @Column(nullable = false)
  private LocalDateTime checkOut;

  @Column(nullable = true)
  private String description;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "application_user_id")
  private ApplicationUser applicationUser;

  @ManyToMany
  @JoinTable(
    name = "entry_tag",
    joinColumns = @JoinColumn(name = "entry_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
  )
  private Set<Tag> tags = new LinkedHashSet<>();

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

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public ApplicationUser getApplicationUser() {
    return applicationUser;
  }

  public void setApplicationUser(ApplicationUser applicationUser) {
    this.applicationUser = applicationUser;
  }

  public Set<Tag> getTags() {
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}