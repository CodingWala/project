package com.agoda.hotelSearch;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestCaseListener implements ITestListener {
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started : " + result.getName());

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Got Successful : " + result.getName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Got Failed : " + result.getName());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Got Skipped : " + result.getName());

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("Test Failed but within success percentage" + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("This is onStart method" + context.getOutputDirectory());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("This is onFinish method" + context.getPassedTests());
		System.out.println("This is onFinish method" + context.getFailedTests());
	}
}
