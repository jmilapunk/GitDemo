package milacode.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import milacode.pageobjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		// properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\milacode\\resources\\GlobalData.properties");
		prop.load(fis);
		
		String browserName = System.getProperty("browser")!= null ? System.getProperty("browser") : prop.getProperty("browser");

		 if (browserName.equalsIgnoreCase("chrome")) {
		        WebDriverManager.chromedriver().setup();
		        ChromeOptions options = new ChromeOptions();
		        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		        driver = new ChromeDriver(options);

		    } else if (browserName.equalsIgnoreCase("firefox")) {
		        WebDriverManager.firefoxdriver().setup();
		        FirefoxOptions options = new FirefoxOptions();
		        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		        driver = new FirefoxDriver(options);

		    } else if (browserName.equalsIgnoreCase("edge")) {
//		        WebDriverManager.edgedriver().setup();
//		        EdgeOptions options = new EdgeOptions();
//		        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
//		        driver = new EdgeDriver(options);

		    } else {
		        throw new IllegalArgumentException("Navegador no soportado: " + browserName);
		    }

		driver.manage().window().maximize(); 

		return driver;
	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goToLandinPage();
		driver.manage().deleteAllCookies();
	    ((JavascriptExecutor) driver).executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		// String to HashMap Jackson Databind
		ObjectMapper mapper = new ObjectMapper();

		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;
	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
	    TakesScreenshot ts = (TakesScreenshot) driver;
	    File source = ts.getScreenshotAs(OutputType.FILE);

	    // ✅ Ruta bien armada FUERA del getProperty
	    String filePath = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
	    
	    File file = new File(filePath);
	    FileUtils.copyFile(source, file);

	    return filePath;
	}

}
