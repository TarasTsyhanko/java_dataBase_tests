package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.dao.*;
import com.epam.sql.banksystem.entity.*;
import com.google.inject.Inject;

import java.util.List;

import static com.epam.sql.banksystem.listeners.ExtentTestManager.infoLog;

public class BankService {
    @Inject
    private BankDAO bankDAO;
    @Inject
    private LocationDAO locationDAO;
    @Inject
    private LoanDAO loanDAO;

    public Bank getBankByName(String name) {
        infoLog("try to get bank by name: " + name);
        if (bankDAO.isBankExists(name)) {
            infoLog("bank successfully fined");
            return bankDAO.getBankByName(name);
        } else {
            infoLog("This bank is absent");
            return null;
        }
    }

    public List<Bank> getAllBanks() {
        return bankDAO.getAllBanks();
    }

    public void insertBank(Bank bank) {
        infoLog("try to add to dataBase bank : " + bank);
        if (!(bankDAO.isBankExists(bank.getName()))) {
            if (locationDAO.isLocationFree(bank.getIDLocation())) {
                bankDAO.insertBank(bank);
                infoLog("bank successfully added");
            } else {
                infoLog("This Location already has Bank");
            }
        } else {
            infoLog("This bank already exists");
        }
    }

    public void updateBank(Bank bank) {
        infoLog("try to update bank : " + bank);
        Bank bankDB = bankDAO.getBankByID(bank.getId());
        if (!bank.getName().equals(bankDB.getName()) && bank.getIDLocation() != bankDB.getIDLocation()) {
            if (!bankDAO.isBankExists(bank.getName()) && locationDAO.isLocationFree(bank.getIDLocation())) {
                bankDAO.updateBank(bank);
            }
        } else if (bank.getName().equals(bankDB.getName())) {
            if (locationDAO.isLocationFree(bank.getIDLocation())) {
                bankDAO.updateBank(bank);
            } else {
                infoLog("This Location already has Bank");
            }
        } else if (bank.getIDLocation() == bankDB.getIDLocation()) {
            if (!bankDAO.isBankExists(bank.getName())) {
                bankDAO.updateBank(bank);
            } else {
                infoLog("This bank already exists");
            }
        }
        infoLog("bank successfully updated");
    }

    public Bank getBankByLocation(Location location) {
        infoLog("try to get bank by :" + location);
        if (locationDAO.isLocationExists(location)) {
            infoLog("bank successfully fined");
            return bankDAO.getBankByLocation(location);
        } else infoLog("This location absent");
        return null;
    }

    public void deleteBank(Bank bank){
        infoLog("try to delete :" + bank);
        if (bankDAO.isBankExists(bank.getName())) {
            List<Loan> loanList = loanDAO.getAllLoanByClient(bank.getId());
            if (!loanList.isEmpty()) {
                loanList.forEach(loan -> loanDAO.deleteLoan(loan));
            }
            loanList = loanDAO.getAllLoanInBank(bank);
            if (!loanList.isEmpty()) {
                loanList.forEach(loan -> loanDAO.deleteLoan(loan));
            }
            bankDAO.deleteBank(bank);
            infoLog("bank successfully deleted");
        } else {
            infoLog("This bank is absent");
        }
    }
}
