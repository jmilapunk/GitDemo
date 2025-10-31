package milacode.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import milacode.TestComponents.BaseTest;
import milacode.TestComponents.Retry;
import milacode.pageobjects.CartPage;
import milacode.pageobjects.CheckoutPage;
import milacode.pageobjects.ConfirmationOrderPage;
import milacode.pageobjects.LandingPage;
import milacode.pageobjects.ProductCatalogue;

public class ErrorValidations extends BaseTest {

	@Test(groups= {"ErrorHandling"}/*, retryAnalyzer=Retry.class*/)
	public void LoginErrorValidation() throws IOException
	{		
		ProductCatalogue productCatalogue = landingPage.loginApplication("jmila.ice2@gmail.com", "$Miej18496");
		String ErrorMessage = landingPage.getErrorMesssage();
		Assert.assertEquals(ErrorMessage, "Incorrect email or password.");		
	}
	
	@Test
	public void ProductErrorValidation() throws IOException
	{
		String productName = "ZARA COAT 3";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("jmila.ice@gmail.com", "$Miej18496");
		productCatalogue.addProductToCart(productName);

		CartPage cartPage = productCatalogue.goToCartPage();
		Assert.assertFalse(cartPage.VerifyProductDisplay("ZARA COAT 33"));
		
	}
}
