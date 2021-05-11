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

    /**
     * count today's deposits
     * @param accountNumber - account number
     * @return count
     */
    Long countTodaysDeposits(String accountNumber);

    /**
     * count today's withdrawals
     * @param accountNumber - account number
     * @return count
     */
    Long countTodaysWithdrawals(String accountNumber);

    /**
     * @param accountNumber - account number
     * @return sum of todays withdrawals
     */
    BigDecimal sumTodaysWithdrawals(String accountNumber);

    /**
     * @param accountNumber - account number
     * @return sum of todays deposits
     */
    BigDecimal sumTodaysDeposits(String accountNumber);
}
