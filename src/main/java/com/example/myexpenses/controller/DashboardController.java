package com.example.myexpenses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.services.DashboardService;
import com.example.myexpenses.dto.dashboard.DashboardResponseDto;

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
   public ResponseEntity<DashboardResponseDto> getCashFlow(
         @RequestParam(name = "initialDate") String initialDate,
         @RequestParam(name = "finalDate") String finalDate) {

      DashboardResponseDto responseDto = dashboardService.getCashFlow(initialDate, finalDate);

      return ResponseEntity.ok(responseDto);
   }
}
