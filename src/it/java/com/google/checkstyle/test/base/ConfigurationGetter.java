package com.google.checkstyle.test.base;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class ConfigurationGetter {

	private static String config = "https://raw.githubusercontent.com/checkstyle/checkstyle/master/src/main/resources/google_checks.xml";
	private static boolean updateStatus;

	public static final ConfigurationGetter INSTANCE = new ConfigurationGetter();

	private ConfigurationGetter() {
		updateStatus = false;
	}

	public static ConfigurationGetter getInstance() {
		return INSTANCE;
	}

	public boolean isConfigurationUpdated() {
		return updateStatus;
	}

	public void updateConfiguration() throws CheckstyleException, IOException {
		if (!updateStatus) {
			getConfiguration();
		}
	}

	private static void getConfiguration() throws CheckstyleException, IOException {
		final String xmlName = "google_checks.xml";
		final URL configUrl = new URL(config);
		FileUtils.copyURLToFile(configUrl, new File(xmlName));
		updateStatus = true;
	}
}
