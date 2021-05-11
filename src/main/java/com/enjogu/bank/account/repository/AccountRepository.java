package com.enjogu.bank.account.repository;

import com.enjogu.bank.account.entity.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, String> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
