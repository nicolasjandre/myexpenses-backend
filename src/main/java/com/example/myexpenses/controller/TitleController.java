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
   @Operation(summary = "Create a new title for the user who is requesting.", description = """
      <h2>Request body example:</h2>
      <code>
      {<br>
         "description": "Abajur",<br>
         "type": "EXPENSE",<br>
         "value": 50,<br>
         "referenceDate": "2023-04-04T16:40:36",<br>
         "dueDate": "2023-04-25T16:40:36",<br>
         "costCenter": { "id": 1 }<br>
      }
      </code>
         """)
   public ResponseEntity<TitleResponseDto> create(TitleRequestDto dto) {

      TitleResponseDto titleDto = titleService.create(dto);

      return new ResponseEntity<>(titleDto, HttpStatus.CREATED);
   }

   @Override
   @Operation(summary = "Updates a specific title.")
   public ResponseEntity<TitleResponseDto> update(Long id, TitleRequestDto dto) {
      return ResponseEntity.ok(titleService.update(id, dto));
   }

   @Override
   @Operation(summary = "Delete a specific title. (Doesn't remove it from the database, just marks it with an inactive date)")
   public ResponseEntity<?> delete(Long id) {

      titleService.delete(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}