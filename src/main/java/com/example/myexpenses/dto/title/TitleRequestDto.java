package com.example.myexpenses.dto.title;

import java.util.Date;

import com.example.myexpenses.domain.enums.Type;
import com.example.myexpenses.domain.model.CreditCardInvoice;
import com.example.myexpenses.dto.costcenter.CostCenterRequestDto;

public class TitleRequestDto {

   private String description;

   private Type type;

   private int installment;

   private Long creditCardId;

   private CostCenterRequestDto costCenter;

   private CreditCardInvoice invoice;

   private Double value;

   private Date createdAt;

   private Date inativeAt;

   private Date referenceDate;

   private String notes;

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

   public CostCenterRequestDto getCostCenter() {
      return costCenter;
   }

   public void setCostCenter(CostCenterRequestDto costCenter) {
      this.costCenter = costCenter;
   }

   public Double getValue() {
      return value;
   }

   public void setValue(Double value) {
      this.value = value;
   }

   public Date getReferenceDate() {
      return referenceDate;
   }

   public void setReferenceDate(Date referenceDate) {
      this.referenceDate = referenceDate;
   }

   public String getNotes() {
      return notes;
   }

   public void setNotes(String notes) {
      this.notes = notes;
   }

   public CreditCardInvoice getInvoice() {
      return invoice;
   }

   public void setInvoice(CreditCardInvoice invoice) {
      this.invoice = invoice;
   }

   public int getInstallment() {
      return installment;
   }

   public void setInstallment(int installment) {
      this.installment = installment;
   }

   public Long getCreditCardId() {
      return creditCardId;
   }

   public void setCreditCardId(Long creditCardId) {
      this.creditCardId = creditCardId;
   }

   public Date getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   public Date getInativeAt() {
      return inativeAt;
   }

   public void setInativeAt(Date inativeAt) {
      this.inativeAt = inativeAt;
   }

}
