package milacode.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import milacode.TestComponents.BaseTest;
import milacode.pageobjects.CartPage;
import milacode.pageobjects.CheckoutPage;
import milacode.pageobjects.ConfirmationOrderPage;
import milacode.pageobjects.LandingPage;
import milacode.pageobjects.OrdersPage;
import milacode.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest {

	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException
	{		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		productCatalogue.addProductToCart(input.get("product"));

		CartPage cartPage = productCatalogue.goToCartPage();
		Assert.assertTrue(cartPage.VerifyProductDisplay(input.get("product")));
		
		CheckoutPage checkoutPage = cartPage.GoToCheckout();
		checkoutPage.SelectMexicoCountry();
		
		ConfirmationOrderPage confirmationOrderPage = checkoutPage.PlaceOrder();
		Assert.assertEquals(confirmationOrderPage.ReturnConfirmationMessage(), "THANKYOU FOR THE ORDER.");
		
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest() throws InterruptedException
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("jmila.ice@gmail.com", "$Miej18496");
		OrdersPage ordersPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "\\src\\test\\java\\milacode\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
	
	//Extent Reports
	
//	@DataProvider
//	public Object[][] getData()
//	{
//	HashMap<String, String> map = new HashMap<String, String>();
//	map.put("email", "jmila.ice@gmail.com");
//	map.put("password", "$Miej18496");
//	map.put("product", "ZARA COAT 3");
//
//	HashMap<String, String> map1 = new HashMap<String, String>();
//	map1.put("email", "javierzuzuk@gmail.com");
//	map1.put("password", "$Exkorbuto1");
//	map1.put("product", "ADIDAS ORIGINAL");
	
//		return new Object[][] {{"jmila.ice@gmail.com", "$Miej18496", "ZARA COAT 3"},{"javierzuzuk@gmail.com", "$Exkorbuto1", "ADIDAS ORIGINAL"}};
//	}
}
