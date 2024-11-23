// just create multiple tcs for testng.xml
// negative scenario

package rahulshettyacademy.tests;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("anshika@gmail.com", "Iamki000");
		Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());// correct-"Incorrect email or password."
	}                                                                                   // incorrect-"Incorrect email password."
	

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{
                                                                          // rahulshetty@gmail.com  Iamking@000
		String productName = "ZARA COAT 3";                              //    
		ProductCatalogue productCatalogue = landingPage.loginApplication("postman4075@gmail.com", "Hello123@");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	

	}

	
	

}
