package com.duongvct.repository;

import com.duongvct.entity.Table;
import com.duongvct.utils.TableStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> , PagingAndSortingRepository<Table, Long> {



    List<Table> findByNameContaining(String searchValue);


    List<Table> findByTableStatus(TableStatus valueOf);

    Page<Table> findByNameContaining(String searchValue, Pageable pageable);

    Page<Table> findByTableStatus(TableStatus valueOf, Pageable pageable);


    Page<Table> findById(Long id, Pageable pageable);
}
