package com.epam.sql.banksystem.dao.databasedao;

import com.epam.sql.banksystem.config.mysql.MySQLClient;
import com.epam.sql.banksystem.dao.PersonDAO;
import com.epam.sql.banksystem.entity.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.sql.banksystem.config.CrudOperationConstants.*;

public class PersonDataBaseDAO implements PersonDAO {
    private static Logger log = LogManager.getLogger(PersonDataBaseDAO.class);

    @Override
    public List<Person> getAllPerson() {
        List<Person> personList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_PERSONS)) {
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                personList.add(getPersonFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return personList;
    }

    @Override
    public Person getPersonByName(String firstName, String lastName) {
        Person person = new Person();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_PERSON_BY_FIRST_NAME)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                person = getPersonFromDB(resultSet);
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return person;
    }
    @Override
    public Person getPersonById(int IdPerson) {
        Person person = new Person();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_PERSON_BY_ID)) {
            statement.setInt(1, IdPerson);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                person = getPersonFromDB(resultSet);
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return person;
    }

    @Override
    public void insertPerson( Person person) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(INSERT_PERSON)) {
            statement.setInt(1, person.getId());
            statement.setString(2, person.getFirstName());
            statement.setString(3, person.getLastName());
            statement.setString(4, person.getNationality());
            statement.setString(5, person.getCountry());
            statement.setString(6, person.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void updatePerson( Person person) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(UPDATE_PERSON)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getNationality());
            statement.setString(4, person.getCountry());
            statement.setString(5, person.getEmail());
            statement.setInt(6, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void deletePerson( Person person) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(DELETE_PERSON)) {
            statement.setInt(1, person.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }


    private Person getPersonFromDB( ResultSet resultSet) throws SQLException {
        Person person = new Person();
        person.setId(resultSet.getInt("IDPerson"));
        person.setFirstName(resultSet.getString("FirstName"));
        person.setLastName(resultSet.getString("LastName"));
        person.setNationality(resultSet.getString("Nationality"));
        person.setCountry(resultSet.getString("Country"));
        person.setEmail(resultSet.getString("Email"));
        return person;
    }

    public boolean isPersonExists(String firstName, String lastName) {
        String msg = "";
        try (CallableStatement statement = MySQLClient.getConnection().prepareCall("{call InsertPerson(?,?)}")) {
            statement.setString("FirstNamePersonIn", firstName);
            statement.setString("LastNameIn", lastName);
            try(ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                msg = rs.getString(1);
            }
            if(msg.equals("This Person already exists")){
                return true;
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        return false;
    }
}
