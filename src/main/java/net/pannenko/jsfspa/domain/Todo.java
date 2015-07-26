package net.pannenko.jsfspa.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Todo implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Please provide a note")
  private String notes;

  @NotNull(message = "Please provide another note")
  private String notes2;

  public Todo() {
  }

  public Todo(String notes, String notes2) {
    this.notes = notes;
    this.notes2 = notes2;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getNotes2() {
    return notes2;
  }

  public void setNotes2(String notes2) {
    this.notes2 = notes2;
  }

}
