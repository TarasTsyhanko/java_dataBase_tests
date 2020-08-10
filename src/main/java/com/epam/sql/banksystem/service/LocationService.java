package com.epam.sql.banksystem.service;

import com.epam.sql.banksystem.dao.BankDAO;
import com.epam.sql.banksystem.dao.LocationDAO;
import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;
import com.google.inject.Inject;

import java.util.List;

import static com.epam.sql.banksystem.listeners.ExtentTestManager.*;

public class LocationService {
    @Inject
    private LocationDAO locationDAO;
    @Inject
    private BankDAO bankDAO;

    public List<Location> getAllLocation() {
        return locationDAO.getAllLocations();
    }

    public Location getLocation(Location location) {
        infoLog("try to get :" + location);
        if (locationDAO.isLocationExists(location)) {
            infoLog("location successfully fined");
            return locationDAO.getLocation(location);
        } else {
            infoLog("This Location absent");
            return null;
        }
    }

    public Location getLocationByBank(Bank bank) {
        infoLog("try to get location by: " + bank);
        if (bankDAO.isBankExists(bank.getName())) {
            infoLog("location successfully fined");
            return locationDAO.getLocationByBank(bank);
        } else {
            infoLog("This bank absent");
        }
        return null;
    }

    public void insertLocation(Location location) {
        infoLog("try to add: " + location);
        if (!locationDAO.isLocationExists(location)) {
            locationDAO.insertLocation(location);
            infoLog("location successfully added");
        } else {
            infoLog("This Location already exists");
        }
    }

    public void updateLocation(Location location) {
        infoLog("try to update: " + location);
        if (!locationDAO.isLocationExists(location)) {
            locationDAO.updateLocation(location);
            infoLog("location successfully updated");
        } else {
            infoLog("This Location already exists");
        }
    }

    public void deleteLocation(Location location) {
        infoLog("try to delete: " + location);
        if (locationDAO.isLocationExists(location)) {
            if (!bankDAO.getBankByLocation(location).equals(new Bank()))
                new BankService().deleteBank(bankDAO.getBankByLocation(location));
            locationDAO.deleteLocation(location);
            infoLog("location successfully deleted");
        }
    }
}
