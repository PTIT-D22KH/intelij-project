package com.duongvct.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InactiveAccountException extends UsernameNotFoundException {

    public InactiveAccountException(String msg) {
        super(msg);
    }
}
