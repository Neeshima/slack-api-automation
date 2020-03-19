package com.slack.testutils;

import java.io.IOException;

import org.testng.annotations.BeforeTest;

import com.slack.utils.CommonUtils;

import io.restassured.RestAssured;

public class BaseTest {

	@BeforeTest
	public void init() throws IOException {

		RestAssured.baseURI = CommonUtils.getProperty("baseUri");
	}

}
