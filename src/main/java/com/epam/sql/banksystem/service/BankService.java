package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.dao.*;
import com.epam.sql.banksystem.dao.databasedao.*;
import com.epam.sql.banksystem.entity.*;

import java.util.List;

public class BankService {
    private BankDAO bankDAO;
    private LocationDAO locationDAO;
    private DepositDAO depositDAO;
    private LoanDAO loanDAO;
    private InvestmentDAO investmentDAO;

    public BankService() {
        bankDAO = new BankDataBaseDAO();
        locationDAO = new LocationDataBaseDAO();
        depositDAO = new DepositDataBaseDAO();
        loanDAO = new LoanDataBaseDAO();
        investmentDAO = new InvestmentDataBaseDAO();
    }

    public Bank getBankByName(String name) throws InfoException {
        if (bankDAO.isBankExists(name)) {
            return bankDAO.getBankByName(name);
        }else {
            throw new InfoException("This bank is absent");
        }
    }

    public List<Bank> getAllBanks() {
        return bankDAO.getAllBanks();
    }

    public void insertBank( Bank bank) throws InfoException {
        if (!(bankDAO.isBankExists(bank.getName()))) {
            if (locationDAO.isLocationFree(bank.getIDLocation())) {
                bankDAO.insertBank(bank);
            }else {
                throw new InfoException("This Location already has Bank");
            }
        }else {
            throw new InfoException("This bank already exists");
        }
    }

    public void updateBank(Bank bank) throws InfoException {
        Bank bankDB = bankDAO.getBankByID(bank.getId());
        if (!bank.getName().equals(bankDB.getName()) && bank.getIDLocation() != bankDB.getIDLocation()) {
            if (!bankDAO.isBankExists(bank.getName()) && locationDAO.isLocationFree(bank.getIDLocation())) {
                bankDAO.updateBank(bank);
            }
        } else if (bank.getName().equals(bankDB.getName())) {
            if (locationDAO.isLocationFree(bank.getIDLocation())) {
                bankDAO.updateBank(bank);
            }else {
                throw new InfoException("This Location already has Bank");
            }
        } else if (bank.getIDLocation() == bankDB.getIDLocation()) {
            if (!bankDAO.isBankExists(bank.getName())) {
                bankDAO.updateBank(bank);
            }else {
                throw new InfoException("This bank already exists");
            }
        }
    }

    public Bank getBankByLocation(Location location) throws InfoException {
        if (locationDAO.isLocationExists(location)) {
            return bankDAO.getBankByLocation(location);
        }else throw new InfoException("This location absent");

    }

    public void deleteBank( Bank bank) throws InfoException {
        if (bankDAO.isBankExists(bank.getName())) {
            List<Loan> loanList = loanDAO.getAllLoanByClient(bank.getId());
            List<Deposit> depositList = depositDAO.getAllDepositByClient(bank.getId());
            List<Investment> investmentList = investmentDAO.getAllInvestmentByClient(bank.getId());
            if (!loanList.isEmpty()) {
                loanList.forEach(loan -> loanDAO.deleteLoan(loan));
            }
            if (!depositList.isEmpty()) {
                depositList.forEach(deposit -> depositDAO.deleteDeposit(deposit));
            }
            if (!investmentList.isEmpty()) {
                investmentList.forEach(investment -> investmentDAO.deleteInvestment(investment));
            }
            loanList = loanDAO.getAllLoanInBank(bank);
            depositList = depositDAO.getAllDepositInBank(bank);
            investmentList = investmentDAO.getAllInvestmentInBank(bank);
            if (!loanList.isEmpty()) {
                loanList.forEach(loan -> loanDAO.deleteLoan(loan));
            }
            if (!depositList.isEmpty()) {
                depositList.forEach(deposit -> depositDAO.deleteDeposit(deposit));
            }
            if (!investmentList.isEmpty()) {
                investmentList.forEach(investment -> investmentDAO.deleteInvestment(investment));
            }
            bankDAO.deleteBank(bank);
        }else {
            throw new InfoException("This bank is absent");
        }
    }
}
