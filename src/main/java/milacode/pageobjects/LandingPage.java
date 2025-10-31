package milacode.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import milacode.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement emailField;
	
	@FindBy(id="userPassword")
	WebElement passwordField;
	
	@FindBy(id="login")
	WebElement submitButton;
	
	@FindBy(css="div[aria-label='Incorrect email or password.']")
	WebElement ErrorLogingToast;
	
	By submitButtonBy = By.id("login");

	public ProductCatalogue loginApplication(String email, String password)
	{
		waitforElementToAppear(emailField);
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		waitforElementToBeClickable(submitButtonBy);
		submitButton.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}

	public void goToLandinPage() {
		driver.get("https://rahulshettyacademy.com/client/#/auth/login");
	}
	
	public String getErrorMesssage()
	{
		waitforElementToAppear(ErrorLogingToast);
		return ErrorLogingToast.getText();
	}
}
