package com.epam.sql.banksystem.entity;

import java.sql.Date;

public class Deposit extends Operation {
    public Deposit() {
    }

    public Deposit(int IDOperation, int IDBank, int IDClient, int amount, double percent, String currency, Date dateIssue, Date dateReturn) {
        super(IDOperation, IDBank, IDClient, amount, percent, currency, dateIssue, dateReturn);
    }
}
