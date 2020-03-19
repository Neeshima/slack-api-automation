package com.slack.utils;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestUtils {

	public static Response get() {

		Response response = RestAssured.given().auth().oauth2(Constants.ACCESS_TOKEN).when().get();
		return response;
	}

	public static Response post(String body) {

		Response response = RestAssured.given().auth().oauth2(Constants.ACCESS_TOKEN).when().body(body).post();
		return response;
	}

	public static Response post(Map<String, String> formParams) {

		Response response = RestAssured.given().auth().oauth2(Constants.ACCESS_TOKEN).when().formParams(formParams)
				.post();
		return response;
	}

}
