package com.example.myexpenses.dto.creditcard;

import com.example.myexpenses.domain.enums.Bank;
import com.example.myexpenses.domain.enums.CardFlag;
import com.example.myexpenses.domain.model.User;

public class CreditCardRequestDto {

   private Long id;

   private String name;

   private Double creditLimit;

   private Double availableLimit;

   private CardFlag flag;

   private Bank bank;

   private int closingDay;

   private int dueDay;

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

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   public Double getAvailableLimit() {
      return availableLimit;
   }

   public void setAvailableLimit(Double availableLimit) {
      this.availableLimit = availableLimit;
   }

   public Double getCreditLimit() {
      return creditLimit;
   }

   public void setCreditLimit(Double creditLimit) {
      this.creditLimit = creditLimit;
   }

   public int getClosingDay() {
      return closingDay;
   }

   public void setClosingDay(int closingDay) {
      this.closingDay = closingDay;
   }

   public int getDueDay() {
      return dueDay;
   }

   public void setDueDay(int dueDay) {
      this.dueDay = dueDay;
   }
}
