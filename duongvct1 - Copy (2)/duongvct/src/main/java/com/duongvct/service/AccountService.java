package com.duongvct.service;

import com.duongvct.entity.Account;

public interface AccountService {
    public Account findByUsername(String username);
}
