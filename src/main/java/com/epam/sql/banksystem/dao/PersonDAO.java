package com.epam.sql.banksystem.dao;

import com.epam.sql.banksystem.entity.Person;

import java.util.List;

public interface PersonDAO {
    List<Person> getAllPerson();
    Person getPersonByName(String firstName, String lastName);
    Person getPersonById(int IdPerson);
    void insertPerson(Person person);
    void updatePerson(Person person);
    void deletePerson(Person person);
    public boolean isPersonExists(String firstName, String lastName);
}
