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
  @Operation(summary = """
    Endpoint to refresh an expired JWT using the refresh token. 
    If refresh token is also expired or invalid, the unauthenticated user needs to login again.""",
    description = """
        <h2>Example of request body:</h2>
        <code>
        {
          "refreshToken": "86e2cf61-6464-4c1b-8a1d-986e1c0de80a"
          }
        </code>
        <br>
        <h2>Login is not being showed here, but it happens requesting the /api/auth with this body:</h2>
        <code>
        {
          "email": "nicolasjandre@live.com",
          "password": "12345678"
        }
        </code>
        <h2>After a successful login, the user get a answer with his token and his refresh token, like this:</h2><br>
        <code>
      {
          "token": "Bearer eyJhbGciOiJIUzUxMiJ9R...",<br>
          "refreshToken": "60bf817b-ccae-4170-a0cd-24ec91f8836b",<br>
          "user": {<br>
            "id": 2,<br>
            "name": "Nicolas Jandre",<br>
            "email": "nicolasjandre@live.com",<br>
            "created_at": "Apr 5, 2023, 9:29:54 AM",<br>
            "updated_at": "Apr 5, 2023, 9:09:34 PM"<br>
          }<br>
        }
        </code>
        """)
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
