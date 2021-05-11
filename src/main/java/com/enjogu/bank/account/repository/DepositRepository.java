package com.enjogu.bank.account.repository;

import com.enjogu.bank.account.entity.Deposit;
import org.springframework.data.repository.CrudRepository;

public interface DepositRepository extends CrudRepository<Deposit, String> {
}
