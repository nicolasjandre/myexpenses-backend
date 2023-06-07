package com.example.myexpenses.domain.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.enums.Type;
import com.example.myexpenses.dto.dashboard.DashboardCashflowResponseDto;
import com.example.myexpenses.dto.dashboard.DashboardLastDaysExpenseIncomeSumTitlesResponseDto;
import com.example.myexpenses.dto.dashboard.DashboardLastDaysExpenseIncomesByCostCenter;
import com.example.myexpenses.dto.title.TitleResponseDto;
import com.example.myexpenses.vo.TotalByCostCenterVo;

@Service
public class DashboardService {

    @Autowired
    private TitleService titleService;

    public DashboardCashflowResponseDto getCashFlow(String initialDate, String finalDate) {

        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sqlDate1 = new Date();
        Date sqlDate2 = new Date();
        try {
            java.util.Date utilDate1 = inputFormatter.parse(initialDate);
            java.util.Date utilDate2 = inputFormatter.parse(finalDate);

            sqlDate1 = new Date(utilDate1.getTime());
            sqlDate2 = new Date(utilDate2.getTime());

        } catch (ParseException e) {
            System.out.println("Failed to parse date: " + e.getMessage());
        }

        List<TitleResponseDto> titles = titleService.getCashFlowByDueDate(sqlDate1, sqlDate2);

        Double totalExpenses = 0.0;
        Double totalIncomes = 0.0;
        Double balance;

        for (TitleResponseDto title : titles) {

            if (title.getType() == Type.EXPENSE) {
                totalExpenses += title.getValue();
            } else {
                totalIncomes += title.getValue();
            }
        }

        balance = totalIncomes - totalExpenses;

        return new DashboardCashflowResponseDto(totalExpenses, totalIncomes, balance);
    }

    public DashboardLastDaysExpenseIncomeSumTitlesResponseDto getLastDaysTitles(Long days) {
        List<TitleResponseDto> titles = titleService.getLastDaysTitles(days);

        return getLastXDaysSum(titles, days);
    }

    public DashboardLastDaysExpenseIncomesByCostCenter getLastDaysTotalByCostCenter(Long days) {
        List<TitleResponseDto> titles = titleService.getLastDaysTitles(days);

        List<TotalByCostCenterVo> totalExpensesByCostCenter = new ArrayList<>();
        List<TotalByCostCenterVo> totalIncomesByCostCenter = new ArrayList<>();

        for (TitleResponseDto title : titles) {
            Long costCenterId = title.getCostCenter().getId();
            String costCenterDesc = title.getCostCenter().getDescription();

            if (title.getType() == Type.EXPENSE) {
                boolean alreadyExists = false;
                TotalByCostCenterVo expense = new TotalByCostCenterVo();

                for (TotalByCostCenterVo expenses : totalExpensesByCostCenter) {
                    if (title.getCostCenter().getId().equals(expenses.getId())) {
                        expense = expenses;
                        alreadyExists = true;
                        break;
                    }
                }

                if (alreadyExists) {
                    expense.setTotal(expense.getTotal() + title.getValue());
                } else {
                    totalExpensesByCostCenter
                            .add(new TotalByCostCenterVo(costCenterId, costCenterDesc, title.getValue()));
                }

            } else {
                boolean alreadyExists = false;
                TotalByCostCenterVo income = new TotalByCostCenterVo();

                for (TotalByCostCenterVo incomes : totalIncomesByCostCenter) {
                    if (title.getCostCenter().getId().equals(incomes.getId())) {
                        income = incomes;
                        alreadyExists = true;
                        break;
                    }
                }

                if (alreadyExists) {
                    income.setTotal(income.getTotal() + title.getValue());
                } else {
                    totalIncomesByCostCenter
                            .add(new TotalByCostCenterVo(costCenterId, costCenterDesc, title.getValue()));
                }
            }
        }

        return new DashboardLastDaysExpenseIncomesByCostCenter(totalExpensesByCostCenter, totalIncomesByCostCenter);
    }

    private DashboardLastDaysExpenseIncomeSumTitlesResponseDto getLastXDaysSum(List<TitleResponseDto> titles,
            Long days) {
        LocalDate today = LocalDate.now();
        LocalDate lastXDays = today.minusDays(days + 1);

        List<Double> lastXDaysExpenses = new ArrayList<>();
        List<Double> lastXDaysIncomes = new ArrayList<>();

        for (int i = 0; i <= days; i++) {

            LocalDate day = lastXDays.plusDays(i + 1L);

            Double expenseSum = 0.0;
            Double incomeSum = 0.0;

            for (TitleResponseDto title : titles) {
                LocalDate titleDate = convertToLocalDate(title.getReferenceDate());
                if (titleDate.isEqual(day) && title.getType() == Type.EXPENSE) {
                    expenseSum += title.getValue();
                } else if (titleDate.isEqual(day) && title.getType() == Type.INCOME) {
                    incomeSum += title.getValue();
                }
            }

            lastXDaysExpenses.add(expenseSum);
            lastXDaysIncomes.add(incomeSum);
        }

        Collections.reverse(lastXDaysExpenses); // reversing the list to match the front end chart, from oldest date to
                                                // today
        Collections.reverse(lastXDaysIncomes);

        return new DashboardLastDaysExpenseIncomeSumTitlesResponseDto(lastXDaysExpenses, lastXDaysIncomes);
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}