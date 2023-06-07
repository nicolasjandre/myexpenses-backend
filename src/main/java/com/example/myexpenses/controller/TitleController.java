package com.example.myexpenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.services.TitleService;
import com.example.myexpenses.dto.title.TitleRequestDto;
import com.example.myexpenses.dto.title.TitleResponseDto;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/titles")
public class TitleController implements ICRUDController<TitleRequestDto, TitleResponseDto> {

   @Autowired
   private TitleService titleService;

   @Override
   @Operation(summary = "Get all titles that were created by the user who is requesting.")
   public ResponseEntity<List<TitleResponseDto>> getAll() {

      return ResponseEntity.ok(titleService.getAll());
   }

   @Override
   @Operation(summary = "Get a specific title by its ID.")
   public ResponseEntity<TitleResponseDto> getById(Long id) {

      return ResponseEntity.ok(titleService.getById(id));
   }

   @Override
   @Operation(summary = "Create a new title for the user who is requesting. Check description to see the details.", description = """
         <h2>Important: </h2>
         The creditCardId should be 0 when creating a Wallet title. <br>
         The number of installment is REQUIRED when creating a credit card expense. <br>
         The number of installment is not required when creating a WALLET expense. <br>

         <br>
         <br>

         <h2>Request body INCOME:</h2>
         <code>
         {<br>
            "description": "Income Example",<br>
            "type": "INCOME",<br>
            "value": 54.97,<br>
            "referenceDate": "2023-04-11T19:35:36",<br>
            "creditCardId": 0, << Credit card ID should be 0 when creating incomes or wallet expenses<br>
            "costCenter": { "id": 1 }<br>
         }
         </code>

         <h2>Request body for wallet expense:</h2>
         <code>
         {<br>
            "description": "Expense Example",<br>
            "type": "EXPENSE",<br>
            "value": 54.97,<br>
            "referenceDate": "2023-04-11T19:35:36",<br>
            "creditCardId": 0, << Credit card ID should be 0 when creating incomes or wallet expenses <br>
            "costCenter": { "id": 1 }<br>
         }
         </code>

         <h2>Request body for CREDIT CARD expense:</h2>
         <code>
         {<br>
            "description": "Expense Example",<br>
            "type": "EXPENSE",<br>
            "value": 54.97,<br>
            "referenceDate": "2023-04-11T19:35:36",<br>
            "installment": "12" << max of 99 <br>
            "creditCardId": 1, << Credit card ID <br>
            "costCenter": { "id": 1 }<br>
         }
         </code>
            """)
   public ResponseEntity<List<TitleResponseDto>> create(TitleRequestDto dto) {

      List<TitleResponseDto> titleDto = titleService.create(dto);

      return new ResponseEntity<>(titleDto, HttpStatus.CREATED);
   }

   @Override
   @Operation(summary = "Updates a specific title.")
   public ResponseEntity<TitleResponseDto> update(Long id, TitleRequestDto dto) {
      return ResponseEntity.ok(titleService.update(id, dto));
   }

   @Override
   @Operation(summary = "Delete a specific title. (Doesn't remove it from the database, just marks it with an inactive date)")
   public ResponseEntity<Void> delete(Long id) {

      titleService.delete(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}