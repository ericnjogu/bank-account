package com.enjogu.bank.account.repository;

import com.enjogu.bank.account.entity.Deposit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Date;

@NoRepositoryBean
public interface TransactionRepository<T, ID> extends CrudRepository<T, ID> {
    @Query("select count(*) from #{#entityName} as t where " +
            "t.created between :one and :two and t.accountId = :accountNumber")
    Long countCreatedBetween(Date one, Date two, String accountNumber);
}
