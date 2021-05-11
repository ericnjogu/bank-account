package com.enjogu.bank.account.service.impl;

import com.enjogu.bank.account.entity.Account;
import com.enjogu.bank.account.exception.InvalidTransactionException;
import com.enjogu.bank.account.exception.NotFoundException;
import com.enjogu.bank.account.repository.AccountRepository;
import com.enjogu.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
class AccountServiceImplIT {
    private final AccountRepository accountRepository;
    private final JdbcTemplate jdbcTemplate;
    private final AccountService accountService;


    private static final String TEST_ACCOUNT_ID = "test-account-001";
    private static final String TEST_ACCOUNT_NUMBER = "2342";
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
    void checkAccountTimestamps() {
        String accountNumber = "new-account-001";
        Account account = Account.builder()
                .accountNumber("1827")
                .accountNumber(accountNumber)
                .balance(new BigDecimal("3")).build();
        accountRepository.save(account);
        Optional<Account> fetchedAccount = accountRepository.findByAccountNumber(accountNumber);
        if(fetchedAccount.isPresent()) {
            assertNotNull(fetchedAccount.get().getCreated(), "Created");
            assertNotNull(fetchedAccount.get().getModified(), "Modified");
        } else {
            fail("account is missing");
        }
    }


    @Test
    @DisplayName("deposit and get balance for existing account")
    void getBalance_01() throws NotFoundException, InvalidTransactionException {
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("1.2"));
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("2.3"));
        assertEquals(new BigDecimal("3.5"), accountService.getBalance(TEST_ACCOUNT_NUMBER));
    }

    @Test
    @DisplayName("verify today's deposit count")
    void countTodaysDeposits() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        // yesterday
        String sql = "insert into deposit (id, account_id, amount, created) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.25, cal.getTime());
        //future
        cal.roll(Calendar.DAY_OF_MONTH, 3);
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.27, cal.getTime());
        //current
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.26,
                Calendar.getInstance().getTime());
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), "test_account_2", 10.30,
                Calendar.getInstance().getTime());

        assertEquals(1, accountService.countTodaysDeposits(TEST_ACCOUNT_NUMBER));
    }

    @Test
    @DisplayName("verify today's withdraw count")
    void countTodaysWithdrawals() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        // yesterday
        String sql = "insert into withdrawal (id, account_id, amount, created) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.25, cal.getTime());
        //future
        cal.roll(Calendar.DAY_OF_MONTH, 3);
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.27, cal.getTime());
        //current
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.26,
                Calendar.getInstance().getTime());
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.30,
                Calendar.getInstance().getTime());
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), "test_account_2", 10.30,
                Calendar.getInstance().getTime());

        assertEquals(2, accountService.countTodaysWithdrawals(TEST_ACCOUNT_NUMBER));
    }

    @Test
    @DisplayName("verify today's total deposit total")
    void sumTodaysDeposits() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        // yesterday
        String sql = "insert into deposit (id, account_id, amount, created) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.25, cal.getTime());
        //future
        cal.roll(Calendar.DAY_OF_MONTH, 3);
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.27, cal.getTime());
        //current
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 14.43,
                Calendar.getInstance().getTime());
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), "test_account_2", 10.30,
                Calendar.getInstance().getTime());

        assertEquals(new BigDecimal("14.43"), accountService.sumTodaysDeposits(TEST_ACCOUNT_NUMBER));
    }

    @Test
    @DisplayName("verify today's withdraw total")
    void sumTodaysWithdrawals() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        // yesterday
        String sql = "insert into withdrawal (id, account_id, amount, created) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.25, cal.getTime());
        //future
        cal.roll(Calendar.DAY_OF_MONTH, 3);
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 10.27, cal.getTime());
        //current
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 1,
                Calendar.getInstance().getTime());
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), TEST_ACCOUNT_ID, 90,
                Calendar.getInstance().getTime());
        jdbcTemplate.update(sql, UUID.randomUUID().toString(), "test_account_2", 10.30,
                Calendar.getInstance().getTime());

        assertEquals(new BigDecimal("91"), accountService.sumTodaysWithdrawals(TEST_ACCOUNT_NUMBER));
    }

    @Test
    @DisplayName("withdraw and get balance for existing account")
    void withdraw_01() throws NotFoundException, InvalidTransactionException {
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("10"));
        accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("2"));
        accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("7"));
        assertEquals(new BigDecimal("1"), accountService.getBalance(TEST_ACCOUNT_NUMBER));
    }

    @Test
    @DisplayName("balance should not go below zero")
    void withdraw_02() throws NotFoundException, InvalidTransactionException {
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("10"));
        assertThrows(
                InvalidTransactionException.class,
                () -> accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("11"))
        );
    }

    @Test
    @DisplayName("daily withdrawals should not exceed configured count")
    void withdraw_03() throws InvalidTransactionException, NotFoundException {
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("10"));
        accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("1"));
        accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("2"));
        accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("3"));
        assertThrows(
                InvalidTransactionException.class,
                () -> accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("4"))
        );
    }

    @Test
    @DisplayName("daily withdrawals should not exceed configured total")
    void withdraw_04() throws InvalidTransactionException, NotFoundException {
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("60000"));
        accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("20000"));
        accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("20000"));
        assertThrows(
                InvalidTransactionException.class,
                () -> accountService.withdraw(TEST_ACCOUNT_NUMBER, new BigDecimal("15000"))
        );
    }

    @Test
    @DisplayName("daily deposits should not exceed configured count")
    void deposit_01() throws InvalidTransactionException, NotFoundException {
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("10"));
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("1"));
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("2"));
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("7"));
        assertThrows(
                InvalidTransactionException.class,
                () -> accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("4"))
        );
    }

    @Test
    @DisplayName("daily deposits should not exceed configured total")
    void deposit_02() throws InvalidTransactionException, NotFoundException {
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("60000"));
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("20000"));
        accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("20000"));
        assertThrows(
                InvalidTransactionException.class,
                () -> accountService.deposit(TEST_ACCOUNT_NUMBER, new BigDecimal("51000"))
        );
    }
}