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

import org.apache.commons.io.FileUtils;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.Utils;

public class ConfigurationBuilder extends BaseCheckTestSupport {

	private File mROOT;

	private List<File> mFiles = new ArrayList<File>();

	Configuration mConfig;
	
	URL mUrl;
	
	String mXmlName = "google_checks.xml";
	
	Pattern warnPattern = Utils.createPattern(".*[ ]*//[ ]*warn[ ]*|/[*]warn[*]/");
	
	public ConfigurationBuilder(File aROOT)
			throws CheckstyleException, IOException {
		ConfigurationGetter.getInstance().updateConfiguration();
		this.mROOT = aROOT;
		mConfig = getConfigurationFromXML(mXmlName, System.getProperties());
		listFiles(mFiles, mROOT, "java");
	}

	private Configuration getConfigurationFromXML(String aConfigName,
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
		for (Configuration config : mConfig.getChildren()) {
			if ("TreeWalker".equals(config.getName())) {
				for (Configuration checkConfig : config.getChildren()) {
					if (aCheckName.equals(checkConfig.getName())) {
						return checkConfig;
					}
				}
			} else if (aCheckName.equals(config.getName())) {
				return config;
			}
		}
		return null;
	}

	public String getFilePath(String aFileName) {
		String absoluteRootPath = mROOT.getAbsolutePath();
		String rootPath = absoluteRootPath.substring(0,
				absoluteRootPath.lastIndexOf("src"));
		for (File file : mFiles) {
			if (file.toString().contains(aFileName)) {
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
		return mROOT;
	}

	public Integer[] getLinesWithWarn(String aFileName) throws IOException {
		int lineNumber = 1;
	    List<Integer> result = new ArrayList<Integer>();
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
