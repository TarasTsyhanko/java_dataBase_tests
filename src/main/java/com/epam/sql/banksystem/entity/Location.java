package com.epam.sql.banksystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Location {
    @JsonIgnore
    private int locationID;
    private String country;
    private String city;
    private String street;

    public Location() {
    }

    public Location( String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationID=" + locationID +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return  Objects.equals(country, location.country) &&
                Objects.equals(city, location.city) &&
                Objects.equals(street, location.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, street);
    }
}
