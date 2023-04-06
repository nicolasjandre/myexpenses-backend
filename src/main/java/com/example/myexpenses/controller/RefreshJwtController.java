package com.example.myexpenses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.model.RefreshJwt;
import com.example.myexpenses.domain.services.RefreshJwtService;
import com.example.myexpenses.dto.jwt.JwtRefreshRequestDto;
import com.example.myexpenses.dto.jwt.JwtRefreshResponseDto;
import com.example.myexpenses.handler.TokenRefreshException;
import com.example.myexpenses.security.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/refresh")
public class RefreshJwtController {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private RefreshJwtService refreshJwtService;

  @PostMapping
  @Operation(summary = "Endpoint to refresh an expired JWT using the refresh token. If refresh token is also expired or invalid, the unauthenticated user needs to login again.")
   public ResponseEntity<?> refreshtoken(@RequestBody JwtRefreshRequestDto request) {
     String requestRefreshToken = request.getRefreshToken();
 
     return refreshJwtService.findByToken(requestRefreshToken)
         .map(refreshJwtService::verifyExpiration)
         .map(RefreshJwt::getUser)
         .map(user -> {
           String token = jwtUtil.generateTokenByEmail(user.getEmail());
           return ResponseEntity.ok(new JwtRefreshResponseDto("Bearer " + token));
         })
         .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
             "Este refresh token não existe na base de dados."));
   }
}
