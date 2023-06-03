package com.example.myexpenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.services.CreditCardService;
import com.example.myexpenses.dto.BankDto;
import com.example.myexpenses.dto.CardFlagDto;
import com.example.myexpenses.dto.creditCard.CreditCardRequestDto;
import com.example.myexpenses.dto.creditCard.CreditCardResponseDto;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/creditcard")
public class CreditCardController implements ICRUDController<CreditCardRequestDto, CreditCardResponseDto> {

   @Autowired
   CreditCardService creditCardService;

   @Operation(summary = "Get all default banks.")
   @GetMapping("/banks")
   public ResponseEntity<List<BankDto>> getBanks() {
      return ResponseEntity.status(HttpStatus.OK).body(creditCardService.getAllBanks());
   }

   @Operation(summary = "Get all default flags.")
   @GetMapping("/flags")
   public ResponseEntity<List<CardFlagDto>> getFlags() {
      return ResponseEntity.status(HttpStatus.OK).body(creditCardService.getAllFlags());
   }

   @Override
   @Operation(summary = "Get all credit cards that belongs to the user who is requesting.")
   public ResponseEntity<List<CreditCardResponseDto>> getAll() {

      return ResponseEntity.ok(creditCardService.getAll());
   }

   @Override
   public ResponseEntity<CreditCardResponseDto> getById(Long id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getById'");
   }

   @Override
   @Operation(summary = "Create a credit card. Use this method when creating a title on the front end.")
   public ResponseEntity<List<CreditCardResponseDto>> create(CreditCardRequestDto dto) {

      List<CreditCardResponseDto> creditCard = creditCardService.create(dto);

      return new ResponseEntity<>(creditCard, HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<CreditCardResponseDto> update(Long id, CreditCardRequestDto dto) {
      return ResponseEntity.status(HttpStatus.OK).body(creditCardService.update(id, dto));
   }

   @Override
   public ResponseEntity<Void> delete(Long id) {
      creditCardService.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
   }

}
