package com.example.myexpenses.dto.costcenter;

import java.util.Date;

public class CostCenterRequestDto {
  
  private Long id;

  private String description;

  private String notes;

  private Date inative_at;

  public Date getInative_at() {
    return inative_at;
  }

  public void setInative_at(Date inative_at) {
    this.inative_at = inative_at;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
