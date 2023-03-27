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

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

   @Autowired
   private DashboardService dashboardService;

   @GetMapping
   public ResponseEntity<DashboardResponseDto> getCashFlow(
         @RequestParam(name = "initialDate") String initialDate,
         @RequestParam(name = "finalDate") String finalDate) {

      return ResponseEntity.ok(dashboardService.getCashFlow(initialDate, finalDate));
   }
}
