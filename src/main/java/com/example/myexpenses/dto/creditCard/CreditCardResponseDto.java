package com.example.myexpenses.dto.creditCard;

import com.example.myexpenses.domain.Enum.Bank;
import com.example.myexpenses.domain.Enum.CardFlag;
import com.example.myexpenses.dto.user.UserResponseDto;

public class CreditCardResponseDto {
   private Long id;

   private String name;

   private Double creditLimit;

   private Double availableLimit;

   private CardFlag flag;

   private Bank bank;

   private int closingDay;

   private int due_date;

   private UserResponseDto user;

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

   public int getDue_date() {
      return due_date;
   }

   public void setDue_date(int due_date) {
      this.due_date = due_date;
   }

   public UserResponseDto getUser() {
      return user;
   }

   public void setUser(UserResponseDto user) {
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

   
}
