package com.duongvct.service.impl;

import com.duongvct.entity.Account;
import com.duongvct.repository.AccountRepository;
import com.duongvct.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    private static final Logger log = LoggerFactory.getLogger(RegisterServiceImpl.class);
    @Override
    public void registerAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        log.info("Encoded password during registration: {}", account.getPassword());
        account.setActivated(true);
        account.setFirstLogin(true);
        accountRepository.save(account);
    }
}
