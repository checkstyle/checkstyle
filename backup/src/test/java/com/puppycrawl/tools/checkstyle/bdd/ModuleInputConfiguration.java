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

package com.puppycrawl.tools.checkstyle.bdd;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public final class ModuleInputConfiguration {

    /** The module name. */
    private final String moduleName;

    /** Map of default properties. */
    private final Map<String, String> defaultProperties;

    /** Map of non default properties. */
    private final Map<String, String> nonDefaultProperties;

    /** Map of module messages. */
    private final Map<String, String> moduleMessages;

    private ModuleInputConfiguration(String moduleName,
                                     Map<String, String> defaultProperties,
                                     Map<String, String> nonDefaultProperties,
                                     Map<String, String> moduleMessages) {
        this.moduleName = moduleName;
        this.defaultProperties = defaultProperties;
        this.nonDefaultProperties = nonDefaultProperties;
        this.moduleMessages = moduleMessages;
    }

    public String getModuleName() {
        return moduleName;
    }

    public Map<String, String> getAllProperties() {
        final Map<String, String> properties = new HashMap<>();
        properties.putAll(defaultProperties);
        properties.putAll(nonDefaultProperties);
        return Collections.unmodifiableMap(properties);
    }

    public Map<String, String> getDefaultProperties() {
        return Collections.unmodifiableMap(defaultProperties);
    }

    public Map<String, String> getNonDefaultProperties() {
        return Collections.unmodifiableMap(nonDefaultProperties);
    }

    public Map<String, String> getModuleMessages() {
        return Collections.unmodifiableMap(moduleMessages);
    }

    public DefaultConfiguration createConfiguration() {
        final DefaultConfiguration parsedConfig = new DefaultConfiguration(moduleName);
        nonDefaultProperties.forEach(parsedConfig::addProperty);
        moduleMessages.forEach(parsedConfig::addMessage);
        return parsedConfig;
    }

    public String getDefaultPropertyValue(String key) {
        return defaultProperties.get(key);
    }

    public static final class Builder {

        private final Map<String, String> defaultProperties = new HashMap<>();

        private final Map<String, String> nonDefaultProperties = new HashMap<>();

        private final Map<String, String> moduleMessages = new HashMap<>();

        private String moduleName;

        public void setModuleName(String moduleName) {
            this.moduleName = moduleName;
        }

        public void addDefaultProperty(String propertyName, String defaultPropertyValue) {
            defaultProperties.put(propertyName, defaultPropertyValue);
        }

        public void addNonDefaultProperty(String propertyName, String nonDefaultPropertyValue) {
            nonDefaultProperties.put(propertyName, nonDefaultPropertyValue);
        }

        public void addModuleMessage(String messageKey, String messageString) {
            moduleMessages.put(messageKey, messageString);
        }

        public ModuleInputConfiguration build() {
            return new ModuleInputConfiguration(
                    moduleName,
                    defaultProperties,
                    nonDefaultProperties,
                    moduleMessages
            );
        }
    }
}
