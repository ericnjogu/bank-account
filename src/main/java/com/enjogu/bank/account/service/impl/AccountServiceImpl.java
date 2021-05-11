package com.enjogu.bank.account.service.impl;

import com.enjogu.bank.account.entity.Account;
import com.enjogu.bank.account.exception.NotFoundException;
import com.enjogu.bank.account.repository.AccountRepository;
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
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final DepositRepository depositRepository;
    private final WithdrawalRepository withdrawalRepository;
    private final AccountRepository accountRepository;

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
    public void deposit(String accountNumber, BigDecimal amount) throws NotFoundException {
        log.debug("deposit into {}: {}", accountNumber, amount);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            optionalAccount.get().setBalance(optionalAccount.get().getBalance().add(amount));
        } else {
            throw new NotFoundException(String.format("account '%s' does not exist", accountNumber));
        }
    }

    @Override
    public void withdraw(String accountNumber, BigDecimal amount) throws NotFoundException {
        log.debug("deposit {}: {}", accountNumber, amount);
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isPresent()) {
            optionalAccount.get().setBalance(optionalAccount.get().getBalance().subtract(amount));
        } else {
            throw new NotFoundException(String.format("account '%s' does not exist", accountNumber));
        }
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

    @Override
    public BigDecimal sumTodaysWithdrawals(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        return withdrawalRepository.sumCreatedBetween(pair.left, pair.right, accountNumber);
    }

    @Override
    public BigDecimal sumTodaysDeposits(String accountNumber) {
        ImmutablePair<Date, Date> pair = getDayStartAndEnd();
        return depositRepository.sumCreatedBetween(pair.left, pair.right, accountNumber);
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
