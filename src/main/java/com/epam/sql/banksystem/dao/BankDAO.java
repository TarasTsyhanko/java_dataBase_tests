package com.epam.sql.banksystem.dao;

import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;

import java.util.List;

public interface BankDAO {
    List<Bank> getAllBanks();
    Bank getBankByName(String bankName);
    Bank getBankByLocation(Location location);
    public Bank getBankByID(int IdBank);
    void insertBank(Bank bank);
    void updateBank(Bank bank);
    void deleteBank(Bank bank);
    public boolean isBankExists(String name);
}
