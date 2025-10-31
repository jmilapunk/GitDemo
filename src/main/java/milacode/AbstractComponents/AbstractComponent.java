package milacode.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import milacode.pageobjects.CartPage;
import milacode.pageobjects.OrdersPage;
import org.openqa.selenium.JavascriptExecutor; // 👈 NUEVO
import org.openqa.selenium.ElementClickInterceptedException; // 👈 NUEVO
import org.openqa.selenium.StaleElementReferenceException; // 👈 NUEVO
import java.util.List; // 👈 NUEVO

public class AbstractComponent {
	
	WebDriver driver;
	
	public AbstractComponent(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css= "[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(xpath= "//button[@routerlink='/dashboard/myorders']")
	WebElement ordersHeader;
	
	By cartHeaderBy = By.cssSelector("[routerlink*='cart']");
	
    private By overlays = By.cssSelector("div.ngx-spinner-overlay"); // 👈 NUEVO


	public void waitforElementToAppear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(6));
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitforElementToAppear(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitforElementToBeClickable(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.elementToBeClickable(findBy));
	}
	
	public void waitforElementToDisappear(By findBy)
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findBy));
	}
	
	 // ================= NUEVO: esperar a que no haya overlays =================
    public void waitForOverlaysToDisappear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(d -> {
            List<WebElement> els = d.findElements(overlays);
            if (els.isEmpty()) return true;
            for (WebElement e : els) {
                try {
                    if (e.isDisplayed()) {
                        String op = e.getCssValue("opacity");
                        if (op == null || !"0".equals(op)) return false;
                    }
                } catch (StaleElementReferenceException ignore) {}
            }
            return true;
        });
        // Extra: por si hay fade con opacity:1
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(d ->
            (Boolean)((JavascriptExecutor)d).executeScript(
                "return !document.querySelector(\".ngx-spinner-overlay[style*='opacity: 1']\");"
            )
        );
    }
    
    
    // ================= NUEVO: click seguro con fallback =================
    private void scrollIntoViewCenter(WebElement el) {
        ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", el);
    }

    public void safeClick(By locator) {
        waitForOverlaysToDisappear(); // quita el overlay si está activo
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoViewCenter(el);
        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

	public CartPage goToCartPage()
	{
		waitforElementToAppear(cartHeader);
//		waitforElementToBeClickable(cartHeaderBy);
		safeClick(cartHeaderBy);
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrdersPage goToOrdersPage()
	{
		waitforElementToAppear(ordersHeader);
		ordersHeader.click();
		OrdersPage ordersPage = new OrdersPage(driver);
		return ordersPage;
	}
}
