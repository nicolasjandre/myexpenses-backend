package com.example.myexpenses.domain.services;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myexpenses.domain.model.RefreshJwt;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.RefreshJwtRepository;
import com.example.myexpenses.domain.repository.UserRepository;
import com.example.myexpenses.handler.TokenRefreshException;

@Service
public class RefreshJwtService {

   @Value("${auth.refreshjwt.expiration}")
   private Long refreshTokenDuration;

   @Autowired
   private RefreshJwtRepository refreshJwtRepository;

   @Autowired
   private UserRepository userRepository;

   public Optional<RefreshJwt> findByToken(String token) {

      return refreshJwtRepository.findByToken(token);
   }

   public RefreshJwt createRefreshToken(Long userId) {

      RefreshJwt refreshToken = new RefreshJwt();

      User user = userRepository.findById(userId).get();

      refreshToken.setUser(user);
      refreshToken.setExpirationDate(new Date(System.currentTimeMillis() + refreshTokenDuration));
      refreshToken.setToken(UUID.randomUUID().toString());

      refreshToken = refreshJwtRepository.save(refreshToken);
      return refreshToken;
   }

   public RefreshJwt verifyExpiration(RefreshJwt token) {

      if (token.getExpirationDate().compareTo(new Date()) < 0) {
         refreshJwtRepository.deleteByExpirationDate();
         throw new TokenRefreshException(token.getToken(), "O refresh token expirou, por favor faça um novo login.");
      }

      return token;
   }

   @Transactional
   public void deleteByExpirationDate() {
      refreshJwtRepository.deleteByExpirationDate();
   }
}
