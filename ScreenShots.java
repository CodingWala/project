package com.agoda.hotelSearch;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShots {

	public static void captureScreenShot(WebDriver driver)
		{
		// Take screenshot and store as a file format             
		 File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);           
			try {
			// now copy the  screenshot to desired location using copyFile method
			 
			FileUtils.copyFile(src, new File(".ScreenShots/priceRange.png"));                              
			} catch (Exception e)
			{
			  System.out.println(e.getMessage());
			}
		}
		 
}

