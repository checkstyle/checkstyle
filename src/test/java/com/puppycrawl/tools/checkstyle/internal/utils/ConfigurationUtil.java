///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public final class ConfigurationUtil {

    private ConfigurationUtil() {
    }

    public static Configuration loadConfiguration(String path) throws CheckstyleException {
        final Properties props = new Properties();

        props.setProperty("checkstyle.basedir", "basedir");
        props.setProperty("checkstyle.cache.file", "file");
        props.setProperty("checkstyle.suppressions.file", "file");
        props.setProperty("checkstyle.suppressions-xpath.file", "file");
        props.setProperty("checkstyle.header.file", "file");
        props.setProperty("checkstyle.regexp.header.file", "file");
        props.setProperty("checkstyle.importcontrol.file", "file");
        props.setProperty("checkstyle.importcontroltest.file", "file");

        return loadConfiguration(path, props);
    }

    public static Configuration loadConfiguration(String path, Properties props)
            throws CheckstyleException {
        return ConfigurationLoader.loadConfiguration(path, new PropertiesExpander(props));
    }

    public static Set<Configuration> getModules(Configuration config) {
        final Set<Configuration> result = new HashSet<>();

        for (Configuration child : config.getChildren()) {
            if ("TreeWalker".equals(child.getName())) {
                result.addAll(getModules(child));
            }
            else {
                result.add(child);
            }
        }

        return result;
    }

    public static Set<Configuration> getChecks(Configuration config) {
        final Set<Configuration> result = new HashSet<>();

        for (Configuration child : config.getChildren()) {
            if ("TreeWalker".equals(child.getName())) {
                result.addAll(getModules(child));
            }
        }

        return result;
    }

}
