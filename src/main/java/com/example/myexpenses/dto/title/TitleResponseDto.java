package com.example.myexpenses.dto.title;

import java.util.Date;

import com.example.myexpenses.domain.Enum.Type;
import com.example.myexpenses.dto.costcenter.CostCenterResponseDto;

public class TitleResponseDto {
   private Long id;

   private String description;

   private Type type;

   private CostCenterResponseDto costCenter;

   private Double value;

   private Date created_at;

   private Date inative_at;

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

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   public CostCenterResponseDto getCostCenter() {
      return costCenter;
   }

   public void setCostCenter(CostCenterResponseDto costCenter) {
      this.costCenter = costCenter;
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

   public Date getInative_at() {
      return inative_at;
   }

   public void setInative_at(Date inative_at) {
      this.inative_at = inative_at;
   }

   
}
