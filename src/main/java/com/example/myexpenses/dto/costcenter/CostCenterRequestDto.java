package com.example.myexpenses.dto.costcenter;

import java.util.Date;

import com.example.myexpenses.domain.Enum.Type;

public class CostCenterRequestDto {

  private Long id;

  private String description;

  private boolean standard;

  private Type type;

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

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public boolean isStandard() {
    return standard;
  }

  public void setStandard(boolean standard) {
    this.standard = standard;
  }
}
