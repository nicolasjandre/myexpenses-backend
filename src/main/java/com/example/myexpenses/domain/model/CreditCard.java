package com.example.myexpenses.domain.model;

import java.util.List;

import com.example.myexpenses.domain.Enum.Bank;
import com.example.myexpenses.domain.Enum.CardFlag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class CreditCard {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "creditcard_id")
   private Long id;

   @Column(nullable = false)
   private String name;

   @Column(nullable = false)
   private Double creditLimit;

   @Column
   private Double availableLimit;

   @Column(nullable = false)
   private CardFlag flag;

   @Column(nullable = false)
   private Bank bank;

   @Column(nullable = false)
   private int closing_day;

   @Column(nullable = false)
   private int due_date;

   @OneToMany(mappedBy = "creditCard")
   @JsonManagedReference(value = "oneCreditCardToManyInvoices")
   private List<CreditCardInvoice> invoices;
   
   @ManyToOne
   @JsonBackReference(value = "oneUserToManyCreditCards")
   @JoinColumn(name = "user_id")
   private User user;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public CardFlag getFlag() {
      return flag;
   }

   public void setFlag(CardFlag flag) {
      this.flag = flag;
   }

   public Bank getBank() {
      return bank;
   }

   public void setBank(Bank bank) {
      this.bank = bank;
   }

   public int getClosing_day() {
      return closing_day;
   }

   public void setClosing_day(int closing_day) {
      this.closing_day = closing_day;
   }

   public int getDue_date() {
      return due_date;
   }

   public void setDue_date(int due_date) {
      this.due_date = due_date;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public List<CreditCardInvoice> getInvoices() {
      return invoices;
   }

   public void setInvoices(List<CreditCardInvoice> invoices) {
      this.invoices = invoices;
   }

   public Double getCreditLimit() {
      return creditLimit;
   }

   public void setCreditLimit(Double creditLimit) {
      this.creditLimit = creditLimit;
   }

   public Double getAvailableLimit() {
      return availableLimit;
   }

   public void setAvailableLimit(Double availableLimit) {
      this.availableLimit = availableLimit;
   }
}
