package com.example.myexpenses.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.User;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

   List<CreditCard> findByUser(User user);
}
