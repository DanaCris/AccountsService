package com.accounts.service.account;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int id;
    private String iban;
    private String currency;
    private double balance;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;

    protected Account() {
    }

    public Account(int id, String iban, String currency, double balance, Date lastUpdated) {
        this.id = id;
        this.iban = iban;
        this.currency = currency;
        this.balance = balance;
        this.lastUpdated = lastUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
