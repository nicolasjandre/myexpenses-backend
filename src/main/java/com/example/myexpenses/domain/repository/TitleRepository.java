package com.example.myexpenses.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myexpenses.domain.model.Title;

public interface TitleRepository extends JpaRepository<Title, Long> {}
