package com.epam.sql.banksystem.dao.databasedao;

import com.epam.sql.banksystem.dao.DepositDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Deposit;
import com.epam.sql.banksystem.config.mysql.MySQLClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.sql.banksystem.config.CrudOperationConstants.*;

public class DepositDataBaseDAO implements DepositDAO {
    private static Logger log = LogManager.getLogger(DepositDataBaseDAO.class);

    @Override
    public List<Deposit> getAllDeposit() {
        List<Deposit> depositList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_DEPOSIT)) {
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                depositList.add(getDepositFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return depositList;
    }

    @Override
    public List<Deposit> getAllDepositByClient(int IDClient) {
        List<Deposit> depositList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_DEPOSIT_BY_CLIENT)) {
            statement.setInt(1, IDClient);
            statement.setInt(2, IDClient);
           try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                depositList.add(getDepositFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return depositList;
    }

    @Override
    public List<Deposit> getAllDepositInBank( Bank bank) {
        List<Deposit> depositList = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_DEPOSIT_IN_ONE_BANK)) {
            statement.setString(1, bank.getName());
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                depositList.add(getDepositFromDB(resultSet));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return depositList;
    }

    @Override
    public Deposit getDepositByID(int depositID) {
        Deposit deposit = new Deposit();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_DEPOSIT_BY_ID)) {
            statement.setInt(1, depositID);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                deposit = getDepositFromDB(resultSet);
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return deposit;
    }

    @Override
    public void insertDeposit( Deposit deposit) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(INSERT_DEPOSIT)) {
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"0");
            statement.executeBatch();
            statement.setInt(1, deposit.getIDOperation());
            statement.setInt(2, deposit.getIDBank());
            statement.setInt(3, deposit.getIDClient());
            statement.setInt(4, deposit.getAmount());
            statement.setDouble(5, deposit.getPercent());
            statement.setString(6, deposit.getCurrency());
            statement.setDate(7, deposit.getDateIssue());
            statement.setDate(8, deposit.getDateReturn());
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
    public void updateDeposit( Deposit deposit) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(UPDATE_DEPOSIT)) {
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"0");
            statement.executeBatch();
            statement.setInt(1, deposit.getAmount());
            statement.setDouble(2, deposit.getPercent());
            statement.setString(3, deposit.getCurrency());
            statement.setDate(4, deposit.getDateIssue());
            statement.setDate(5, deposit.getDateReturn());
            statement.setInt(6, deposit.getIDOperation());
            statement.addBatch(SET_FOREIGN_KEY_CHECKS+"1");
            statement.executeUpdate();
            statement.executeBatch();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void deleteDeposit( Deposit deposit) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(DELETE_DEPOSIT)) {
            statement.setInt(1, deposit.getIDOperation());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    private Deposit getDepositFromDB( ResultSet resultSet) throws SQLException {
        Deposit deposit = new Deposit();
        deposit.setIDOperation(resultSet.getInt("IDDeposit"));
        deposit.setIDBank(resultSet.getInt("IDBank"));
        deposit.setIDClient(resultSet.getInt("IDClient"));
        deposit.setAmount(resultSet.getInt("Amount"));
        deposit.setPercent(resultSet.getDouble("Percent"));
        deposit.setCurrency(resultSet.getString("Currency"));
        deposit.setDateIssue(resultSet.getDate("Date_Issue"));
        deposit.setDateReturn(resultSet.getDate("Date_Return"));
        return deposit;
    }
}
