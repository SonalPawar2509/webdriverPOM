package com.vinted.utils;

import org.testng.annotations.BeforeClass;

import com.vinted.common.TestBase;

public class MemberProfileTest extends TestBase {
	
	String url = "https://www.vinted.co.uk/member/64881339-sonal250990";
	public MemberProfileTest() {
		super();
	}
	
	@BeforeClass
	public void setUp() throws InterruptedException {
		initialization(url);
	}
}
