package com.epam.sql.banksystem.dao;

import com.epam.sql.banksystem.entity.Bank;
import com.epam.sql.banksystem.entity.Location;

import java.util.List;

public interface LocationDAO {
    List<Location> getAllLocations();
    Location getLocationByBank(Bank bank);
    Location getLocation(Location location);
    void insertLocation(Location location);
    void updateLocation(Location location);
    void deleteLocation(Location location);
    boolean isLocationFree(int IdLocation);
    public boolean isLocationExists(Location location);
}
