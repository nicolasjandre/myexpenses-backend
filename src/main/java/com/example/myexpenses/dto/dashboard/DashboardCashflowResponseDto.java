package com.example.myexpenses.dto.dashboard;

public class DashboardCashflowResponseDto {

   private Double totalExpenses;

   private Double totalIncomes;

   private Double balance;

   public DashboardCashflowResponseDto() {
   }

   public DashboardCashflowResponseDto(Double totalExpenses, Double totalIncomes, Double balance) {
      this.totalExpenses = totalExpenses;
      this.totalIncomes = totalIncomes;
      this.balance = balance;
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
}
