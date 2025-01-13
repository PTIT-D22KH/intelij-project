package com.duongvct.service.impl;

import com.duongvct.entity.Account;
import com.duongvct.repository.AccountRepository;
import com.duongvct.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account =  accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("Encoded password: {}", account.getPassword());
        return new User(account.getUsername(), account.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(account.getRoles().getId()));

    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
