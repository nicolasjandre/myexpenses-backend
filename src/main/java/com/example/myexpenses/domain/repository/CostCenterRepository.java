package com.example.myexpenses.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myexpenses.domain.model.CostCenter;

public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {}
