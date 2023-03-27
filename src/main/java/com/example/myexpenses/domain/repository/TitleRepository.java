package com.example.myexpenses.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myexpenses.domain.model.Title;
import com.example.myexpenses.domain.model.User;

public interface TitleRepository extends JpaRepository<Title, Long> {

      @Query(nativeQuery = true, value = "SELECT * FROM public.title " +
                  "WHERE due_date " +
                  "BETWEEN TO_TIMESTAMP(:initialDate, 'YYYY-MM-DD hh24:MI:SS') AND " +
                  "TO_TIMESTAMP(:finalDate, 'YYYY-MM-DD hh24:MI:SS') AND " + 
                  "user_id = :userId")
      List<Title> getByDueDate(
                  @Param("initialDate") String initialDate,
                  @Param("finalDate") String finalDate,
                  @Param("userId") Long userId
                  );

      List<Title> findByUser(User user);
}
