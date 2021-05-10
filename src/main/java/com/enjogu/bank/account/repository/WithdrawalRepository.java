package com.enjogu.bank.account.repository;

import com.enjogu.bank.account.entity.Withdrawal;
import org.springframework.data.repository.CrudRepository;

public interface WithdrawalRepository extends CrudRepository<Withdrawal, Integer> {
}
