package com.duongvct.service.impl;

import com.duongvct.entity.Account;
import com.duongvct.exception.InactiveAccountException;
import com.duongvct.repository.AccountRepository;
import com.duongvct.service.AccountService;
import com.duongvct.utils.Role;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account =  accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!account.isActivated()) {
            throw new InactiveAccountException("Account is not activated. Please contact admin");
        }
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

    @Override
    public List<Account> findAllUsers() {
        List<Account> a = accountRepository.findAll();
        List<Account> accounts = new ArrayList<>();

        for (Account account : a) {
            if (account.getRoles() == Role.ROLE_USER) {
                accounts.add(account);
            }
        }
        return accounts;
    }

    @Override
    public List<Account> findByRole(Role role) {
        return accountRepository.findByRoles(role);
    }


    @Override
    public List<Account> searchCustomers(String searchColumn, String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return accountRepository.findByRoles(Role.ROLE_USER);
        }
        List<Account> accounts = new ArrayList<>();
        switch (searchColumn) {
            case "id":
                accounts = accountRepository.findUserById(searchValue, Role.ROLE_USER);
                break;

            case "username":
                accounts = accountRepository.findUserByUsernameContaining(searchValue, Role.ROLE_USER);
                break;
            case "fullname":
                accounts = accountRepository.findUserByFullnameContaining(searchValue, Role.ROLE_USER);
                break;
            case "email":
                accounts = accountRepository.findUserByEmailContaining(searchValue, Role.ROLE_USER);
                break;
            case "phoneNumber":
                accounts = accountRepository.findUserByPhoneNumberContaining(searchValue, Role.ROLE_USER);
                break;
            case "address":
                accounts = accountRepository.findUserByAddressContaining(searchValue, Role.ROLE_USER);
                break;
            default:
                accounts = accountRepository.findByRoles(Role.ROLE_USER);
                break;
        }
        return accounts;
    }

    @Override
    public List<Account> searchEmployees(String searchColumn, String searchValue) {
        if (searchValue == null || searchValue.isEmpty()) {
            return accountRepository.findByRoles(Role.ROLE_EMPLOYEE);
        }
        List<Account> accounts = new ArrayList<>();
        switch (searchColumn) {
            case "id":
                accounts = accountRepository.findUserById(searchValue, Role.ROLE_EMPLOYEE);
                break;

            case "username":
                accounts = accountRepository.findUserByUsernameContaining(searchValue, Role.ROLE_EMPLOYEE);
                break;
            case "fullname":
                accounts = accountRepository.findUserByFullnameContaining(searchValue, Role.ROLE_EMPLOYEE);
                break;
            case "email":
                accounts = accountRepository.findUserByEmailContaining(searchValue, Role.ROLE_EMPLOYEE);
                break;
            case "phoneNumber":
                accounts = accountRepository.findUserByPhoneNumberContaining(searchValue, Role.ROLE_EMPLOYEE);
                break;
            case "address":
                accounts = accountRepository.findUserByAddressContaining(searchValue, Role.ROLE_EMPLOYEE);
                break;
            default:
                accounts = accountRepository.findByRoles(Role.ROLE_USER);
                break;
        }
        return accounts;
    }
}
