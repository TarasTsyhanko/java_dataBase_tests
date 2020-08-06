package com.epam.sql.banksystem.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static com.epam.sql.banksystem.config.PropConstants.*;

public class DataProp {
    private Properties props = new Properties();
    public DataProp() {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE)){
            props.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String dbHost() {
        return props.getProperty(DB_HOST);
    }

    private int dbPort() {
        return Integer.parseInt(props.getProperty(DB_PORT));
    }

    public String dbName() {
        return props.getProperty(DB_NAME);
    }

    public String dbUser() {
        return props.getProperty(DB_USER);
    }

    public String dbPass() {
        return props.getProperty(DB_PASSWORD);
    }

    public String jdbcDriver() {
        return props.getProperty(JDBC_DRIVER);
    }

    public String sqlURL() {
        return String.format(props.getProperty(DB_URL), dbHost(), dbPort(), dbName());
    }
}
