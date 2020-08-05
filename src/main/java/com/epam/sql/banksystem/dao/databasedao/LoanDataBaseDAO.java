package com.epam.sql.banksystem.dao.databasedao;

import com.epam.sql.banksystem.config.mysql.MySQLClient;
import com.epam.sql.banksystem.dao.LoanDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Loan;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.sql.banksystem.config.CrudOperationConstants.*;

public class LoanDataBaseDAO implements LoanDAO {
    private static Logger log = LogManager.getLogger(LoanDataBaseDAO.class);

    @Override
    public List<Loan> getAllLoan() {
        List<Loan> loanList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_LOAN)) {
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                loanList.add(getLoanFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return loanList;
    }

    @Override
    public List<Loan> getAllLoanByClient(int IDClient) {
        List<Loan> loanList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_LOAN_BY_CLIENT)) {
            statement.setInt(1, IDClient);
            statement.setInt(2, IDClient);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                loanList.add(getLoanFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return loanList;
    }

    @Override
    public List<Loan> getAllLoanInBank( Bank bank) {
        List<Loan> loanList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_LOAN_IN_ONE_BANK)) {
            statement.setString(1, bank.getName());
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                loanList.add(getLoanFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return loanList;
    }

    @Override
    public Loan getLoanByID(int loanID) {
        Loan loan = new Loan();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_LOAN_BY_ID)) {
            statement.setInt(1, loanID);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                loan = getLoanFromDB(resultSet);
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return loan;
    }

    @Override
    public void insertLoan( Loan loan) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(INSERT_LOAN)) {
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"0");
            statement.executeBatch();
            statement.setInt(1, loan.getIDOperation());
            statement.setInt(2, loan.getIDBank());
            statement.setInt(3, loan.getIDClient());
            statement.setInt(4, loan.getAmount());
            statement.setDouble(5, loan.getPercent());
            statement.setString(6,loan.getCurrency());
            statement.setDate(7,loan.getDateIssue());
            statement.setDate(8,loan.getDateReturn());
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"1");
            statement.executeUpdate();
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
            e.printStackTrace();
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void updateLoan( Loan loan) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(UPDATE_LOAN)) {
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"0");
            statement.executeBatch();
            statement.setInt(1, loan.getAmount());
            statement.setDouble(2, loan.getPercent());
            statement.setString(3,loan.getCurrency());
            statement.setDate(4,loan.getDateIssue());
            statement.setDate(5,loan.getDateReturn());
            statement.setInt(6, loan.getIDOperation());
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"1");
            statement.executeUpdate();
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void deleteLoan( Loan loan) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(DELETE_LOAN)){
            statement.setInt(1,loan.getIDOperation());
            statement.executeUpdate();
        }catch (SQLException e){
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    private Loan getLoanFromDB( ResultSet resultSet) throws SQLException {
        Loan loan = new Loan();
        loan.setIDOperation(resultSet.getInt("IDLoan"));
        loan.setIDBank(resultSet.getInt("IDBank"));
        loan.setIDClient(resultSet.getInt("IDClient"));
        loan.setAmount(resultSet.getInt("Amount"));
        loan.setPercent(resultSet.getDouble("Percent"));
        loan.setCurrency(resultSet.getString("Currency"));
        loan.setDateIssue(resultSet.getDate("Date_Issue"));
        loan.setDateReturn(resultSet.getDate("Date_Return"));
        return loan;
    }
     public boolean isClientHasAlreadyLoan(int IDClient, int IDBank){
         String msg ="";
         try (CallableStatement statement = MySQLClient.getConnection().prepareCall("{call TakeLoan(?,?)}")) {
             statement.setInt("IDClientIN", IDClient);
             statement.setInt("IDBankIN", IDBank);
             try(ResultSet rs = statement.executeQuery()){
             while (rs.next()) {
                 msg = rs.getString(1);
             }
             if(msg.equals("This Client already has LOAN in this Bank")){
                 log.error("This Client already has LOAN in this Bank"+ " IDClient: "+IDClient);
                 return true;
             }}
         } catch (SQLException e) {
             log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
         }
         return false;
     }
}
