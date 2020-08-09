package com.epam.sql.banksystem.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestManager {
    static ExtentReports extent = ExtentManager.getInstance();
    public static ExtentTest test;

    public static void endTest() {
        extent.flush();
    }

    public static ExtentTest startTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }

    public static void infoLog(String massage) {
        test.log(Status.INFO, massage);
    }

    public static void errorLog(String massage) {
        test.log(Status.ERROR, massage);
    }

    public static void skipLog(String massage) {
        test.log(Status.SKIP, massage);
    }
}
