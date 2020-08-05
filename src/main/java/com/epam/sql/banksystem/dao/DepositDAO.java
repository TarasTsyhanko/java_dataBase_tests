package com.epam.sql.banksystem.dao;

import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Deposit;

import java.util.List;

public interface DepositDAO {
    List<Deposit> getAllDeposit();
    List<Deposit> getAllDepositByClient(int IDClient);
    List<Deposit> getAllDepositInBank(Bank bank);
    Deposit getDepositByID(int depositID);
    void insertDeposit(Deposit deposit);
    void updateDeposit(Deposit deposit);
    void deleteDeposit(Deposit deposit);
}
