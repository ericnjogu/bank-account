package com.enjogu.bank.account.entity;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deposit")
@SuperBuilder
@NoArgsConstructor
public class Deposit extends Transaction {
}
