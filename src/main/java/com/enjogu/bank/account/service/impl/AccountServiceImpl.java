package com.enjogu.bank.account.service.impl;

import com.enjogu.bank.account.entity.Account;
import com.enjogu.bank.account.entity.Deposit;
import com.enjogu.bank.account.entity.Withdrawal;
import com.enjogu.bank.account.exception.InvalidTransactionException;
import com.enjogu.bank.account.exception.NotFoundException;
import com.enjogu.bank.account.repository.AccountRepository;
import com.enjogu.bank.account.repository.DepositRepository;
import com.enjogu.bank.account.repository.WithdrawalRepository;
import com.enjogu.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final DepositRepository depositRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final AccountRepository accountRepository;

    @Value("${bank.account.withdrawal.daily-count}")
    private int maxDailyWithdrawalCount;
    @Value("${bank.account.withdrawal.daily-total}")
    private BigDecimal maxDailyWithdrawalTotal;
    @Value("${bank.account.deposit.daily-count}")
    private int maxDailyDepositCount;
    @Value("${bank.account.deposit.daily-total}")
    private BigDecimal maxDailyDepositTotal;

    @Override
    public BigDecimal getBalance(String accountNumber) throws NotFoundException{
        log.debug("getBalance {}", accountNumber);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get().getBalance();
        } else {
            throw new NotFoundException(String.format("account '%s' does not exist", accountNumber));
        }
    }

    @Override
    public void deposit(String accountNumber, BigDecimal amount) throws NotFoundException, InvalidTransactionException {
        log.debug("deposit into {}: {}", accountNumber, amount);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            if (countTodaysDeposits(accountNumber) == maxDailyDepositCount) {
                throw new InvalidTransactionException("daily deposit maximum transactions reached");
            }
            if (sumTodaysDeposits(accountNumber).add(amount).compareTo(maxDailyDepositTotal) >= 0) {
                throw new InvalidTransactionException("daily deposit maximum will be exceeded");
            }
            optionalAccount.get().setBalance(optionalAccount.get().getBalance().add(amount));
            depositRepository.save(Deposit.builder().accountId(optionalAccount.get().getId()).amount(amount).build());
        } else {
            throw new NotFoundException(String.format("account '%s' does not exist", accountNumber));
        }
    }

    @Override
    public void withdraw(String accountNumber, BigDecimal amount) throws NotFoundException, InvalidTransactionException {
        log.debug("withdraw {}: {}", accountNumber, amount);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            BigDecimal balance = optionalAccount.get().getBalance().subtract(amount);
            if (BigDecimal.ZERO.compareTo(balance) > 0) {
                throw new InvalidTransactionException("balance should not be negative");
            }
            if (countTodaysWithdrawals(accountNumber) >= maxDailyWithdrawalCount) {
                throw new InvalidTransactionException("daily withdrawal maximum transactions reached");
            }
            if (sumTodaysWithdrawals(accountNumber).add(amount).compareTo(maxDailyWithdrawalTotal) >= 0) {
                throw new InvalidTransactionException("daily withdrawal maximum will be exceeded");
            }
            optionalAccount.get().setBalance(balance);
            withdrawalRepository.save(Withdrawal.builder()
                .accountId(optionalAccount.get().getId())
                .amount(amount)
                .build()
            );
        } else {
            throw new NotFoundException(String.format("account '%s' does not exist", accountNumber));
        }
    }

    @Override
    public Long countTodaysDeposits(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        Optional<Long> count = depositRepository.countCreatedBetween(pair.left, pair.right, accountNumber);
        log.debug("deposit count: {}", count);
        return count.orElse(0L);
    }

    @Override
    public Long countTodaysWithdrawals(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        Optional<Long> count = withdrawalRepository.countCreatedBetween(pair.left, pair.right, accountNumber);
        log.debug("withdrawal count: {}", count);
        return count.orElse(0L);
    }

    @Override
    public BigDecimal sumTodaysWithdrawals(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        Optional<BigDecimal> sum = withdrawalRepository.sumCreatedBetween(pair.left, pair.right, accountNumber);
        log.debug("withdrawal sum: {}", sum);
        return sum.orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal sumTodaysDeposits(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        Optional<BigDecimal> sum = depositRepository.sumCreatedBetween(pair.left, pair.right, accountNumber);
        log.debug("deposit sum: {}", sum);
        return sum.orElse(BigDecimal.ZERO);
    }

    /**
     * @return dates for the begin and end of today
     */
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
