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

@CrossOrigin("*")
@RestController
@RequestMapping("/api/titles")
public class TitleController implements ICRUDController<TitleRequestDto, TitleResponseDto> {

   @Autowired
   private TitleService titleService;

   @Override
   public ResponseEntity<List<TitleResponseDto>> getAll() {

      return ResponseEntity.ok(titleService.getAll());
   }

   @Override
   public ResponseEntity<TitleResponseDto> getById(Long id) {

      return ResponseEntity.ok(titleService.getById(id));
   }

   @Override
   public ResponseEntity<TitleResponseDto> create(TitleRequestDto dto) {

      TitleResponseDto titleDto = titleService.create(dto);

      return new ResponseEntity<>(titleDto, HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<TitleResponseDto> update(Long id, TitleRequestDto dto) {
      return ResponseEntity.ok(titleService.update(id, dto));
   }

   @Override
   public ResponseEntity<?> delete(Long id) {

      titleService.delete(id);

      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}