////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.bdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public final class TestInputConfiguration {

    /** The check name. */
    private final String checkName;

    /** Map of default properties. */
    private final Map<String, String> defaultProperties;

    /** Map of non default properties. */
    private final Map<String, String> nonDefaultProperties;

    /** List of parsed violations. */
    private final List<TestInputViolation> violations;

    private TestInputConfiguration(String checkName,
                               Map<String, String> defaultProperties,
                               Map<String, String> nonDefaultProperties,
                               List<TestInputViolation> violations) {
        this.checkName = checkName;
        this.defaultProperties = defaultProperties;
        this.nonDefaultProperties = nonDefaultProperties;
        this.violations = violations;
    }

    public String getCheckName() {
        return checkName;
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

    public List<TestInputViolation> getViolations() {
        return Collections.unmodifiableList(violations);
    }

    public DefaultConfiguration createConfiguration() {
        final DefaultConfiguration parsedConfig = new DefaultConfiguration(checkName);
        nonDefaultProperties.forEach(parsedConfig::addProperty);
        return parsedConfig;
    }

    public String getDefaultPropertyValue(String key) {
        return defaultProperties.get(key);
    }

    public static final class Builder {

        private final Map<String, String> defaultProperties = new HashMap<>();

        private final Map<String, String> nonDefaultProperties = new HashMap<>();

        private final List<TestInputViolation> violations = new ArrayList<>();

        private String checkName;

        public void setCheckName(String checkName) {
            this.checkName = checkName;
        }

        public void addDefaultProperty(String key, String value) {
            defaultProperties.put(key, value);
        }

        public void addNonDefaultProperty(String key, String value) {
            nonDefaultProperties.put(key, value);
        }

        public void addViolation(int violationLine, String violationMessage) {
            violations.add(new TestInputViolation(violationLine, violationMessage));
        }

        public TestInputConfiguration build() {
            return new TestInputConfiguration(
                    checkName,
                    defaultProperties,
                    nonDefaultProperties,
                    violations
            );
        }
    }
}
