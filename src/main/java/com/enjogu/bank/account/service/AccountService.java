package com.enjogu.bank.account.service;

import java.math.BigDecimal;

public interface AccountService {
    /**
     * @param accountNumber - account number
     * @return balance
     */
    BigDecimal getBalance(String accountNumber);

    /**
     * @param accountNumber - account number
     * @param amount - amount
     */
    void deposit(String accountNumber, BigDecimal amount);

    /**
     * @param accountNumber - account number
     * @param amount - amount
     */
    void withdraw(String accountNumber, BigDecimal amount);
}
