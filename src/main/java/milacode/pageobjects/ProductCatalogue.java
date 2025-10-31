package milacode.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import milacode.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(css=".mb-3")
	List<WebElement> productsList;
	
	By productsBy = By.cssSelector(".mb-3");
	By addToCartBy = By.cssSelector(".card-body button:last-of-type");
	By toastMessageBy = By.cssSelector("#toast-container");
	By loadAnimationBy = By.cssSelector(".ng-animating");
	By spinnerBy = By.cssSelector(".ngx-spinner-overlay");

	
	public List<WebElement> getProductList() {
		
		waitforElementToAppear(productsBy);
		return productsList;
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement productReturned = getProductList().stream().filter(product-> product.findElement(By.tagName("b")).getText().equals(productName)).findFirst().orElse(null);
		return productReturned;
	}
	
	public void addProductToCart(String productName)
	{
		WebElement product = getProductByName(productName);
		product.findElement(addToCartBy).click();
		waitforElementToAppear(toastMessageBy);
		waitforElementToDisappear(loadAnimationBy);
		waitforElementToDisappear(spinnerBy);
	}

}
