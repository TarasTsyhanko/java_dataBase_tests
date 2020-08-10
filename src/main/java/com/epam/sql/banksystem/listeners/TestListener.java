package com.epam.sql.banksystem.listeners;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

import static com.epam.sql.banksystem.listeners.ExtentTestManager.*;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        endTest();
    }

    @Override
    public void onTestStart(ITestResult result) {
        startTest(result.getMethod().getMethodName());
        test.getModel().setDescription("Test description: "+result.getMethod().getDescription());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.test.log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        errorLog(result.getThrowable().getMessage());
        ExtentTestManager.test.log(Status.FAIL, "Test Failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skipLog(Arrays.toString(result.getThrowable().getStackTrace()));
        ExtentTestManager.test.log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("*** Test failed but within percentage % " + result.getMethod().getMethodName());
    }

}
