package com.vinted.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vinted.common.SafeActions;
import com.vinted.common.TestBase;
import com.vinted.common.TestUtil;
import com.vinted.pages.CataloguePage;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class CatalogueTest extends TestBase {

	CataloguePage cataloguePage;
	String url = getURL()+ "/catalog";

	public CatalogueTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws InterruptedException {
		initialization(url);
	}

	@Test(priority = 1)
	@Severity(SeverityLevel.CRITICAL)
	@Epic("Validating Catalogue Page")
	public void validateNikeProducts() {
		Allure.description("Validating the Nike Search Results");
		cataloguePage = new CataloguePage();
		String brand = "Nike";

		Allure.step("Clicking on the Brand Filter");
		cataloguePage.selectFilterOption("Brand");

		Allure.step("Typing in the the Brand Filter Searchbox");
		cataloguePage.searchForBrand("Ni");

		Allure.step("Selecting the Brand '" + brand + "' from the search results");
		cataloguePage.selectBrandFromList(brand);
		cataloguePage.escapeDropdown();

		Allure.step("Verifying the selected brand " + brand + "' is displayed below the filter menu");
		cataloguePage.validateFilterApplied(brand);

		Allure.step("Verifying the product brands from the search results");
		cataloguePage.verifyAllProductsBrand(brand);
	}

	/**
	 - Open catalogue https://www.vinted.co.uk/catalog
	 - Click on the brand filter dropdown
	 - Type Nik in the search input
	 - Assert that all filter option items contain Nik in the title.
	 */
	@Test(priority = 3)
	@Severity(SeverityLevel.CRITICAL)
	@Epic("Validating Filter Option Items")
	public void validateBrandFilterDropdownListItems() {
		Allure.description("Validating the Filter Options value");
		cataloguePage = new CataloguePage();

		Allure.step("Clicking on the Brand Filter");
		cataloguePage.selectFilterOption("Brand");

		Allure.step("Typing in the the Brand Filter Searchbox");
		cataloguePage.searchForBrand("Nik");

		Allure.step("Selecting the Brand 'Nik' from the search results");
		cataloguePage.validateDropdownListItems("Nik");
	}
	
	/**
	 - Open catalogue https://www.vinted.co.uk/catalog
	 - Click on catalogue filter
	 - Select �Women� -> �Shoes� -> �Heels� -> �High-heels�
	 - Assert that any random item from the list is from the selected category
	 */
	@Test(priority = 2)
	public void verifyRandomProduct() {
		Allure.description("Validating the Filter Options value");
		
		String[] filters = {"Women","Shoes","Heels","High heels"};
		
		cataloguePage = new CataloguePage();

		Allure.step("Clicking on the Catalogue Filter");
		cataloguePage.selectFilterOption("Catalogue");
		
		for(String filter:filters) {
			//Allure.step("Clicking on the Catalogue Filter");
			//cataloguePage.selectFilterOption("Catalogue");
			Allure.step("Selecting '"+filter+"' from the dropdown");
			cataloguePage.selectBrandFromList(filter);
			
		}
		cataloguePage.escapeDropdown();
		
		SafeActions.waitTime(5000);
		
		cataloguePage.selectRandromProduct();
		
		Allure.step("Validating the Bread Crumb navigation of the Product");
		cataloguePage.validateBreadcrumbBarOfTheProduct(filters);
	}
	
	@AfterMethod
	public void endOfTest(ITestResult result) throws IOException {
		if (!result.isSuccess()) {
			String methodName = result.getMethod().getMethodName();
			TestUtil.takeScreenshotAtEndOfTest(methodName);
			
		}
		closeBrowser();
	}

	//@Test
	@Severity(SeverityLevel.CRITICAL)
	public void allureTest() throws Exception {
		Allure.suite("This is Test Suite");
		Allure.epic("This is Test Epic");
		Allure.feature("This is Test Feature");
		Allure.story("This is Test Story");

		// Test Content Allure.description("Validating the Nike Search Results");
		Allure.parameter("Param_Test", "param Value");
		Allure.issue("Issue Test", "https://testingbulletin.blogspot.com/2017/12/inheritance.html");
		Allure.link("Link Tet", "https://testingbulletin.blogspot.com/2017/12/inheritance.html");

		Allure.label("Label Name", "Label Value");
		Allure.attachment("Log", new FileInputStream(new File("./Attachments/log.txt")));
		Allure.addAttachment("Info", "Selecting the Filter");
		Allure.step("Selecting the Brand");
		Allure.step("Typing the Nike");
		Allure.step("Validated the Results");
	}

	//@AfterClass
	public void tearDown() {
		closeBrowser();
	}
}
