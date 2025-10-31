package milacode.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import milacode.AbstractComponents.AbstractComponent;

public class ConfirmationOrderPage extends AbstractComponent{

	WebDriver driver;
	
	public ConfirmationOrderPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css=".hero-primary")
	WebElement confirmationMessage;
	
	By confirmationMessageBy = By.cssSelector(".hero-primary");

	public String ReturnConfirmationMessage() {
		waitforElementToAppear(confirmationMessageBy);
		return confirmationMessage.getText();
	}
}
