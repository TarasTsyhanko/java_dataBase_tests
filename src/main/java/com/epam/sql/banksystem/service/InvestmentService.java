package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.dao.BankDAO;
import com.epam.sql.banksystem.dao.InvestmentDAO;
import com.epam.sql.banksystem.dao.PersonDAO;
import com.epam.sql.banksystem.dao.databasedao.BankDataBaseDAO;
import com.epam.sql.banksystem.dao.databasedao.InvestmentDataBaseDAO;
import com.epam.sql.banksystem.dao.databasedao.PersonDataBaseDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Investment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InvestmentService {
    InvestmentDAO investmentDAO;
    BankDAO bankDAO;
    PersonDAO personDAO;

    public InvestmentService() {
        investmentDAO = new InvestmentDataBaseDAO();
        bankDAO = new BankDataBaseDAO();
        personDAO = new PersonDataBaseDAO();
    }

    public List<Investment> getAllInvestment() {
        return investmentDAO.getAllInvestment();
    }

    public List<Investment> getAllInvestmentInOneBank( Bank bank) {
        if (bankDAO.isBankExists(bank.getName())) {
            return investmentDAO.getAllInvestmentInBank(bank);
        }
        return new ArrayList<>();
    }

    public Investment insertInvestment(Investment investment) throws InfoException {
        if (investment.getIDBank() == investment.getIDClient()) {
            throw new InfoException("Incorrect investment");
        }
        investmentDAO.insertInvestment(investment);
        List<Investment> L = investmentDAO.getAllInvestmentByClient(investment.getIDClient())
                .stream().filter(investment1 -> investment1.hashCode() == investment.hashCode())
                .collect(Collectors.toList());
        return L.get(0);
    }

    public Investment updateInvestment(Investment investment) throws InfoException {
        Investment investmentDB = investmentDAO.getInvestmentByID(investment.getIDOperation());
        if (investmentDB.getIDBank() == investment.getIDBank() && investmentDB.getIDClient() == investment.getIDClient()) {
            investmentDAO.updateInvestment(investment);
        } else throw new InfoException("You can't change Bank or Client");
        return investmentDAO.getInvestmentByID(investment.getIDOperation());
    }

    public List<Investment> getAllInvestmentByClient(int IDClient) {
        return investmentDAO.getAllInvestmentByClient(IDClient);
    }

    public void deleteInvestment(Investment investment) {
        investmentDAO.deleteInvestment(investment);
    }


}
