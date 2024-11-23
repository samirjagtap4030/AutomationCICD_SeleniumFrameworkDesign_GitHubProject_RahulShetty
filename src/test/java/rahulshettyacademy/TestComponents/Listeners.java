/* 
  just fail LoginErrorValidation() test - "instead Incorrect email or password." use "instead Incorrect email password" ..it will fail tcs
 (in video 177) when i run parallaly ,report is messed up (wrong),but when i run in series then report is correctly showing.for that we will see thread safe concept (in video 178)
Ignore-(remember: (in video 177) remove parallel=tests in xml file.so it will run in series otherwise it will give wrong test pass or fail.)
 
*/
package rahulshettyacademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{
	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //Thread safe
	@Override
	public void onTestStart(ITestResult result) {

		test = extent.createTest(result.getMethod().getMethodName());   // test entry is created dynamically (method name)
		extentTest.set(test);//unique thread id(ErrorValidationTest)->test
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		extentTest.get().log(Status.PASS, "Test Passed");
		
	}
	/*
	    first it will find test class(submitOrder),then it will find real class (its class method)(OrderHistoryTest) 
	    then some field is using driver,so get that field,(that field is part of class ,not method)...
	    (and that field will give life to driver for acreenshot),and that driver we will pass in getScreenshot()...
	    In this way we will get driver in getScreenshot() in parent class)
	    
	    Interview que-
	    i cannot use test method to get driver because fields are associated in class level..not method level
	 */

	@Override     
	public void onTestFailure(ITestResult result) {           // if tcs is fail then i will take ss 

		extentTest.get().fail(result.getThrowable());//fail test and print fail message in report
		                                             // (you can ignore try_catch here
		try {                                        // attach screenshots to report
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")   // this step is giving driver
					.get(result.getInstance());  
			
		} catch (Exception e1) {                       // instead of writing 4-5 exception ,we wrote Exception (parent class)...so you can delete other exceptions
			// TODO Auto-generated catch block
			e1.printStackTrace();// whatever you will get exception,we print it 
		}
		
		
		// attach screenshots to extent report
		String filePath = null; // initialise filePath var,initially it is null  
		try {
			
			filePath = getScreenshot(result.getMethod().getMethodName(),driver); // filepath where ss is saved(stored) in local system
		} catch (IOException e) {                                                // pass tcs name and driver in getScreenshot()
			// TODO Auto-generated catch block
			e.printStackTrace(); // if ss doesnot exist then it will print there is no path present
		}
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());// pass para filepath and same tcs name in addScreenCaptureFromPath() 
		
		
		//Screenshot, Attach to report
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();   // after complete of execution ,we flush it.so it will organize and consolidated report
		
	}
	
	
	
	

}
