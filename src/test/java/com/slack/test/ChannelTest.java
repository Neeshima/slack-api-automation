package com.slack.test;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.slack.testutils.BaseTest;
import com.slack.utils.RestUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ChannelTest extends BaseTest {

	String channelName = "testchannel" + System.currentTimeMillis();
	String channelId;

	@Test(priority = 0)
	public void createChannel() {

		System.out.println("-------------------------------- Creating a Channel --------------------------------");
		// set the basepath
		RestAssured.basePath = "/channels.create";

		// form parameter map
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("name", channelName);

		// REST call
		Response response = RestUtils.post(formParams);
		System.out.println("Response from CreateChannelApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());

		// record the channelId of the channel created
		channelId = response.jsonPath().getString("channel.id");
	}

	@Test(priority = 1)
	public void joinChannel() {

		System.out.println("-------------------------------- Joining the Channel --------------------------------");
		// set the basepath
		RestAssured.basePath = "/channels.join";

		// form parameter map
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("name", channelName);

		// REST call
		Response response = RestUtils.post(formParams);
		System.out.println("Response from JoinChannelApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
	}

	@Test(priority = 2)
	public void renameChannel() {

		System.out.println("-------------------------------- Renaming the Channel --------------------------------");
		// set the basepath
		RestAssured.basePath = "/channels.rename";
		String newName = channelName + "_rename";

		// form parameter map
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("channel", channelId);
		formParams.put("name", newName);

		// REST call
		Response response = RestUtils.post(formParams);
		System.out.println("Response from RenameChannelApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());

		// call ListChannelApi and validate if channel is renamed successfully
		System.out.println("-------------------------------- Listing All Channels --------------------------------");
		// set the basepath
		RestAssured.basePath = "channels.list";
		Response listResponse = RestUtils.get();
		System.out.println("Response from ListChannelsApi : " + listResponse.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());
		JSONObject jsonObj = new JSONObject(listResponse.getBody().asString());
		JSONArray channelList = jsonObj.getJSONArray("channels");

		// iterate through channel list from the response and check if the name of the
		// channel is equal to the new name
		for (int i = 0; i < channelList.length(); i++) {
			JSONObject channel = channelList.getJSONObject(i);
			if (channel.getString("id").equalsIgnoreCase(channelId)) {
				Assert.assertEquals(channel.getString("name"), newName, "Renaming of channel not successful!!!");
			}
		}
	}

	@Test(priority = 3)
	public void archiveChannel() {

		System.out.println("-------------------------------- Archiving the Channel --------------------------------");
		// set the basepath
		RestAssured.basePath = "/channels.archive";

		// form parameter map
		Map<String, String> formParams = new HashMap<String, String>();
		formParams.put("channel", channelId);

		// REST call
		Response response = RestUtils.post(formParams);
		System.out.println("Response from ArchiveChannelApi : " + response.getBody().asString());
		System.out.println("Response status code : " + response.statusCode());

		// check the value of ok to know if archival was successful or not
		Assert.assertEquals(response.jsonPath().getBoolean("ok"), true, "Archive of channel not successful!!!");

	}

}
