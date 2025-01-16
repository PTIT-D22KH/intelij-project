package com.duongvct.repository;

import com.duongvct.entity.Account;
import com.duongvct.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByUsername(String username);

    public void deleteById(Long id);

    public List<Account> findByRoles(Role role);

}
