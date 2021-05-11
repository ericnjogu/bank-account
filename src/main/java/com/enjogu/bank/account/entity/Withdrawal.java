package com.enjogu.bank.account.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "withdrawal")
public class Withdrawal extends Transaction {
}
