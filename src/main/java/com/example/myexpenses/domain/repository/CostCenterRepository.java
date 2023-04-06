package com.example.myexpenses.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myexpenses.domain.model.CostCenter;
import com.example.myexpenses.domain.model.User;

public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {

   List<CostCenter> findByUser(User user);
   List<CostCenter> findByStandard(boolean standard);
}