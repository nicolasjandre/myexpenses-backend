package com.example.myexpenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.services.UserService;
import com.example.myexpenses.dto.user.UserRequestDto;
import com.example.myexpenses.dto.user.UserResponseDto;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
public class UserController implements ICRUDController<UserRequestDto, UserResponseDto> {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<List<UserResponseDto>> getAll() {

        return ResponseEntity.ok(userService.getAll());
    }

    @Override
    public ResponseEntity<UserResponseDto> getById(Long id) {

        return ResponseEntity.ok(userService.getById(id));
    }

    @Override
    public ResponseEntity<UserResponseDto> create(UserRequestDto dto) {

        UserResponseDto userDto = userService.create(dto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserResponseDto> update(Long id, @RequestBody UserRequestDto dto) {

        UserResponseDto user = userService.update(id, dto);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
