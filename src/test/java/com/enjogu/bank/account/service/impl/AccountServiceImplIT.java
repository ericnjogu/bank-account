package com.enjogu.bank.account.service.impl;

import com.enjogu.bank.account.entity.Account;
import com.enjogu.bank.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class AccountServiceImplIT {
    private final AccountRepository accountRepository;
    private static final String TEST_ACCOUNT_ID = "test-account-001";
    @BeforeEach
    void setUp() {
    }

    @Test
    void checkDefaultAccount() {
        Optional<Account> optionalAccount = accountRepository.findById(TEST_ACCOUNT_ID);
        assertTrue(optionalAccount.isPresent());
        assertEquals(BigDecimal.ZERO, optionalAccount.get().getBalance());
    }

    @Test
    @Disabled
    void getBalance() {
    }

    @Test
    @Disabled
    void deposit() {
    }

    @Test
    @Disabled
    void withdraw() {
    }
}