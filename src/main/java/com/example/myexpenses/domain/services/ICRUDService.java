package com.example.myexpenses.domain.services;

import java.util.List;

public interface ICRUDService<Req, Res> {
 
    List<Res> getAll();
    Res getById(Long id);
    Res create(Req dto);
    Res update(Long id, Req dto);
    void delete(Long id);
}