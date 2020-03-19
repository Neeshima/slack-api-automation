package com.slack.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonUtils {

	public static String getProperty(String key) throws IOException {

		Properties properties = new Properties();
		properties.load(new FileInputStream(Constants.PROPERTIES_FILE));
		return properties.getProperty(key);
	}

}
