package com.example.myexpenses.dto.creditcardinvoice;

import java.time.LocalDate;
import java.util.List;

import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.Title;

public class CreditCardInvoiceRequestDto {

   private Long id;

   private CreditCard creditCard;

   private List<Title> titles;

   private LocalDate closingDate;
  
   private LocalDate dueDate;

   private boolean isPaid;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public CreditCard getCreditCard() {
      return creditCard;
   }

   public void setCreditCard(CreditCard creditCard) {
      this.creditCard = creditCard;
   }

   public List<Title> getTitles() {
      return titles;
   }

   public void setTitles(List<Title> titles) {
      this.titles = titles;
   }

   public LocalDate getClosingDate() {
      return closingDate;
   }

   public void setClosingDate(LocalDate closingDate) {
      this.closingDate = closingDate;
   }

   public LocalDate getDueDate() {
      return dueDate;
   }

   public void setDueDate(LocalDate dueDate) {
      this.dueDate = dueDate;
   }

   public boolean isPaid() {
      return isPaid;
   }

   public void setPaid(boolean isPaid) {
      this.isPaid = isPaid;
   }

   
}
