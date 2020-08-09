package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.dao.LoanDAO;
import com.epam.sql.banksystem.dao.PersonDAO;
import com.epam.sql.banksystem.entity.Loan;
import com.epam.sql.banksystem.entity.Person;
import com.google.inject.Inject;

import java.util.List;

import static com.epam.sql.banksystem.listeners.ExtentTestManager.infoLog;

public class PersonService {
    @Inject
    private PersonDAO personDAO;
    @Inject
    private LoanDAO loanDAO;

    public List<Person> getAllPerson() {
        return personDAO.getAllPerson();
    }

    public Person getPersonByName(String firstName, String lastName) {
        if (personDAO.isPersonExists(firstName, lastName)) {
            return personDAO.getPersonByName(firstName, lastName);
        } else infoLog("This person is absent");
        return null;
    }

    public Person insertPerson(Person person) {
        if (!personDAO.isPersonExists(person.getFirstName(), person.getLastName())) {
            personDAO.insertPerson(person);
            return personDAO.getPersonByName(person.getFirstName(), person.getLastName());
        } else infoLog("This person already exists");
        return null;
    }

    public Person updatePerson(Person person) {
        Person personDB = personDAO.getPersonById(person.getId());
        if (person.getFirstName().equals(personDB.getFirstName()) && person.getLastName().equals(personDB.getLastName())) {
            personDAO.updatePerson(person);
        } else {
            if (personDAO.isPersonExists(person.getFirstName(), person.getLastName())) {
                infoLog("Person with this Name already exists");
            } else personDAO.updatePerson(person);
        }
        return personDAO.getPersonById(person.getId());
    }

    public void deletePerson(Person person) {
        if (personDAO.isPersonExists(person.getFirstName(), person.getLastName())) {
            List<Loan> loanList = loanDAO.getAllLoanByClient(person.getId());
            if (!loanList.isEmpty()) {
                loanList.forEach(loan -> loanDAO.deleteLoan(loan));
            }
            personDAO.deletePerson(person);
        } else infoLog("This person is absent");
    }
}
