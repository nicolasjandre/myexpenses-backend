package com.example.myexpenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.services.CostCenterService;
import com.example.myexpenses.dto.costcenter.CostCenterRequestDto;
import com.example.myexpenses.dto.costcenter.CostCenterResponseDto;

import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/costcenters")
public class CostCenterController implements ICRUDController<CostCenterRequestDto, CostCenterResponseDto> {

   @Autowired
   private CostCenterService costCenterService;

   @Override
   @Operation(summary = "Get all the standard cost centers and the one's that were created by the user who's requesting")
   public ResponseEntity<List<CostCenterResponseDto>> getAll() {

      return ResponseEntity.ok(costCenterService.getAll());
   }

   @Override
   @Operation(summary = "Get a specific cost center by its id")
   public ResponseEntity<CostCenterResponseDto> getById(Long id) {

      return ResponseEntity.ok(costCenterService.getById(id));
   }

   @Override
   @Operation(summary = "Creates a new cost center", description = """
         <h2>Request body example (does'nt need the standard, it's automatically set by the server)</h2>
         <code>{<br>
             "description": "Outros",<br>
             "type": "INCOME"<br>
         }</code>

             """)
   public ResponseEntity<List<CostCenterResponseDto>> create(CostCenterRequestDto dto) {

      List<CostCenterResponseDto> costCenterDto = costCenterService.create(dto);

      return new ResponseEntity<>(costCenterDto, HttpStatus.CREATED);
   }

   @Override
   @Operation(summary = "Update informations about a specific cost center")
   public ResponseEntity<CostCenterResponseDto> update(Long id, CostCenterRequestDto dto) {
      return ResponseEntity.ok(costCenterService.update(id, dto));
   }

   @Override
   @Operation(summary = "Delete a cost center (does'nt remove from the database, just puts an innative date)")
   public ResponseEntity<?> delete(Long id) {

      costCenterService.delete(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}