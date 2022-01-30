package com.vinted.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Allure;

public class TestUtil extends TestBase {

	static JavascriptExecutor js;

	public static void takeScreenshot() {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			String filePath = currentDir + "/target/Screenshots/" + System.currentTimeMillis() + ".png";
			Allure.attachment("Screenshot: 1", new FileInputStream(scrFile));
			FileUtils.copyFile(scrFile, new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void takeScreenshotAtEndOfTest(String testName) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		String filePath = currentDir + "/target/Screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
		Allure.attachment(testName, new FileInputStream(scrFile));
		FileUtils.copyFile(scrFile, new File(filePath));
	}

	public static void scrollIntoView(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		waitTime(500);
	}

	public static void waitTime(long timeInMilliSeconds) {
		try {
			Thread.sleep(timeInMilliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void highLightElement(WebElement webElement) {
		String originalStyle = webElement.getAttribute("style");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", webElement, "style",
				originalStyle + "border: 2px solid red;");
		waitTime(50);
		// Reset style
		js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", webElement, "style", originalStyle);
	}

	/**
	 * 
	 * @param messageType
	 * @param message
	 * @throws InterruptedException
	 */
	public static void runTimeInfo(String messageType, String message) throws InterruptedException {
		js = (JavascriptExecutor) driver;
		js.executeScript("if (!window.jQuery) {"
				+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
				+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
				+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
		Thread.sleep(5000);

		js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

		js.executeScript("$('head').append('<link rel=\"stylesheet\" "
				+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");
		Thread.sleep(5000);

		js.executeScript("$.growl({ title: 'GET', message: '/' });");

		if (messageType.equals("error")) {
			js.executeScript("$.growl.error({ title: 'ERROR', message: '" + message + "' });");
		} else if (messageType.equals("info")) {
			js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
		} else if (messageType.equals("warning")) {
			js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
		} else
			System.out.println("no error message");
		Thread.sleep(5000);
	}

}
