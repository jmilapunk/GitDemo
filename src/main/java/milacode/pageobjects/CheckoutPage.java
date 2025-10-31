package milacode.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import milacode.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent{

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//button[contains(.,'Mexico')]")
	WebElement MexicoDropdownOption;
	
	@FindBy(css="input[placeholder='Select Country']")
	WebElement SelectCountry;
	
	@FindBy(xpath="//a[normalize-space()='Place Order']")
	WebElement PlaceOrderButton;

	By MexicoDropdownBy = By.xpath("//button[contains(.,'Mexico')]");
	By PlaceOrderBy = By.xpath("//a[normalize-space()='Place Order']");


	public void SelectMexicoCountry() {

		Actions a = new Actions(driver);
		a.sendKeys(SelectCountry, "Mexi").build().perform();
		waitforElementToBeClickable(MexicoDropdownBy);
		MexicoDropdownOption.click();
	}

	public ConfirmationOrderPage PlaceOrder() {
		waitforElementToAppear(PlaceOrderBy);
		PlaceOrderButton.click();
		ConfirmationOrderPage confirmationOrderPage = new ConfirmationOrderPage(driver);
		return confirmationOrderPage;
	}
}
