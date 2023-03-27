package com.example.myexpenses.dto.dashboard;

import java.util.List;

import com.example.myexpenses.dto.title.TitleResponseDto;

public class DashboardResponseDto {

   private Double totalExpenses;

   private Double totalIncomes;

   private Double balance;

   private List<TitleResponseDto> titlesToPay;

   private List<TitleResponseDto> titlesToReceive;

   public DashboardResponseDto() {
   }

   public DashboardResponseDto(Double totalExpenses, Double totalIncomes, Double balance,
         List<TitleResponseDto> titlesToPay, List<TitleResponseDto> titlesToReceive) {
      this.totalExpenses = totalExpenses;
      this.totalIncomes = totalIncomes;
      this.balance = balance;
      this.titlesToPay = titlesToPay;
      this.titlesToReceive = titlesToReceive;
   }

   public Double getTotalExpenses() {
      return totalExpenses;
   }

   public void setTotalExpenses(Double totalExpenses) {
      this.totalExpenses = totalExpenses;
   }

   public Double getTotalIncomes() {
      return totalIncomes;
   }

   public void setTotalIncomes(Double totalIncomes) {
      this.totalIncomes = totalIncomes;
   }

   public Double getBalance() {
      return balance;
   }

   public void setBalance(Double balance) {
      this.balance = balance;
   }

   public List<TitleResponseDto> getTitlesToPay() {
      return titlesToPay;
   }

   public void setTitlesToPay(List<TitleResponseDto> titlesToPay) {
      this.titlesToPay = titlesToPay;
   }

   public List<TitleResponseDto> getTitlesToReceive() {
      return titlesToReceive;
   }

   public void setTitlesToReceive(List<TitleResponseDto> titlesToReceive) {
      this.titlesToReceive = titlesToReceive;
   }

}
