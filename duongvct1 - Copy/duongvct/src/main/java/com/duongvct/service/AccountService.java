package com.duongvct.service;

import com.duongvct.entity.Account;

import java.util.List;

public interface AccountService {
    public Account findByUsername(String username);

    public void save(Account account);

    public List<Account> findAll();

    public void deleteById(Long id);


    public Account findById(Long id);
}
