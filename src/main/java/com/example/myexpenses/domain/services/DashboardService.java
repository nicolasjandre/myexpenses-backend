package com.example.myexpenses.domain.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.Enum.Type;
import com.example.myexpenses.dto.dashboard.DashboardResponseDto;
import com.example.myexpenses.dto.title.TitleResponseDto;

@Service
public class DashboardService {

   @Autowired
   private TitleService titleService;

   public DashboardResponseDto getCashFlow(String initialDate, String finalDate) {

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
      Double balance = 0.0;
      List<TitleResponseDto> expenseTitles = new ArrayList<>();
      List<TitleResponseDto> incomeTitles = new ArrayList<>();

      for (TitleResponseDto title : titles) {

         if (title.getType() == Type.EXPENSE) {
            totalExpenses += title.getValue();
            expenseTitles.add(title);
         } else {
            totalIncomes += title.getValue();
            incomeTitles.add(title);
         }
      }

      Collections.sort(expenseTitles, Comparator.comparing(TitleResponseDto::getReferenceDate));
      Collections.sort(incomeTitles, Comparator.comparing(TitleResponseDto::getReferenceDate));

      balance = totalIncomes - totalExpenses;

      DashboardResponseDto dashboardResponseDto = new DashboardResponseDto(totalExpenses, totalIncomes, balance,
            expenseTitles, incomeTitles);

      return dashboardResponseDto;
   }
}