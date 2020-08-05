package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.config.exception.InfoException;
import com.epam.sql.banksystem.dao.BankDAO;
import com.epam.sql.banksystem.dao.DepositDAO;
import com.epam.sql.banksystem.dao.PersonDAO;
import com.epam.sql.banksystem.dao.databasedao.BankDataBaseDAO;
import com.epam.sql.banksystem.dao.databasedao.DepositDataBaseDAO;
import com.epam.sql.banksystem.dao.databasedao.PersonDataBaseDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Deposit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepositService {
    DepositDAO depositDAO;
    BankDAO bankDAO;
    PersonDAO personDAO;

    public DepositService() {
        depositDAO = new DepositDataBaseDAO();
        bankDAO = new BankDataBaseDAO();
        personDAO = new PersonDataBaseDAO();
    }

    public List<Deposit> getAllDeposit() {
        return depositDAO.getAllDeposit();
    }

    public List<Deposit> getAllDepositInOneBank( Bank bank) {
        if (bankDAO.isBankExists(bank.getName())) {
            return depositDAO.getAllDepositInBank(bank);
        }
        return new ArrayList<>();
    }
    public Deposit updateDeposit(Deposit deposit) throws InfoException {
        Deposit depositDB = depositDAO.getDepositByID(deposit.getIDOperation());
        if(depositDB.getIDBank()==deposit.getIDBank()&&depositDB.getIDClient()==deposit.getIDClient()){
            depositDAO.updateDeposit(deposit);
        }else  throw new InfoException("You can't change Bank or Client");
        return depositDAO.getDepositByID(deposit.getIDOperation());
    }

    public List<Deposit> getAllDepositByClient(int IDClient) {
        return depositDAO.getAllDepositByClient(IDClient);
    }

    public Deposit insertDeposit(Deposit deposit) throws InfoException {
        if(deposit.getIDBank()==deposit.getIDClient()){
            throw new InfoException("Incorrect deposit");
        }
        depositDAO.insertDeposit(deposit);
        List<Deposit> L = depositDAO.getAllDepositByClient(deposit.getIDClient())
                .stream().filter(deposit1 -> deposit1.hashCode() == deposit.hashCode())
                .collect(Collectors.toList());
        return L.get(0);
    }

    public void deleteDeposit(Deposit deposit) {
        depositDAO.deleteDeposit(deposit);
    }
}
