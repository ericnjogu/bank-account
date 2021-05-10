package com.enjogu.bank.account.service.impl;

import com.enjogu.bank.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Override
    public BigDecimal getBalance(String accountNumber) {
        log.debug("getBalance {}", accountNumber);
        return BigDecimal.ZERO;
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount) {
        log.debug("deposit {}: {}", accountNumber, amount);
    }

    @Override
    public void withdraw(String accountNumber, BigDecimal amount) {
        log.debug("deposit {}: {}", accountNumber, amount);
    }
}
