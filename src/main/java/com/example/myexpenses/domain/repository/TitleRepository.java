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

      @Query(nativeQuery = true, value = "SELECT title.*, inv.invoice_id AS inv_invoice_id, " +
                  "inv.closing_date AS inv_closing_date, inv.creditcard_id AS inv_creditcard_id, " +
                  "inv.due_date AS inv_due_date, inv.is_paid AS inv_is_paid FROM title " +
                  "LEFT JOIN credit_card_invoice AS inv ON inv.invoice_id = title.invoice_id " +
                  "WHERE inv.due_date BETWEEN :initialDate AND :finalDate " +
                  "AND title.user_id = :userId")
      List<Title> findByInvoiceDueDateBetweenAndUserId(
                  @Param("initialDate") Date initialDate,
                  @Param("finalDate") Date finalDate,
                  @Param("userId") Long userId);

      @Query(nativeQuery = true, value = "select title.* from public.title " +
                  "left join credit_card_invoice ON credit_card_invoice.invoice_id = title.invoice_id " +
                  "where credit_card_invoice.is_paid = false " +
                  "and credit_card_invoice.creditcard_id = :creditCardId")
      List<Title> getUnpaidTitlesByCreditCard(@Param("creditCardId") Long creditCardId);

      @Query(nativeQuery = true, value = "SELECT * FROM title " +
                  "WHERE reference_date >= current_date - (interval '1 day' * :daysQuantity) " +
                  "AND reference_date < current_date + interval '1 day' - interval '1 second' " +
                  "AND user_id = :userId ORDER BY reference_date")
      List<Title> findByLastXDays(@Param("daysQuantity") Long daysQuantity, @Param("userId") Long userId);

}
