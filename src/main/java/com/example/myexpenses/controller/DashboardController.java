package com.example.myexpenses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.services.DashboardService;
import com.example.myexpenses.dto.dashboard.DashboardCashflowResponseDto;
import com.example.myexpenses.dto.dashboard.DashboardLastDaysExpenseIncomeSumTitlesResponseDto;
import com.example.myexpenses.dto.dashboard.DashboardLastDaysExpenseIncomesByCostCenter;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

   @Autowired
   private DashboardService dashboardService;

   @GetMapping
   @Operation(summary = "Get the user who's requesting cash flow filtering by date, check description for details.", description = """
         <h2>Parameters example:</h2>
         Param1: "2023-10-10 00:00:00"<br>
         Param2: "2023-10-17 23:59:59.999"<br>
         In this example, the response will be the cashflow between those dates.<br>
         """)
   public ResponseEntity<DashboardCashflowResponseDto> getCashFlow(
         @RequestParam(name = "initialDate") String initialDate,
         @RequestParam(name = "finalDate") String finalDate) {

      DashboardCashflowResponseDto responseDto = dashboardService.getCashFlow(initialDate, finalDate);

      return ResponseEntity.ok(responseDto);
   }

   @GetMapping("/lastdays")
   @Operation(summary = "Get the user who's requesting the last X days expenses and incomes")
   public ResponseEntity<DashboardLastDaysExpenseIncomeSumTitlesResponseDto> getLastDaysTitles(
         @RequestParam(name = "days") Long days) {
      return ResponseEntity.status(HttpStatus.OK).body(dashboardService.getLastDaysTitles(days));
   }

   @GetMapping("/costcenter")
   @Operation(summary = "Get the user who's requesting the last X days total cost center")
   public ResponseEntity<DashboardLastDaysExpenseIncomesByCostCenter> getLastgetLastDaysTotalByCostCenterDaysTitles(
         @RequestParam(name = "days") Long days) {
      return ResponseEntity.status(HttpStatus.OK).body(dashboardService.getLastDaysTotalByCostCenter(days));
   }
}
