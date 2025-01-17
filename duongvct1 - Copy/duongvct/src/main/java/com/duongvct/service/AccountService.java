package com.duongvct.service;

import com.duongvct.entity.Account;
import com.duongvct.utils.Role;

import java.util.List;

public interface AccountService {
    public Account findByUsername(String username);

    public void save(Account account);

    public List<Account> findAll();

    public void deleteById(Long id);


    public Account findById(Long id);

    public List<Account> findAllUsers();

    public List<Account> findByRole(Role role);

    public List<Account> searchCustomers(String searchColumn, String searchValue);

    public List<Account> searchEmployees(String searchColumn, String searchValue);
}
