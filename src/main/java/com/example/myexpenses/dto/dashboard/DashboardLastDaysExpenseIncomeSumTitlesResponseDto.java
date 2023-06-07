package com.example.myexpenses.dto.dashboard;

import java.util.List;

public class DashboardLastDaysExpenseIncomeSumTitlesResponseDto {
    
    private List<Double> expenseTitles;

    private List<Double> incomeTitles;

    public DashboardLastDaysExpenseIncomeSumTitlesResponseDto(List<Double> expenseTitles, List<Double> incomeTitles) {
        this.expenseTitles = expenseTitles;
        this.incomeTitles = incomeTitles;
    }

    public List<Double> getExpenseTitles() {
        return expenseTitles;
    }

    public void setExpenseTitles(List<Double> expenseTitles) {
        this.expenseTitles = expenseTitles;
    }

    public List<Double> getIncomeTitles() {
        return incomeTitles;
    }

    public void setIncomeTitles(List<Double> incomeTitles) {
        this.incomeTitles = incomeTitles;
    }

}