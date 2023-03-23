package com.example.meusgastos.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meusgastos.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByEmail(String email);
}
