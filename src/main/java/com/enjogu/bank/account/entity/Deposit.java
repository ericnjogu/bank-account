package com.enjogu.bank.account.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "deposit")
public class Deposit extends Transaction{
}
