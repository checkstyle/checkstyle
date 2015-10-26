////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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

    private final Pattern warnPattern = CommonUtils
        .createPattern(".*[ ]*//[ ]*warn[ ]*|/[*]warn[*]/");

    public ConfigurationBuilder(File aRoot) {
        root = aRoot;
        configuration = getConfigurationFromXML(XML_NAME, System.getProperties());
        listFiles(files, root, "java");
    }

    private static Configuration getConfigurationFromXML(String aConfigName,
            Properties aProps) {
        try {
            return ConfigurationLoader.loadConfiguration(aConfigName,
                    new PropertiesExpander(aProps));
        }
        catch (final CheckstyleException e) {
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
        Configuration result = null;
        for (Configuration currentConfig : configuration.getChildren()) {
            if ("TreeWalker".equals(currentConfig.getName())) {
                for (Configuration checkConfig : currentConfig.getChildren()) {
                    if (aCheckName.equals(checkConfig.getName())) {
                        result = checkConfig;
                        break;
                    }
                }
                if (result != null) {
                    break;
                }
            }
            else if (aCheckName.equals(currentConfig.getName())) {
                result = currentConfig;
            }
        }
        return result;
    }

    public String getFilePath(String aFileName) {
        final String absoluteRootPath = root.getAbsolutePath();
        final String rootPath = absoluteRootPath.substring(0,
                absoluteRootPath.lastIndexOf("src"));
        for (File file : files) {
            if (file.toString().endsWith(aFileName + ".java")) {
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
            }
            else if (folder.toString().endsWith("." + extension)) {
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
        final List<Integer> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(aFileName), StandardCharsets.UTF_8))) {
            int lineNumber = 1;
            while (true) {
                final String line = br.readLine();
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
