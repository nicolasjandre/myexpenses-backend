package com.example.myexpenses.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myexpenses.domain.model.RefreshJwt;

public interface RefreshJwtRepository extends JpaRepository<RefreshJwt, Long> {

  Optional<RefreshJwt> findByToken(String token);

  @Query(nativeQuery = true, value = "SELECT * FROM refreshtoken WHERE users = :userId AND expiration_date > now() LIMIT 1")
  Optional<RefreshJwt> findByUsers(@Param("userId") Long userId);

  @Query(nativeQuery = true, value = "DELETE FROM public.refreshtoken WHERE expiration_date < now()")
  void deleteByExpirationDate();
}
