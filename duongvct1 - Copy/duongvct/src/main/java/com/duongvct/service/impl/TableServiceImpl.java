package com.duongvct.service.impl;

import com.duongvct.entity.Table;
import com.duongvct.repository.TableRepository;
import com.duongvct.service.TableService;
import com.duongvct.utils.TableStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return tableRepository.findAllByTableStatus(TableStatus.FREE);
    }
}