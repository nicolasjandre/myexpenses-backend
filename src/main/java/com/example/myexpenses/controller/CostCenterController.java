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

@CrossOrigin("*")
@RestController
@RequestMapping("/api/costcenters")
public class CostCenterController implements ICRUDController<CostCenterRequestDto, CostCenterResponseDto> {

   @Autowired
   private CostCenterService costCenterService;

   @Override
   public ResponseEntity<List<CostCenterResponseDto>> getAll() {

      return ResponseEntity.ok(costCenterService.getAll());
   }

   @Override
   public ResponseEntity<CostCenterResponseDto> getById(Long id) {

      return ResponseEntity.ok(costCenterService.getById(id));
   }

   @Override
   public ResponseEntity<CostCenterResponseDto> create(CostCenterRequestDto dto) {

      CostCenterResponseDto costCenterDto = costCenterService.create(dto);

      return new ResponseEntity<>(costCenterDto, HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<CostCenterResponseDto> update(Long id, CostCenterRequestDto dto) {
      return ResponseEntity.ok(costCenterService.update(id, dto));
   }

   @Override
   public ResponseEntity<?> delete(Long id) {
      
      costCenterService.delete(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}