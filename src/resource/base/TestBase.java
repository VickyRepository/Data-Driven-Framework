package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;

import utils.Xls_Reader;

public class TestBase {

	public static Properties config;
	public static Properties objectRepo;
	public static WebDriver webDriver;
	public static boolean isLoggedIn; 
	public static Xls_Reader dataTable;

	public static void doInitialization() throws FileNotFoundException, IOException, Exception {

		config = new Properties();
		FileInputStream configFile = new FileInputStream(System.getProperty("user.dir") + "\\Configuration.properties");
		config.load(configFile);

		objectRepo = new Properties();
		FileInputStream ObectRepository = new FileInputStream(
				System.getProperty("user.dir") + "\\ObjectRepository.properties");
		objectRepo.load(ObectRepository);
		
	}

	public static WebDriver initializeBrowser() throws Exception {

		String browserType = config.getProperty("browser");

		switch (browserType.toLowerCase()) {

		case "chrome":
			System.setProperty("webdriver.chrome.driver", config.getProperty("chromeDriverPath"));
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			return webDriver = new ChromeDriver(options);

		case "firefox":
			System.setProperty("webdriver.gecko.driver", config.getProperty("geckoDriverPath"));
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.setCapability("marionette", true);
			return webDriver = new FirefoxDriver(firefoxOptions);

		case "ie":
			System.setProperty("webdriver.ie.driver", config.getProperty("ieDriverPath"));
			return webDriver = new InternetExplorerDriver();

		default:
			throw new Exception("Driver not found: " + browserType);
		}

	}

	public static WebElement getObject(String propertyValue) {

		try {
			String value = objectRepo.getProperty(propertyValue);
			String[] split = value.split("~");
			String locaterType = split[0];
			String locaterValue = split[1];

			switch (locaterType.toLowerCase()) {

			case "css":
				return webDriver.findElement(By.cssSelector(locaterValue));

			case "xpath":
				return webDriver.findElement(By.xpath(locaterValue));

			default:
				throw new Exception("locater not found: " + locaterType);
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}

	public static List<WebElement> getObjects(String propertyValue) {

		try {
			String value = objectRepo.getProperty(propertyValue);
			String[] split = value.split("~");
			String locaterType = split[0];
			String locaterValue = split[1];

			switch (locaterType.toLowerCase()) {

			case "css":
				return webDriver.findElements(By.cssSelector(locaterValue));

			case "xpath":
				return webDriver.findElements(By.xpath(locaterValue));

			default:
				throw new Exception("locaters not found: " + locaterType);
			}

		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
}
