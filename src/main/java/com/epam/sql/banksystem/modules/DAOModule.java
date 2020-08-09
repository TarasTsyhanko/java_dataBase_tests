package com.epam.sql.banksystem.modules;

import com.epam.sql.banksystem.dao.*;
import com.epam.sql.banksystem.dao.databasedao.*;
import com.google.inject.AbstractModule;

public class DAOModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BankDAO.class).to(BankDataBaseDAO.class);
        bind(LoanDAO.class).to(LoanDataBaseDAO.class);
        bind(LocationDAO.class).to(LocationDataBaseDAO.class);
        bind(PersonDAO.class).to(PersonDataBaseDAO.class);
    }
}
