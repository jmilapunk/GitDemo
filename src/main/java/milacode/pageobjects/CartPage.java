package milacode.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import milacode.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//li//h3")
	List<WebElement> cartProducts;
	
	@FindBy(css = ".totalRow button")
	WebElement checkOutButton;
	
	By cartItemTitles = By.cssSelector(".cartSection h3");

	public boolean VerifyProductDisplay(String productName)
	{
		waitforElementToAppear(cartItemTitles);
		Boolean match = cartProducts.stream().anyMatch( cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;		
	}

	public CheckoutPage GoToCheckout() {

		checkOutButton.click();		
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}
	
}
