package com.example.myexpenses.dto.jwt;

public class JwtRefreshResponseDto {
   
   private String accessToken;

   public JwtRefreshResponseDto(String accessToken) {
      this.accessToken = accessToken;
   }

   public String getAccessToken() {
      return accessToken;
   }

   public void setAccessToken(String accessToken) {
      this.accessToken = accessToken;
   }
}
