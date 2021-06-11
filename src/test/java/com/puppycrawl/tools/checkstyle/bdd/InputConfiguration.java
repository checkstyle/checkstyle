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

public final class InputConfiguration {

    /** The check name. */
    private final String checkName;

    /** Map of default properties. */
    private final Map<String, String> defaultProperties;

    /** Map of non default properties. */
    private final Map<String, String> nonDefaultProperties;

    /** List of violations. */
    private final List<String> violations;

    private InputConfiguration(String checkName,
                               Map<String, String> defaultProperties,
                               Map<String, String> nonDefaultProperties,
                               List<String> violations) {
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

    public List<String> getViolations() {
        return Collections.unmodifiableList(violations);
    }

    public DefaultConfiguration createConfiguration() {
        // to be implemented
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public static class Builder {

        private final Map<String, String> defaultProperties;

        private final Map<String, String> nonDefaultProperties;

        private final List<String> violations;

        private String checkName;

        public Builder(String checkName) {
            this.checkName = checkName;
            defaultProperties = new HashMap<>();
            nonDefaultProperties = new HashMap<>();
            violations = new ArrayList<>();
        }

        public Builder withDefaultProperties(Map<String, String> defaultProperties) {
            if (defaultProperties != null) {
                this.defaultProperties.putAll(defaultProperties);
            }

            return this;
        }

        public Builder withNonDefaultProperties(Map<String, String> nonDefaultProperties) {
            if (nonDefaultProperties != null) {
                this.nonDefaultProperties.putAll(nonDefaultProperties);
            }

            return this;
        }

        public Builder withViolationList(List<String> violations) {
            if (violations != null) {
                this.violations.addAll(violations);
            }

            return this;
        }

        public void setCheckName(String checkName) {
            this.checkName = checkName;
        }

        public void addDefaultProperty(String property, String value) {
            defaultProperties.put(property, value);
        }

        public void addNonDefaultProperty(String property, String value) {
            nonDefaultProperties.put(property, value);
        }

        public void addViolation(String violation) {
            violations.add(violation);
        }

        public InputConfiguration build() {
            return new InputConfiguration(checkName,
                    defaultProperties, nonDefaultProperties, violations);
        }
    }
}
