package com.example.myexpenses.domain.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myexpenses.domain.model.Title;
import com.example.myexpenses.domain.model.User;

public interface TitleRepository extends JpaRepository<Title, Long> {

      List<Title> findByUser(User user);

      List<Title> findByReferenceDateBetweenAndUserId(@Param("initialDate") Date initialDate,
                  @Param("finalDate") Date finalDate,
                  @Param("userId") Long userId);

      @Query(nativeQuery = true, value = "select title.* from public.title " +
                  "left join credit_card_invoice ON credit_card_invoice.invoice_id = title.invoice_id " +
                  "where credit_card_invoice.is_paid = false " +
                  "and credit_card_invoice.creditcard_id = :creditCardId")
      List<Title> getUnpaidTitlesByCreditCard(@Param("creditCardId") Long creditCardId);
}
