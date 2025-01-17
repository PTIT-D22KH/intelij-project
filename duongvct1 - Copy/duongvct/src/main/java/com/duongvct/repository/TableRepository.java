package com.duongvct.repository;

import com.duongvct.entity.Table;
import com.duongvct.utils.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
    List<Table> findAllByTableStatus(TableStatus free);


    List<Table> findByNameContaining(String searchValue);


    List<Table> findByTableStatus(TableStatus valueOf);
}
