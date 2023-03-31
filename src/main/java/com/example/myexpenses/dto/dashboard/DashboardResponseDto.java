package com.example.myexpenses.dto.dashboard;

import java.util.List;

import com.example.myexpenses.dto.title.TitleResponseDto;

public class DashboardResponseDto {

   private Double totalExpenses;

   private Double totalIncomes;

   private Double balance;

   private List<TitleResponseDto> expenseTitles;

   private List<TitleResponseDto> incomeTitles;

   public DashboardResponseDto() {
   }

   public DashboardResponseDto(Double totalExpenses, Double totalIncomes, Double balance,
         List<TitleResponseDto> expenseTitles, List<TitleResponseDto> incomeTitles) {
      this.totalExpenses = totalExpenses;
      this.totalIncomes = totalIncomes;
      this.balance = balance;
      this.expenseTitles = expenseTitles;
      this.incomeTitles = incomeTitles;
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

   public List<TitleResponseDto> getExpenseTitles() {
      return expenseTitles;
   }

   public void setExpenseTitles(List<TitleResponseDto> expenseTitles) {
      this.expenseTitles = expenseTitles;
   }

   public List<TitleResponseDto> getIncomeTitles() {
      return incomeTitles;
   }

   public void setIncomeTitles(List<TitleResponseDto> incomeTitles) {
      this.incomeTitles = incomeTitles;
   }

}
