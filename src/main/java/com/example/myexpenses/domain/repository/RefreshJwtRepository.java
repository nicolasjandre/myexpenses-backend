package com.example.myexpenses.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.myexpenses.domain.model.RefreshJwt;
import com.example.myexpenses.domain.model.User;

public interface RefreshJwtRepository extends JpaRepository<RefreshJwt, Long> {
  Optional<RefreshJwt> findByToken(String token);

  @Modifying
  int deleteByUser(User user);

  @Query(nativeQuery = true, value = 
  "DELETE FROM public.refreshtoken WHERE expiration_date < now()"
  )
  void deleteByExpirationDate();
}
