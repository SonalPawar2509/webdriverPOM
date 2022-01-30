package com.vinted.common;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;

public class SafeActions {
	static WebDriverWait wait;
	
	public static void safeClick(WebDriver driver, WebElement element) {
		wait = new WebDriverWait(driver, Constants.NORMAL_WAIT);
		isElementVisible(driver, element);
		isElementClickable(driver, element);
		element.click();
		waitTime(1000);
	}
	
	public static void safeType(WebDriver driver, WebElement element,String textToEnter) {
		isElementVisible(driver, element);
		if(element.isEnabled())
		{
			element.clear();
			waitTime(1000);
			element.sendKeys(textToEnter);
			waitTime(1000);
		}
	}

	public static boolean isElementVisible(WebDriver driver, WebElement element) {
		wait = new WebDriverWait(driver, Constants.NORMAL_WAIT);
		try{
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		}
		catch(NoSuchElementException e) {
			Allure.step("Element not found: "+element.toString(), Status.FAILED);
			return false;
		}
		
	}

	public static boolean isElementClickable(WebDriver driver, WebElement element) {
		wait = new WebDriverWait(driver, Constants.NORMAL_WAIT);
		try{
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		}
		catch (Exception e) {
			return false;
		}
		
	}
	
	public static void waitTime(long timeUnit) {
		try {
			Thread.sleep(timeUnit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
