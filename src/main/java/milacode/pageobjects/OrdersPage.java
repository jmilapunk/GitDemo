package milacode.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import milacode.AbstractComponents.AbstractComponent;

public class OrdersPage extends AbstractComponent{

	WebDriver driver;
	
	public OrdersPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tr td:nth-child(3)")
	List<WebElement> productNames;
	
	public boolean VerifyOrderDisplay(String productName) throws InterruptedException
	{
		waitforElementToAppear(By.cssSelector("tr td:nth-child(3)"));
		
		Boolean match = productNames.stream().anyMatch( product -> product.getText().equalsIgnoreCase(productName));
		return match;		
	}
}
