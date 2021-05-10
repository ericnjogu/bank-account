package com.enjogu.bank.account.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.sql.Date;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;
}
