package com.epam.sql.banksystem.entity;

import java.sql.Date;

public class Investment extends Operation {
    public Investment() {
    }

    public Investment(int IDOperation, int IDBank, int IDClient, int amount, double percent, String currency, Date dateIssue, Date dateReturn) {
        super(IDOperation, IDBank, IDClient, amount, percent, currency, dateIssue, dateReturn);
    }
}
