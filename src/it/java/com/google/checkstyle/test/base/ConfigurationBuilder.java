package com.google.checkstyle.test.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class ConfigurationBuilder extends BaseCheckTestSupport {

	private File ROOT;

	private List<File> files = new ArrayList<>();

	Configuration config;
	
	URL url;
	
	String xmlName = "/google_checks.xml";
	
	Pattern warnPattern = Utils.createPattern(".*[ ]*//[ ]*warn[ ]*|/[*]warn[*]/");

	public ConfigurationBuilder(File aROOT)
			throws CheckstyleException, IOException {
		this.ROOT = aROOT;
		config = getConfigurationFromXML(xmlName, System.getProperties());
		listFiles(files, ROOT, "java");
	}

	private static Configuration getConfigurationFromXML(String aConfigName,
			Properties aProps) throws CheckstyleException {
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

	public Configuration getCheckConfig(String aCheckName) {
		for (Configuration currentConfig : config.getChildren()) {
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
		String absoluteRootPath = ROOT.getAbsolutePath();
		String rootPath = absoluteRootPath.substring(0,
				absoluteRootPath.lastIndexOf("src"));
		for (File file : files) {
			if (file.toString().endsWith(aFileName+".java")) {
				return rootPath + file.toString();
			}
		}
		return null;
	}

	private static void listFiles(final List<File> files, final File folder,
			final String extension) {
		if (folder.canRead()) {
			if (folder.isDirectory()) {
				for (final File f : folder.listFiles()) {
					listFiles(files, f, extension);
				}
			} else if (folder.toString().endsWith("." + extension)) {
				files.add(folder);
			}
		}
	}

	public File getROOT() {
		return ROOT;
	}

	public List<File> getFiles() {
            return files;
	}

	public Integer[] getLinesWithWarn(String aFileName) throws IOException {
		int lineNumber = 1;
	    List<Integer> result = new ArrayList<>();
	    try(BufferedReader br = new BufferedReader(new FileReader(aFileName))) {
	        for(String line; (line = br.readLine()) != null; ) {
	            if (warnPattern.matcher(line).find()) {
	            	result.add(lineNumber);
	            }
	            lineNumber++;
	        }
	    }
	    return result.toArray(new Integer[result.size()]);
	}
}
