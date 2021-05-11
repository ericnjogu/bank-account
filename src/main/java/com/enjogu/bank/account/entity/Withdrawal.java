package com.enjogu.bank.account.entity;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@SuperBuilder
@Table(name = "withdrawal")
@NoArgsConstructor
public class Withdrawal extends Transaction {
}
