package com.epam.sql.banksystem.dao;

import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Loan;

import java.util.List;

public interface LoanDAO {
    List<Loan> getAllLoan();
    List<Loan> getAllLoanByClient(int IDClient);
    List<Loan> getAllLoanInBank(Bank bank);
    Loan getLoanByID(int loanID);
    void insertLoan(Loan loan);
    void updateLoan(Loan loan);
    void deleteLoan(Loan loan);
    boolean isClientHasAlreadyLoan(int IDClient, int IDBank);
}
