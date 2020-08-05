package com.epam.sql.banksystem.config.parser;

import com.epam.sql.banksystem.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.epam.sql.banksystem.config.JSonParserConstants.*;

public class ParserJSon {
    private  ObjectMapper objectMapper;

    public ParserJSon() {
        objectMapper = new ObjectMapper();
    }

    public  List<Person> getPersons() {
        Person[] people = new Person[0];
        try { people = objectMapper.readValue(FileReaders.openFile( PERSON_JSON_FILE),Person[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(people);
    }

    public  List<Bank> getBanks(){
        Bank[] banks = new Bank[0];
        try { banks = objectMapper.readValue(FileReaders.openFile(BANK_JSON_FILE), Bank[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(banks);
    }

    public  List<Location> getLocations() {
        Location[] locations = new Location[0];
        try { locations = objectMapper.readValue(FileReaders.openFile(LOCATION_JSON_FILE), Location[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(locations);
    }

    public  List<Deposit> getDeposits() {
        Deposit[] deposits = new Deposit[0];
        try { deposits = objectMapper.readValue(FileReaders.openFile(DEPOSIT_JSON_FILE), Deposit[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(deposits);
    }

    public  List<Loan> getLoans() {
        Loan[] loans = new Loan[0];
        try { loans = objectMapper.readValue(FileReaders.openFile(LOAN_JSON_FILE), Loan[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(loans);
    }

    public  List<Investment> getInvestments() {
        Investment[] in = new Investment[0];
        try { in = objectMapper.readValue(FileReaders.openFile(INVESTMENT_JSON_FILE), Investment[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(in);
    }
}
