package com.example.myexpenses.dto.title;

import java.util.Date;
import java.util.List;

import com.example.myexpenses.domain.Enum.TitleType;
import com.example.myexpenses.dto.costcenter.CostCenterRequestDto;

public class TitleRequestDto {
   private Long id;

   private String description;

   private TitleType type;

   private List<CostCenterRequestDto> costCenters;

   private Double value;

   private Date created_at;

   private Date referenceDate;

   private Date dueDate;

   private Date payDate;

   private String notes;

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

   public TitleType getType() {
      return type;
   }

   public void setType(TitleType type) {
      this.type = type;
   }

   public List<CostCenterRequestDto> getCostCenters() {
      return costCenters;
   }

   public void setCostCenters(List<CostCenterRequestDto> costCenters) {
      this.costCenters = costCenters;
   }

   public Double getValue() {
      return value;
   }

   public void setValue(Double value) {
      this.value = value;
   }

   public Date getCreated_at() {
      return created_at;
   }

   public void setCreated_at(Date created_at) {
      this.created_at = created_at;
   }

   public Date getReferenceDate() {
      return referenceDate;
   }

   public void setReferenceDate(Date referenceDate) {
      this.referenceDate = referenceDate;
   }

   public Date getDueDate() {
      return dueDate;
   }

   public void setDueDate(Date dueDate) {
      this.dueDate = dueDate;
   }

   public Date getPayDate() {
      return payDate;
   }

   public void setPayDate(Date payDate) {
      this.payDate = payDate;
   }

   public String getNotes() {
      return notes;
   }

   public void setNotes(String notes) {
      this.notes = notes;
   }

}
