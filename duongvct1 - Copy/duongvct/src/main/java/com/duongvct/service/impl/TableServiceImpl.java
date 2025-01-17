package com.duongvct.service.impl;

import com.duongvct.entity.Table;
import com.duongvct.repository.TableRepository;
import com.duongvct.service.TableService;
import com.duongvct.utils.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Override
    public List<Table> findAll() {
        return tableRepository.findAll();
    }

    @Override
    public Table findById(Long id) {
        return tableRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Table table) {

        tableRepository.save(table);
    }

    @Override
    public void deleteById(Long id) {
        tableRepository.deleteById(id);
    }

    @Override
    public List<Table> findAllFreeTables() {
        return tableRepository.findByTableStatus(TableStatus.FREE);
    }

    @Override
    public List<Table> searchTables(String searchColumn, String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return tableRepository.findAll();
        }
        switch (searchColumn) {
            case "id":
                Table table = tableRepository.findById(Long.parseLong(searchValue)).orElse(null);
                return table != null ? Collections.singletonList(table) : Collections.emptyList();
            case "name":
                return tableRepository.findByNameContaining(searchValue);
            case "status":
                return tableRepository.findByTableStatus(TableStatus.valueOf(searchValue.toUpperCase()));
            default:
                return tableRepository.findAll();
        }
    }
    @Override
    public Page<Table> findAll(Pageable pageable) {
        return tableRepository.findAll(pageable);
    }

    @Override
    public Page<Table> searchTables(String searchColumn, String searchValue, Pageable pageable) {
        if (searchValue == null || searchValue.isEmpty()) {
            return tableRepository.findAll(pageable);
        }
        switch (searchColumn) {
            case "id":
                return tableRepository.findById(Long.parseLong(searchValue), pageable);
            case "name":
                return tableRepository.findByNameContaining(searchValue, pageable);
            case "status":
                return tableRepository.findByTableStatus(TableStatus.valueOf(searchValue.toUpperCase()), pageable);
            default:
                return tableRepository.findAll(pageable);
        }
    }
}