package com.enjogu.bank.account.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Account extends BaseEntity{
    private String accountNumber;
    private BigDecimal balance;
}
