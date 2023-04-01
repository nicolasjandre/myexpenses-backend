package com.example.myexpenses.domain.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.Enum.TitleType;
import com.example.myexpenses.dto.dashboard.DashboardResponseDto;
import com.example.myexpenses.dto.title.TitleResponseDto;

@Service
public class DashboardService {

   @Autowired
   private TitleService titleService;

   public DashboardResponseDto getCashFlow(String initialDate, String finalDate) {

      List<TitleResponseDto> titles = titleService.getCashFlowByDueDate(initialDate, finalDate);

      Double totalExpenses = 0.0;
      Double totalIncomes = 0.0;
      Double balance = 0.0;
      List<TitleResponseDto> expenseTitles = new ArrayList<>();
      List<TitleResponseDto> incomeTitles = new ArrayList<>();

      for (TitleResponseDto title : titles) {

         if (title.getType() == TitleType.EXPENSE) {
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

      return new DashboardResponseDto(totalExpenses, totalIncomes, balance, expenseTitles, incomeTitles);
   }
}
