package utils;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import base.TestBase;

public class Utilities extends TestBase {

	public static void doLogin(String userNameData, String passwordData) {

		WebElement userName = getObject("username");
		userName.sendKeys(userNameData);
		WebElement password = getObject("password");
		password.sendKeys(passwordData);
		WebElement loginButton = getObject("loginButton");
		loginButton.click();

		try {
			if (webDriver.switchTo().alert() != null) {
				webDriver.switchTo().alert().accept();
			}
		} catch (Exception e) {
			e.getMessage();
		}

		if ((getObjects("logoutButton")).size() != 0) {

			isLoggedIn = true;
		} else {
			isLoggedIn = false;
		}
	}

	public static boolean isSkip(String testCaseName) {

		for (int row_Num = 2; row_Num <= dataTable.getRowCount("TestCase"); row_Num++) {

			if (dataTable.getCellData("TestCase", "TestCaseID", row_Num).equalsIgnoreCase(testCaseName))
				if (dataTable.getCellData("TestCase", 2, row_Num).equalsIgnoreCase("y")) {
					return true;
				} else {
					return false;
				}
		}
		return false;
	}

	public static String[][] getData(String sheetName) {

		dataTable = new Xls_Reader(System.getProperty("user.dir") + "\\src\\resource\\input\\TestSheet.xlsx");
		int rowCount = dataTable.getRowCount(sheetName);
		int columnCount = dataTable.getColumnCount(sheetName);

		String[][] data = new String[rowCount - 1][columnCount];

		for (int row_Num = 2; row_Num <= rowCount; row_Num++) {
			for (int col_Num = 0; col_Num < columnCount; col_Num++) {
				data[row_Num - 2][col_Num] = dataTable.getCellData(sheetName, col_Num, row_Num);
			}
		}

		return data;
	}

	public static void getScreenshot(String fileName) throws IOException {
		
		File src = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		FileHandler.copy(src, new File(System.getProperty("user.dir")+"\\src\\resource\\output\\"+fileName+".png"));
	}
}
