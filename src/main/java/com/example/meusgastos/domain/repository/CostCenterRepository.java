package com.example.meusgastos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.meusgastos.domain.model.CostCenter;

public interface CostCenterRepository extends JpaRepository<CostCenter, Long> {}
