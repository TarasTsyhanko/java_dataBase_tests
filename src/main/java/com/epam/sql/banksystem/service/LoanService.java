package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.dao.BankDAO;
import com.epam.sql.banksystem.dao.LoanDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Loan;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static com.epam.sql.banksystem.listeners.ExtentTestManager.infoLog;

public class LoanService {
    @Inject
    LoanDAO loanDAO;
    @Inject
    BankDAO bankDAO;

    public List<Loan> getAllLoan() {
        return loanDAO.getAllLoan();
    }

    public List<Loan> getAllLoanInOneBank(Bank bank) {
        infoLog("try to get all loan in : " + bank);
        if (bankDAO.isBankExists(bank.getName())) {
            return loanDAO.getAllLoanInBank(bank);
        }
        return new ArrayList<>();
    }

    public List<Loan> getAllLoanIByClient(int IDClient) {
        List<Loan> loanList = loanDAO.getAllLoanByClient(IDClient);
        if (loanList.isEmpty()) {
            infoLog("This client has no any loan");
        }
        return loanList;
    }

    public Loan updateLoan(Loan loan) {
        infoLog("try to update :" + loan);
        Loan loanDB = loanDAO.getLoanByID(loan.getIDOperation());
        if (loanDB.getIDClient() != loan.getIDClient() || loanDB.getIDBank() != loan.getIDBank()) {
            infoLog("You can't change Bank or Client");
        }
        loanDAO.updateLoan(loan);
        infoLog("loan successfully updated");
        return loanDAO.getLoanByID(loan.getIDOperation());
    }

    public Loan getLoanByID(Loan loan) {
        infoLog("try to get loan by id : " + loan.getIDOperation());
        Loan loanDB = loanDAO.getLoanByID(loan.getIDOperation());
        if (loanDB != loan) {
            infoLog("This Loan absent");
            return null;
        }else {
        infoLog(loan + " successfully fined");
        return loan;
        }

    }

    public void insertLoan(Loan loan) {
        infoLog("try to add : "+loan);
        if (loan.getIDBank() == loan.getIDClient()) {
            infoLog("this operation imposible");
        }else if (!loanDAO.isClientHasAlreadyLoan(loan.getIDClient(), loan.getIDBank())) {
            loanDAO.insertLoan(loan);
            infoLog("loan successfully added");
        } else {
            infoLog("This Client already has LOAN in this Bank");
        }
    }

    public void deleteLoan(Loan loan) {
        infoLog("try to delete : "+loan);
        if (getLoanByID(loan) == loan) {
            loanDAO.deleteLoan(loan);
            infoLog("loan successfully deleted");
        } else infoLog("This loan absent");
    }

}
