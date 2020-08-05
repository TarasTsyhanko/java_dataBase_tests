package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.dao.BankDAO;
import com.epam.sql.banksystem.dao.LoanDAO;
import com.epam.sql.banksystem.dao.PersonDAO;
import com.epam.sql.banksystem.dao.databasedao.BankDataBaseDAO;
import com.epam.sql.banksystem.dao.databasedao.LoanDataBaseDAO;
import com.epam.sql.banksystem.dao.databasedao.PersonDataBaseDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanService {
    LoanDAO loanDAO;
    BankDAO bankDAO;
    PersonDAO personDAO;

    public LoanService() {
        loanDAO = new LoanDataBaseDAO();
        bankDAO =new BankDataBaseDAO();
        personDAO = new PersonDataBaseDAO();
    }

    public List<Loan> getAllLoan(){
        return loanDAO.getAllLoan();
    }
    public List<Loan> getAllLoanInOneBank( Bank bank){
        if(bankDAO.isBankExists(bank.getName())) {
            return loanDAO.getAllLoanInBank(bank);
        }
        return new ArrayList<>();
    }
    public List<Loan> getAllLoanIByClient(int IDClient) throws InfoException {
        List<Loan> loanList= loanDAO.getAllLoanByClient(IDClient);
        if(loanList.isEmpty()){
            throw  new InfoException("This com.epam.sql.client has no any loan");
        }
        return loanList;
    }
    public Loan updateLoan(Loan loan) throws InfoException {
        Loan loanDB = loanDAO.getLoanByID(loan.getIDOperation());
        if(loanDB.getIDClient()!=loan.getIDClient()||loanDB.getIDBank()!=loan.getIDBank()){
            throw new InfoException("You can't change Bank or Client");
        }
        loanDAO.updateLoan(loan);
        return loanDAO.getLoanByID(loan.getIDOperation());
    }
    public Loan getLoanByID(Loan loan) throws InfoException {
        Loan loanDB = loanDAO.getLoanByID(loan.getIDOperation());
        if(loanDB!=loan){
            throw new InfoException("This Loan absent");
        }
        return loan;
    }

    public void insertLoan( Loan loan) throws InfoException {
        if(loan.getIDBank()==loan.getIDClient()){
            throw new InfoException("");
        }
        if(!loanDAO.isClientHasAlreadyLoan(loan.getIDClient(),loan.getIDBank())){
            loanDAO.insertLoan(loan);
        }else {
            throw new InfoException("This Client already has LOAN in this Bank");
        }
    }
    public void deleteLoan(Loan loan) throws InfoException {
        if(getLoanByID(loan) == loan){
            loanDAO.deleteLoan(loan);
        }else throw new InfoException("This loan absent");
    }

}
