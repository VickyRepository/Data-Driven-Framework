package suites;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import utils.Utilities;

public class LoginTestCase extends TestBase {

	@BeforeMethod
	public static void doInit() throws FileNotFoundException, IOException, Exception {
		doInitialization();
		boolean executionMode = Utilities.isSkip("LoginTestCase");
		if (executionMode == false) {
			Assert.assertTrue(false);
		}
		initializeBrowser();
	}

	@Test(dataProvider = "getData")
	public void loginTestCase(String userName, String password, String dataType) throws IOException {
		webDriver.get(config.getProperty("applicatioURL"));
		Utilities.doLogin(userName, password);
		if (!isLoggedIn & dataType.equals("Y")) {
			System.err.println("Not Landed succesffuly on the Home page with valid credential");
			Utilities.getScreenshot("LoginFailureOne");
			Assert.assertTrue(false);
		} else if (isLoggedIn & dataType.equals("N")) {
			System.err.println("Landed on the Home page with invalid credential");
			Utilities.getScreenshot("LoginFailureTwo");
			Assert.assertTrue(false);
		}
	}

	@DataProvider
	public Object[][] getData() {

		return Utilities.getData("LoginData");
	}

	@AfterMethod
	public void tearDown() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}
}
