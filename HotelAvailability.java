package com.agoda.hotelSearch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


public class HotelAvailability {
	
WebDriver driver;
String url="https://www.agoda.com/en-in/?cid=1844104";

public void setupBrowser() {
	
	
	driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
	driver.navigate().to(url);
}

@AfterMethod
public void closeBrowser()
{
	driver.quit();
}

@Test(priority = 1)
public void testRunChrome() {
	 System.setProperty("webdriver.chrome.driver", "C:\\Users\\Nikhil\\eclipse-workspace\\Mini Project Hotel Availabilty\\test\\resources\\Driver\\chromedriver.exe");
     driver=new ChromeDriver();
     automationTest();
}

//@Test(priority = 2)
public void testRunFireFox() {
	 System.setProperty("webdriver.gecko.driver","C:\\Users\\Nikhil\\eclipse-workspace\\Mini Project Hotel Availabilty\\test\\resources\\Driver\\geckodriver.exe");	
	 driver = new FirefoxDriver();
	 automationTest();
}

//@Test(priority = 3)
public void testRunEdge() {
	 System.setProperty("webdriver.edge.driver","C:\\Users\\Nikhil\\eclipse-workspace\\Mini Project Hotel Availabilty\\test\\resources\\Driver\\msedgedriver.exe");
	 driver = new EdgeDriver();
	 automationTest();
}

//@Test(priority = 4)
public void testRunInternetExplorer() {
	 System.setProperty("webdriver.ie.driver","C:\\Users\\Nikhil\\eclipse-workspace\\Mini Project Hotel Availabilty\\test\\resources\\Driver\\IEDriverServer.exe");
     driver = new InternetExplorerDriver();
	 automationTest();
}

public void setLocation(String location) {
	
	//Loction Selection
	driver.findElement(By.className("IconBox__child")).click();
	WebElement destination = driver.findElement(By.cssSelector(".IconBox__child > input"));
	if (!destination.getAttribute("value").equalsIgnoreCase(location)) {
		destination.clear();
		destination.sendKeys(location);
	}
	
	//driver.findElement(By.xpath("//span[text()='City']")).click();
	
	List <WebElement>list= driver.findElements(By.xpath("//*[@class='Suggestion Suggestion__categoryName']"));
	list.get(0).click();
	
	JavascriptExecutor jsExecutor=(JavascriptExecutor)driver;
	jsExecutor.executeScript("window.scrollBy(0,200)");
}

public void selectDate()
{	
	//****waitForPageLoad();
	//Date Selection
	try {
		//Calling method to generate random checkIn and CheckOut dates in next week with reference to system current date 
		DateSelection.generateDates();
	} catch (Exception e) {
		e.printStackTrace();
	}
	//****waitForPageLoad();
	List <WebElement> dates=driver.findElements(By.xpath("//*[contains(@class,'DayPicker-Day')][ @aria-disabled='false']"));
	//Selecting checkIn date
	try {
		for (WebElement date : dates) {
			String valueString=date.getAttribute("aria-label");
			if(valueString.equalsIgnoreCase(DateSelection.checkIn_Date)) {
			  date.click();
			  break; }
			 
		}
		
		//****waitForPageLoad();
		List <WebElement> Lists=driver.findElements(By.xpath("//*[contains(@class,'DayPicker-Day')][ @aria-disabled='false']"));	
	
		//Selecting checkout date
		for (WebElement element : Lists)
		{	
			String text=element.getAttribute("aria-label");
			if(text.equalsIgnoreCase(DateSelection.checkOut_Date)) {
				element.click();
				break;
			}
		}
	
	} catch (StaleElementReferenceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public void searchHotels(String category)
{
	 int no_of_guest;
	 int no_of_rooms;
	//Tourist booking requirement selection
	driver.findElement(By.xpath("//*[text()='"+category+"']")).click();
	
	if(category.equalsIgnoreCase("Solo traveler")) {
		no_of_guest=1;
		no_of_rooms=1;
	}
	else if(category.equalsIgnoreCase("Couple/Pair")) {
		no_of_guest=2;
		no_of_rooms=1;
	}
	else {
		no_of_guest=Integer.parseInt(ExcelDataConfig.excelData[3]);
		no_of_rooms=Integer.parseInt(ExcelDataConfig.excelData[2]);
	}
	
	for(int i=1;i<no_of_guest;i++)
		driver.findElement(By.xpath("(//*[@data-selenium='plus'])[2]")).click();
	
	for(int i=1;i<no_of_rooms;i++)
		driver.findElement(By.xpath("(//*[@data-selenium='plus'])[1]")).click();
	
	//Search hotels
	driver.findElement(By.cssSelector("[class$='active']")).click();
	//****waitForPageLoad();
		
}

public void setPriceRange(String minPrice,String maxPrice)
{
	//Price range selection between 2000 and 3000
	WebDriverWait wait=new WebDriverWait(driver, 30);
	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Best match']"))));
	driver.findElement(By.xpath("//span[text()='Your budget']")).click();
	
	WebElement min_Price=driver.findElement(By.id("price_box_0"));
	validate(min_Price, minPrice);
	
	WebElement max_Price=driver.findElement(By.id("price_box_1"));
	validate(max_Price, maxPrice);
	max_Price.sendKeys(Keys.ENTER);
	
	driver.findElement(By.xpath("//span[contains(text(),'Rs. "+minPrice+" - Rs. "+maxPrice+"')]")).click();
	driver.findElement(By.xpath("//*[name()='svg' and @class='sc-fzplgP KPytj']")).click();
}
    
public void countHotels()
{	
	ScreenShots.captureScreenShot(driver);
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//List of all the hotels Available
    String choices=	driver.findElement(By.xpath("//*[@data-selenium='SearchBoxTextDescriptionDesc']")).getText();
    String [] hotelsAvailable=choices.split(" ");
	System.out.println("Number of Hotels Available : "+hotelsAvailable[0]);
	
}

//Method to add custom wait for page loading and jQuery load 
public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete"))
				break;
			else
				waitLoad(1);
		}
		
		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState)
				break;
			else
				waitLoad(1);
		}
	}
	
public static void waitLoad(int i) 
	{
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

public static void validate(WebElement element,String value) 
	{
		while(!element.getAttribute("value").equalsIgnoreCase(value)) {
			element.clear();
			element.sendKeys(value);
			waitLoad(2);
		}
		return;
	}



public void automationTest()
{	
	
	String [] excelData=ExcelDataConfig.readExcelData();
	//Method to set Browser Properties
	setupBrowser() ;
	//Method to set preferred location
	setLocation(excelData[0]);
	//Method to generate random checkIn and checkOut dates in next week with reference to system current date for two days stay.
	selectDate();
	//Method to set room preference and search
	searchHotels(excelData[1]);
	//Method to set price range for hotels one day stay
	setPriceRange(excelData[4],excelData[5]);
	//Method to display the total count of available hotels
	countHotels();
        
}



}






