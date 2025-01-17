package com.duongvct.service;

import com.duongvct.entity.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TableService {
    List<Table> findAll();
    Table findById(Long id);
    void save(Table table);
    void deleteById(Long id);

    public List<Table> findAllFreeTables();

    public List<Table> searchTables(String searchColumn, String searchValue);

    public Page<Table> findAll(Pageable pageable);
    public Page<Table> searchTables(String searchColumn, String searchValue, Pageable pageable);
}