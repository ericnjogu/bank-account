package com.enjogu.bank.account.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
public class Transaction extends BaseEntity {
    @Column(name = "account_id")
    private String accountId;
    private BigDecimal amount;
}
