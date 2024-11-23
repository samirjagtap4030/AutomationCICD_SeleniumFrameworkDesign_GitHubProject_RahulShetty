// AbstractComponent- Reusable component/methods(/code)
// Section 19 Framework Part 2 - Design Pattern - Page Object & factory Implementation
package rahulshettyacademy.AbstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.OrderPage;

public class AbstractComponent {
	
	WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		
		this.driver = driver;                    // it will initiate class driver variable
		PageFactory.initElements(driver, this);  // it will initiate findBy driver variable
		
	}
	
	@FindBy(css = "[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(css = "[routerlink*='myorders']")
	WebElement orderHeader;

/*	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
     wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
     List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
     
  // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
  // wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); 
     
      this is WebElement - driver.findElements(By.cssSelector(".mb-3")
      this is By locator - visibilityOfElementLocated(By.cssSelector(".mb-3")
                           (it is part of webelement but not webelement)
                           we write for By variable
     
     */                                                 
	public void waitForElementToAppear(By findBy) {     // visibilityOfElementLocated() -takes By locator(var)    
                                                        // invisibilityOf()- takes webelement as para  (driver.findElement(By.cssSelector(".ng-animating")))
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));

	}
	
	public void waitForElementToDisappear(WebElement ele) throws InterruptedException
	{                                       //invisibillity takes 4 sec ,or you can add thread.sleep here
		Thread.sleep(1000);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
//		wait.until(ExpectedConditions.invisibilityOf(ele));

	}
	
	public void waitForWebElementToAppear(WebElement findBy) { 

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(findBy));

	}
	
	public CartPage goToCartPage()// this 2 buttons are not exactly on cart page or order page.so we write in common
	{
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrderPage goToOrdersPage()
	{
		orderHeader.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
	

}
