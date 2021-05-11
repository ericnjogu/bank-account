package com.enjogu.bank.account.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "account")
@SuperBuilder
@NoArgsConstructor
public class Account extends BaseEntity {
    @Column(name = "account_number")
    private String accountNumber;
    private BigDecimal balance;
}
