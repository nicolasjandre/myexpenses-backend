package com.example.myexpenses.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.CreditCardInvoice;

public interface CreditCardInvoiceRepository extends JpaRepository<CreditCardInvoice, Long>{

   List<CreditCardInvoice> findByCreditCard(CreditCard creditCard);
   CreditCardInvoice findByCreditCardAndDueDate(CreditCard creditCard, LocalDate dueDate);
}
