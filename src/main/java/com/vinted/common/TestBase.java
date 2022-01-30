package com.vinted.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization(String url) {
		String browserName = System.getProperty("browser");
		if(browserName==null)
			browserName="chrome";
		Allure.step("Launching the '"+browserName.toUpperCase()+"' Browser");
		if (browserName.equals("chrome")) {
			//System.setProperty("webdriver.chrome.driver", "src/test/resources/browsers/chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equals("edge")) {
			//System.setProperty("webdriver.edge.driver", "src/test/resources/browsers/msedgedriver.exe");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else if (browserName.equals("firefox")) {
			//System.setProperty("webdriver.firefox.driver", "src/test/resources/browsers/msedgedriver.exe");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		e_driver = new EventFiringWebDriver(driver);

		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(url);

	}
	
	public static String getURL() {
		String countryCode = System.getProperty("countryCode");
		if (countryCode==null)
			countryCode="uk";
		return prop.getProperty(countryCode+".url");
	}

	public static void closeBrowser() {
		Allure.step("Closing the browser");
		driver.quit();
	}
}
