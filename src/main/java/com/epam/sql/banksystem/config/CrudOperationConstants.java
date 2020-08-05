package com.epam.sql.banksystem.config;

public class CrudOperationConstants {
    public static final String SELECT_ALL_BANKS = "SELECT * FROM Bank";
    public static final String SELECT_BANK_BY_NAME = "SELECT * FROM Bank WHERE BankName=?";
    public static final String SELECT_BANK_BY_ID = "SELECT * FROM Bank WHERE IDBank=?";
    public static final String SELECT_BANK_BY_LOCATION = "SELECT * FROM Bank WHERE IDLocation=?";
    public static final String INSERT_BANK = "INSERT INTO Bank ( BankName, IDLocation) VALUE(?,?)";
    public static final String UPDATE_BANK = "UPDATE Bank SET BankName=?, IDLocation=? WHERE IDBank=?";
    public static final String DELETE_BANK = "DELETE FROM Bank WHERE IDBank=?";

    public static final String SELECT_ALL_PERSONS = "SELECT * FROM Person";
    public static final String SELECT_PERSON_BY_FIRST_NAME = "SELECT * FROM Person WHERE FirstName=? AND LastName=?";
    public static final String SELECT_PERSON_BY_ID = "SELECT * FROM Person WHERE IDPerson=?";
    public static final String INSERT_PERSON = "INSERT INTO Person (IDPerson,FirstName,LastName,Nationality,Country,Email) VALUE(?,?,?,?,?,?)";
    public static final String UPDATE_PERSON = "UPDATE Person SET FirstName=?, LastName=?,Nationality=?, Country=?,Email=? WHERE IDPerson=?";
    public static final String DELETE_PERSON = "DELETE FROM Person WHERE IDPerson=?";

    public static final String SELECT_ALL_LOCATION = "SELECT * FROM Location";
    public static final String SELECT_LOCATION_BY_BANK = "SELECT l.IDLocation,Country,City,Street FROM Location l JOIN Bank b WHERE b.IDLocation=l.IDLocation AND b.BankName=?;";
    public static final String SELECT_LOCATION = "SELECT * FROM Location WHERE Country=? AND City=? AND Street=?";
    public static final String INSERT_LOCATION = "INSERT INTO Location (IDLocation, Country, City, Street) VALUE(?,?,?,?)";
    public static final String UPDATE_LOCATION = "UPDATE Location SET Country=?, City=?, Street=? WHERE IDLocation=?";
    public static final String DELETE_LOCATION = "DELETE FROM Location WHERE IDLocation=?";

    public static final String SELECT_ALL_DEPOSIT = "SELECT * FROM Deposit";
    public static final String SELECT_ALL_DEPOSIT_BY_CLIENT = "SELECT distinct IDDeposit,d.IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return FROM Deposit d JOIN Person p, Bank b WHERE p.IDPerson=d.IDClient AND p.IDPerson=? OR b.IDBank=d.IDClient AND b.IDBank=?";
    public static final String SELECT_ALL_DEPOSIT_IN_ONE_BANK = "SELECT IDDeposit,d.IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return FROM Deposit d JOIN Bank b WHERE b.IDBank=d.IDBank AND b.BankName=?";
    public static final String SELECT_DEPOSIT_BY_ID="SELECT * FROM Deposit WHERE IDDeposit=?";
    public static final String INSERT_DEPOSIT= "INSERT INTO Deposit (IDDeposit,IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return) VALUE(?,?,?,?,?,?,?,?)";
    public static final String UPDATE_DEPOSIT= "UPDATE Deposit SET Amount=?,Percent=?,Currency=?,Date_Issue=?,Date_Return=? WHERE IDDeposit=?";
    public static final String DELETE_DEPOSIT= "DELETE FROM Deposit WHERE IDDeposit=?";

    public static final String SELECT_ALL_LOAN = "SELECT * FROM Loan";
    public static final String SELECT_ALL_LOAN_BY_CLIENT = "SELECT distinct IDLoan,d.IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return FROM Loan d JOIN Person p, Bank b WHERE p.IDPerson=d.IDClient AND p.IDPerson=? OR b.IDBank=d.IDClient AND b.IDBank=?";
    public static final String SELECT_ALL_LOAN_IN_ONE_BANK = "SELECT IDLoan,d.IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return FROM Loan d JOIN Bank b WHERE b.IDBank=d.IDBank AND b.BankName=?";
    public static final String SELECT_LOAN_BY_ID="SELECT * FROM Loan WHERE IDLoan=?";
    public static final String INSERT_LOAN= "INSERT INTO Loan (IDLoan,IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return) VALUE(?,?,?,?,?,?,?,?)";
    public static final String UPDATE_LOAN= "UPDATE Loan SET Amount=?,Percent=?,Currency=?,Date_Issue=?,Date_Return=? WHERE IDLoan=?";
    public static final String DELETE_LOAN= "DELETE FROM Loan WHERE IDLoan=?";

    public static final String SELECT_ALL_INVESTMENT = "SELECT * FROM Investment";
    public static final String SELECT_ALL_INVESTMENT_BY_CLIENT = "SELECT distinct IDInvestment,d.IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return FROM Investment d JOIN Person p, Bank b WHERE p.IDPerson=d.IDClient AND p.IDPerson=? OR b.IDBank=d.IDClient AND b.IDBank=?";
    public static final String SELECT_ALL_INVESTMENT_IN_ONE_BANK = "SELECT IDInvestment,d.IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return FROM Investment d JOIN Bank b WHERE b.IDBank=d.IDBank AND b.BankName=?";
    public static final String SELECT_INVESTMENT_BY_ID="SELECT * FROM Investment WHERE IDInvestment=?";
    public static final String INSERT_INVESTMENT= "INSERT INTO Investment (IDInvestment,IDBank,IDClient,Amount,Percent,Currency,Date_Issue,Date_Return) VALUE(?,?,?,?,?,?,?,?)";
    public static final String UPDATE_INVESTMENT= "UPDATE Investment SET Amount=?,Percent=?,Currency=?,Date_Issue=?,Date_Return=? WHERE IDInvestment=?";
    public static final String DELETE_INVESTMENT= "DELETE FROM Investment WHERE IDInvestment=?";

    public static final String SET_FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS =";



}
