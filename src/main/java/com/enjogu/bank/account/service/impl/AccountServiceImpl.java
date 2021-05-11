package com.enjogu.bank.account.service.impl;

import com.enjogu.bank.account.entity.Withdrawal;
import com.enjogu.bank.account.repository.DepositRepository;
import com.enjogu.bank.account.repository.WithdrawalRepository;
import com.enjogu.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final DepositRepository depositRepository;
    private final WithdrawalRepository withdrawalRepository;
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

    @Override
    public Long countTodaysDeposits(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        return depositRepository.countCreatedBetween(pair.left, pair.right, accountNumber);
    }

    @Override
    public Long countTodaysWithdrawals(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        return withdrawalRepository.countCreatedBetween(pair.left, pair.right, accountNumber);
    }

    ImmutablePair<Date, Date> getDayStartAndEnd() {
        Calendar now = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));
        Date begin = cal.getTime();
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        Date end = cal.getTime();

        return ImmutablePair.of(begin, end);
    }
}
