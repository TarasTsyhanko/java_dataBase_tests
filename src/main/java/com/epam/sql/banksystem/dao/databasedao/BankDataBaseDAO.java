package com.epam.sql.banksystem.dao.databasedao;

import com.epam.sql.banksystem.dao.BankDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;
import com.epam.sql.banksystem.config.mysql.MySQLClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epam.sql.banksystem.config.CrudOperationConstants.*;

public class BankDataBaseDAO implements BankDAO {
    private static Logger log = LogManager.getLogger(BankDataBaseDAO.class);
    @Override
    public List<Bank> getAllBanks() {
        List<Bank> banks = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_BANKS)) {
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Bank bank = new Bank();
                bank.setId(resultSet.getInt("IDBank"));
                bank.setName(resultSet.getString("BankName"));
                bank.setIDLocation(resultSet.getInt("IDLocation"));
                banks.add(bank);
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return banks;
    }

    @Override
    public Bank getBankByName(String bankName) {
        Bank bank = new Bank();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_BANK_BY_NAME)) {
            statement.setString(1,bankName);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                bank.setId(resultSet.getInt("IDBank"));
                bank.setName(resultSet.getString("BankName"));
                bank.setIDLocation(resultSet.getInt("IDLocation"));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return bank;
    }
    @Override
    public Bank getBankByID(int  IdBank) {
        Bank bank = new Bank();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_BANK_BY_ID)) {
            statement.setInt(1,IdBank);
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                bank.setId(resultSet.getInt("IDBank"));
                bank.setName(resultSet.getString("BankName"));
                bank.setIDLocation(resultSet.getInt("IDLocation"));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return bank;
    }

    public Bank getBankByLocation( Location location) {
        Bank bank = new Bank();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_BANK_BY_LOCATION)) {
            statement.setFloat(1,location.getLocationID());
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                bank.setId(resultSet.getInt("IDBank"));
                bank.setName(resultSet.getString("BankName"));
                bank.setIDLocation(resultSet.getInt("IDLocation"));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return bank;
    }

    @Override
    public void insertBank( Bank bank) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(INSERT_BANK)) {
            statement.setString(1, bank.getName());
            statement.setInt(2, bank.getIDLocation());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: " + Arrays.toString(e.getStackTrace()) +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void updateBank( Bank bank) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(UPDATE_BANK)){
            statement.setString(1,bank.getName());
            statement.setInt(2,bank.getIDLocation());
            statement.setInt(3,bank.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            log.error("SQLException: " + Arrays.toString(e.getStackTrace()) +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void deleteBank( Bank bank) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(DELETE_BANK)){
            statement.setInt(1,bank.getId());
            statement.executeUpdate();
        }catch (SQLException e){
            log.error("SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    public boolean isBankExists(String name) {
        String msg = "";
        try (CallableStatement statement = MySQLClient.getConnection().prepareCall("{call InsertBank(?)}")) {
            statement.setString("BankNameIn", name);
            try(ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                msg = rs.getString(1);
            }
            if(msg.equals("This bank already exists")){
                return true;
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        return false;
    }
}
