package com.epam.sql.banksystem.dao.databasedao;

import com.epam.sql.banksystem.dao.InvestmentDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Investment;
import com.epam.sql.banksystem.config.mysql.MySQLClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.sql.banksystem.config.CrudOperationConstants.*;

public class InvestmentDataBaseDAO implements InvestmentDAO {
    private static Logger log = LogManager.getLogger(InvestmentDataBaseDAO.class);

    @Override
    public List<Investment> getAllInvestment() {
        List<Investment> investmentList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_INVESTMENT)) {
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                investmentList.add(getInvestmentFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return investmentList;
    }

    @Override
    public List<Investment> getAllInvestmentByClient(int IDClient) {
        List<Investment> investmentList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_INVESTMENT_BY_CLIENT)) {
            statement.setInt(1, IDClient);
            statement.setInt(2, IDClient);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                investmentList.add(getInvestmentFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return investmentList;
    }

    @Override
    public List<Investment> getAllInvestmentInBank( Bank bank) {
        List<Investment> investmentList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_INVESTMENT_IN_ONE_BANK)) {
            statement.setString(1, bank.getName());
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                investmentList.add(getInvestmentFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return investmentList;
    }

    @Override
    public Investment getInvestmentByID(int investmentID) {
        Investment investment = new Investment();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_INVESTMENT_BY_ID)) {
            statement.setInt(1, investmentID);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                investment = getInvestmentFromDB(resultSet);
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return investment;
    }

    @Override
    public void insertInvestment( Investment investment) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(INSERT_INVESTMENT)) {
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"0");
            statement.executeBatch();
            statement.setInt(1, investment.getIDOperation());
            statement.setInt(2, investment.getIDBank());
            statement.setInt(3, investment.getIDClient());
            statement.setInt(4, investment.getAmount());
            statement.setDouble(5, investment.getPercent());
            statement.setString(6, investment.getCurrency());
            statement.setDate(7, investment.getDateIssue());
            statement.setDate(8, investment.getDateReturn());
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"1");
            statement.executeUpdate();
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void updateInvestment( Investment investment) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(UPDATE_INVESTMENT)) {
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"0");
            statement.executeBatch();
            statement.setInt(1, investment.getAmount());
            statement.setDouble(2, investment.getPercent());
            statement.setString(3, investment.getCurrency());
            statement.setDate(4, investment.getDateIssue());
            statement.setDate(5, investment.getDateReturn());
            statement.setInt(6, investment.getIDOperation());
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"1");
            statement.executeUpdate();
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void deleteInvestment( Investment investment) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(DELETE_INVESTMENT)) {
            statement.setInt(1, investment.getIDOperation());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }


    private Investment getInvestmentFromDB( ResultSet resultSet) throws SQLException {
        Investment investment = new Investment();
        investment.setIDOperation(resultSet.getInt("IDInvestment"));
        investment.setIDBank(resultSet.getInt("IDBank"));
        investment.setIDClient(resultSet.getInt("IDClient"));
        investment.setAmount(resultSet.getInt("Amount"));
        investment.setPercent(resultSet.getDouble("Percent"));
        investment.setCurrency(resultSet.getString("Currency"));
        investment.setDateIssue(resultSet.getDate("Date_Issue"));
        investment.setDateReturn(resultSet.getDate("Date_Return"));
        return investment;
    }
}