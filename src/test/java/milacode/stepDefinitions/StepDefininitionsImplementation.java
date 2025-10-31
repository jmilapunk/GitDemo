package milacode.stepDefinitions;

import java.io.IOException;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import milacode.TestComponents.BaseTest;
import milacode.pageobjects.CartPage;
import milacode.pageobjects.CheckoutPage;
import milacode.pageobjects.ConfirmationOrderPage;
import milacode.pageobjects.LandingPage;
import milacode.pageobjects.ProductCatalogue;

public class StepDefininitionsImplementation extends BaseTest{
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationOrderPage confirmationOrderPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landend_on_Ecommerce_page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username, String password)
	{
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
    @When("^I add product (.+) to Cart$")
    public void I_add_productName_to_Cart(String productName)
    {
    	productCatalogue.addProductToCart(productName);
    }

    @When("^Checkout product (.+) and submit the order$")
    public void checkout_product_and_submit_order(String productName)
    {
		CartPage cartPage = productCatalogue.goToCartPage();
		Assert.assertTrue(cartPage.VerifyProductDisplay(productName));
		
		CheckoutPage checkoutPage = cartPage.GoToCheckout();
		checkoutPage.SelectMexicoCountry();
		
		confirmationOrderPage = checkoutPage.PlaceOrder();
    }

    @Then("{string} message is displayed on Confirmation Page")
    public void message_is_displayed_on_confirmation_page(String message)
    {
		Assert.assertEquals(confirmationOrderPage.ReturnConfirmationMessage(), message);
		driver.quit();
    }
    
    @Then("{string} message is displayed.")
    public void message_is_displayed(String expectedMessage) {
		String ErrorMessage = landingPage.getErrorMesssage();
		Assert.assertEquals(ErrorMessage, expectedMessage);	
		driver.quit();
    }
}
