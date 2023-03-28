package com.example.myexpenses.dto.jwt;

public class JwtRefreshRequestDto {
   
   private String refreshToken;

   public String getRefreshToken() {
      return refreshToken;
   }

   public void setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
   }
}
