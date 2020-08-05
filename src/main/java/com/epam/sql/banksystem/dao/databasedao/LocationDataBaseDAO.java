package com.epam.sql.banksystem.dao.databasedao;

import com.epam.sql.banksystem.config.mysql.MySQLClient;
import com.epam.sql.banksystem.dao.LocationDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.sql.banksystem.config.CrudOperationConstants.*;

public class LocationDataBaseDAO implements LocationDAO {
    private static Logger log = LogManager.getLogger(LocationDataBaseDAO.class);

    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_ALL_LOCATION)) {
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Location location = new Location();
                location.setLocationID(resultSet.getInt("IDLocation"));
                location.setCountry(resultSet.getString("Country"));
                location.setCity(resultSet.getString("City"));
                location.setStreet(resultSet.getString("Street"));
                locations.add(location);
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return locations;
    }

    @Override
    public Location getLocationByBank( Bank bank) {
        Location location = new Location();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_LOCATION_BY_BANK)) {
            statement.setString(1, bank.getName());
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                location.setLocationID(resultSet.getInt("IDLocation"));
                location.setCountry(resultSet.getString("Country"));
                location.setCity(resultSet.getString("City"));
                location.setStreet(resultSet.getString("Street"));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return location;
    }
    public Location getLocation( Location location) {
        Location location1 = new Location();
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(SELECT_LOCATION)) {
            statement.setString(1, location.getCountry());
            statement.setString(2, location.getCity());
            statement.setString(3, location.getStreet());
            try(ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                location1.setLocationID(resultSet.getInt("IDLocation"));
                location1.setCountry(resultSet.getString("Country"));
                location1.setCity(resultSet.getString("City"));
                location1.setStreet(resultSet.getString("Street"));
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
        return location1;
    }

    @Override
    public void insertLocation( Location location) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(INSERT_LOCATION)) {
            statement.setInt(1, location.getLocationID());
            statement.setString(2, location.getCountry());
            statement.setString(3, location.getCity());
            statement.setString(4, location.getStreet());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void updateLocation( Location location) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(UPDATE_LOCATION)){
            statement.setString(1, location.getCountry());
            statement.setString(2, location.getCity());
            statement.setString(3, location.getStreet());
            statement.setInt(4,location.getLocationID());
            statement.executeUpdate();
        }catch (SQLException e){
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }

    @Override
    public void deleteLocation( Location location) {
        try (PreparedStatement statement = MySQLClient.getConnection().prepareStatement(DELETE_LOCATION)){
            statement.setFloat(1,location.getLocationID());
            statement.executeUpdate();
        }catch (SQLException e){
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        MySQLClient.closeConnection();
    }
    @Override
    public boolean isLocationFree(int IdLocation){
        String msg = "";
        try (CallableStatement statement = MySQLClient.getConnection().prepareCall("{call LocationStatus(?)}")) {
            statement.setInt("IDLocationIN", IdLocation);
            try(ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                msg = rs.getString(1);
            }
            if(msg.equals("This Location already has Bank")){
                return false;
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        return true;
    }
    public boolean isLocationExists( Location location){
        String msg = "";
        try (CallableStatement statement = MySQLClient.getConnection().prepareCall("{call LocationExists(?,?,?)}")) {
            statement.setString("CountryIN", location.getCountry());
            statement.setString("CityIN", location.getCity());
            statement.setString("StreetIN", location.getStreet());
            try(ResultSet rs = statement.executeQuery()){
            while (rs.next()) {
                msg = rs.getString(1);
            }
            if(msg.equals("This Location already exists")){
                return true;
            }}
        } catch (SQLException e) {
            log.error("SQLException: " + e.getMessage() +"\n"+"SQLState: " + e.getSQLState());
        }
        return false;
    }
}
