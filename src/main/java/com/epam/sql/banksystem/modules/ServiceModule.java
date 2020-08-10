package com.epam.sql.banksystem.modules;

import com.epam.sql.banksystem.service.BankService;
import com.epam.sql.banksystem.service.LoanService;
import com.epam.sql.banksystem.service.*;
import com.epam.sql.banksystem.service.PersonService;
import com.google.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BankService.class);
        bind(LoanService.class);
        bind(LocationService.class);
        bind(PersonService.class);
    }
}
