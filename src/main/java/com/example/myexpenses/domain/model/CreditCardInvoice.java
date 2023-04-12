package com.example.myexpenses.domain.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class CreditCardInvoice {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "invoice_id")
   private Long id;

   @ManyToOne
   @JsonBackReference(value = "oneCreditCardToManyInvoices")
   @JoinColumn(name = "creditcard_id")
   private CreditCard creditCard;

   @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
   @JsonManagedReference(value = "oneInvoiceToManyTitles")
   private List<Title> titles;

   @Column(nullable = false)
   private LocalDate closingDate;

   @Column(nullable = false)
   private LocalDate dueDate;

   @Column(nullable = false)
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

   public LocalDate getClosingDate() {
      return closingDate;
   }

   public void setClosingDate(LocalDate closingDate) {
      this.closingDate = closingDate;
   }

   public List<Title> getTitles() {
      return titles;
   }

   public void setTitles(List<Title> titles) {
      this.titles = titles;
   }

}
