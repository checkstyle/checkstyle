package com.google.checkstyle.test.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class ConfigurationBuilder extends BaseCheckTestSupport {

	private static final String XML_NAME = "/google_checks.xml";

	private final File root;

	private final List<File> files = new ArrayList<>();

	private final Configuration configuration;

    private final Pattern warnPattern = CommonUtils.createPattern(".*[ ]*//[ ]*warn[ ]*|/[*]warn[*]/");

	private URL url;

	public ConfigurationBuilder(File aROOT)
			throws CheckstyleException {
		root = aROOT;
		configuration = getConfigurationFromXML(XML_NAME, System.getProperties());
		listFiles(files, root, "java");
	}

	private static Configuration getConfigurationFromXML(String aConfigName,
			Properties aProps) {
		try {
			return ConfigurationLoader.loadConfiguration(aConfigName,
					new PropertiesExpander(aProps));
		} catch (final CheckstyleException e) {
			System.out.println("Error loading configuration file");
			e.printStackTrace(System.out);
			System.exit(1);
			return null;
		}
	}

	Configuration getConfiguration() {
		return configuration;
	}

	public Configuration getCheckConfig(String aCheckName) {
		for (Configuration currentConfig : configuration.getChildren()) {
			if ("TreeWalker".equals(currentConfig.getName())) {
				for (Configuration checkConfig : currentConfig.getChildren()) {
					if (aCheckName.equals(checkConfig.getName())) {
						return checkConfig;
					}
				}
			} else if (aCheckName.equals(currentConfig.getName())) {
				return currentConfig;
			}
		}
		return null;
	}

	public String getFilePath(String aFileName) {
		String absoluteRootPath = root.getAbsolutePath();
		String rootPath = absoluteRootPath.substring(0,
				absoluteRootPath.lastIndexOf("src"));
		for (File file : files) {
			if (file.toString().endsWith(aFileName+".java")) {
				return rootPath + file;
			}
		}
		return null;
	}

	private static void listFiles(final List<File> files, final File folder,
			final String extension) {
		if (folder.canRead()) {
			if (folder.isDirectory()) {
				for (final File file : folder.listFiles()) {
					listFiles(files, file, extension);
				}
			} else if (folder.toString().endsWith("." + extension)) {
				files.add(folder);
			}
		}
	}

	public File getRoot() {
		return root;
	}

	public List<File> getFiles() {
            return Collections.unmodifiableList(files);
	}

	public Integer[] getLinesWithWarn(String aFileName) throws IOException {
		List<Integer> result = new ArrayList<>();
	    try(BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
			int lineNumber = 1;
			while (true) {
	            String line = br.readLine();
	            if (line == null) {
	                break;
	            }
	            if (warnPattern.matcher(line).find()) {
	            	result.add(lineNumber);
	            }
	            lineNumber++;
	        }
	    }
	    return result.toArray(new Integer[result.size()]);
	}
}
