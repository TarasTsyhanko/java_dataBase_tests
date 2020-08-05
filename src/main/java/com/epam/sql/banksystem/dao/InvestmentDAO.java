package com.epam.sql.banksystem.dao;

import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Investment;

import java.util.List;

public interface InvestmentDAO {
    List<Investment> getAllInvestment();
    List<Investment> getAllInvestmentByClient(int IDClient);
    List<Investment> getAllInvestmentInBank(Bank bank);
    Investment getInvestmentByID(int investmentID);
    void insertInvestment(Investment investment);
    void updateInvestment(Investment investment);
    void deleteInvestment(Investment investment);
}
