package com.duongvct.service;

import com.duongvct.entity.Table;

import java.util.List;

public interface TableService {
    List<Table> findAll();
    Table findById(Long id);
    void save(Table table);
    void deleteById(Long id);

    public List<Table> findAllFreeTables();
}