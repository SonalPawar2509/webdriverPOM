package com.vinted.pages;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.vinted.common.SafeActions;
import com.vinted.common.TestBase;
import com.vinted.common.TestUtil;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;

public class CataloguePage extends TestBase {
	public static Logger logger = Logger.getLogger(CataloguePage.class);

	@FindBy(xpath = "(//div[@role='presentation']//button)[1]") // div[@role='presentation']//button[normalize-space()='Catalogue']
	private WebElement catalogueDropdown;

	@FindBy(xpath = "(//div[@role='presentation']//button)[2]") // div[@role='presentation']//button[normalize-space()='Size']
	private WebElement sizeDropdown;

	@FindBy(xpath = "(//div[@role='presentation']//button)[3]") // div[@role='presentation']//button[normalize-space()='Colour']
	private WebElement colourDropdown;

	@FindBy(xpath = "(//div[@role='presentation']//button)[4]") // div[@role='presentation']//button[normalize-space()='Brand']
	private WebElement brandDropdown;

	@FindBy(xpath = "(//div[@role='presentation']//button)[5]") // div[@role='presentation']//button[normalize-space()='Price']
	private WebElement priceDropdown;

	@FindBy(xpath = "(//div[@role='presentation']//button)[6]") // div[@role='presentation']//button[normalize-space()='Condition']
	private WebElement conditionDropdown;

	@FindBy(xpath = "(//div[@role='presentation']//button)[7]") // div[@role='presentation']//button[normalize-space()='Swap']
	private WebElement swapDropdown;

	@FindBy(css = "input[id='brand_keyword']")
	private WebElement txtBrandSearch;

	@FindBy(xpath = "//li[@class='pile__element']/div[@role='presentation']//span[contains(@class,'Text_title')]")
	private List<WebElement> dropdownElements;

	@FindBy(css = "div[class*='ItemBox_details']")
	private List<WebElement> productBrands;

	@FindBy(css = "div[class*='Badge_content']")
	private List<WebElement> appliedFilters;

	@FindBy(xpath = "//button[contains(@id,'accept-btn')]")
	private WebElement acceptAllButton;

	@FindBy(xpath = "//span[@itemprop='title']")
	private List<WebElement> breadcrumbBar;

	@FindBy(css = "div[class*='ItemBox_image'] >a")
	private List<WebElement> productTiles;

	@FindBy(xpath = "//div[@data-icon-name='x']")
	private WebElement closeCountrypopup;

	@FindBy(css = "h1[class*='margin-top']")
	private WebElement margin;

	public CataloguePage() {
		PageFactory.initElements(driver, this);
		SafeActions.safeClick(driver, closeCountrypopup);
		SafeActions.waitTime(2000);
		SafeActions.safeClick(driver, acceptAllButton);
	}

	public void selectFilterOption(String filter) {
		logger.info("Selecting '" + filter + "' Filter");
		switch (filter.toLowerCase()) {
		case "catalogue":
			SafeActions.safeClick(driver, catalogueDropdown);
			break;
		case "size":
			SafeActions.safeClick(driver, sizeDropdown);
			break;
		case "colour":
			SafeActions.safeClick(driver, colourDropdown);
			break;
		case "brand":
			SafeActions.safeClick(driver, brandDropdown);
			break;
		case "price":
			SafeActions.safeClick(driver, priceDropdown);
			break;
		case "condition":
			SafeActions.safeClick(driver, conditionDropdown);
			break;
		case "swap":
			SafeActions.safeClick(driver, swapDropdown);
			break;
		default:
			Allure.step(filter + " is not a valid filter. Please check once", Status.FAILED);
			Assert.fail(filter + " is not a valid filter. Please check once");
			break;
		}
		logger.info("Selected '" + filter + "' Filter");
	}

	@Step("Searching for brand")
	public void searchForBrand(String brand) {
		logger.info("Searching for '" + brand + "'");
		SafeActions.safeType(driver, txtBrandSearch, brand);
	}

	public void selectBrandFromList(String brand) {
		boolean flag = false;
		for (WebElement ele : dropdownElements)
			if (ele.getText().equalsIgnoreCase(brand)) {
				flag = true;
				SafeActions.safeClick(driver, ele);
				break;
			}
		SafeActions.waitTime(1000);
		//SafeActions.safeClick(driver, margin);
		//SafeActions.waitTime(1000);
		if (!flag)
			logger.info("Specified brand '" + brand + "' is not availble in the result filter list");
		Assert.assertTrue(flag, "Specified brand '" + brand + "' is not availble in the filter list");
	}
	
	public void escapeDropdown() {
		SafeActions.safeClick(driver, margin);
	}

	public void validateDropdownListItems(String brand) {
		for (WebElement ele : dropdownElements) {
			System.out.println(ele.getText());
			String actualSearchResult = ele.getText();
			//Assert.assertEquals(true, ele.getText().contains(brand), "Actual: "+ele.getText()+"  Expected: "+brand);
			System.out.println(actualSearchResult.toLowerCase().startsWith(brand.toLowerCase()));
			Assert.assertEquals(true, actualSearchResult.toLowerCase().startsWith(brand.toLowerCase()), "Actual: "+ele.getText()+"  Expected: "+brand);
		}

	}

	public void verifyAllProductsBrand(String brandName) {
		System.out.println(productBrands.size());
		Allure.step(productBrands.size()+"");
		for (WebElement productBrand : productBrands) {
			TestUtil.highLightElement(productBrand);
			TestUtil.scrollIntoView(productBrand);
			SafeActions.isElementVisible(driver, productBrand);
			System.out.println(productBrand.getText());
			Assert.assertEquals(productBrand.getText().toLowerCase(), brandName.toLowerCase(), "Brand Name Mismatched");
		}
	}

	public void validateFilterApplied(String brandName) {
		boolean flag = false;
		for (WebElement filter : appliedFilters)
			if (filter.getText().equalsIgnoreCase(brandName)) {
				flag = true;
				break;
			}
		Assert.assertTrue(flag, "Selected Brand is not applied");
	}

	public void validateBreadcrumbBarOfTheProduct(String[] filters) {
		for (int i = 1; i < (breadcrumbBar.size() - 1); i++) {
			System.out.println(breadcrumbBar.get(i).getText() + " " + filters[i - 1]);
			Assert.assertEquals(breadcrumbBar.get(i).getText(), filters[i - 1], "Fliter Option didn't match");
		}
	}

	public void selectRandromProduct() {
		Random rn = new Random();
		int productNo = rn.nextInt(productTiles.size()) + 1;
		SafeActions.safeClick(driver, productTiles.get(productNo));

		SafeActions.isElementVisible(driver, driver.findElement(By.cssSelector("span[itemprop='price']")));
	}
}
