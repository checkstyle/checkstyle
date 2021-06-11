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

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport.ModuleCreationOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ParsedInput {

    /** The check name. */
    private final String checkName;

    /** Map of properties. */
    private final Map<String, String> properties;

    /** Map of default properties. */
    private final Map<String, String> defaultProperties;

    /** Map of non default properties. */
    private final Map<String, String> nonDefaultProperties;

    /** List of violations. */
    private final List<String> violations;

    public ParsedInput(String checkName,
                       Map<String, String> properties,
                       Map<String, String> defaultProperties,
                       Map<String, String> nonDefaultProperties,
                       List<String> violations) {
        this.checkName = checkName;
        if (properties == null) {
            this.properties = null;
        }
        else {
            this.properties = new HashMap<>(properties);
        }
        if (defaultProperties == null) {
            this.defaultProperties = null;
        }
        else {
            this.defaultProperties = new HashMap<>(defaultProperties);
        }
        if (nonDefaultProperties == null) {
            this.nonDefaultProperties = null;
        }
        else {
            this.nonDefaultProperties = new HashMap<>(nonDefaultProperties);
        }
        if (violations == null) {
            this.violations = null;
        }
        else {
            this.violations = new ArrayList<>(violations);
        }
    }

    public String getCheckName() {
        return checkName;
    }

    public Map<String, String> getAllProperties() {
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

    public DefaultConfiguration createConfiguration(ModuleCreationOption option) {
        return null;
    }
}
