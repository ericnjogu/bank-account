package com.enjogu.bank.account.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
@Getter
@Setter
public class Transaction extends BaseEntity {
    private String accountId;
    private String id;
    private BigDecimal amount;
}
