////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.api;

import java.util.Locale;

/**
 * Severity level for a check violation.
 *
 * <p>
 * Each violation of an audit check is assigned one of the severity levels
 * defined here.
 * </p>
 *
 */
public enum SeverityLevel {

    /** Severity level ignore. */
    IGNORE,
    /** Severity level info. */
    INFO,
    /** Severity level warning. */
    WARNING,
    /** Severity level error. */
    ERROR;

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Returns name of severity level.
     *
     * @return the name of this severity level.
     */
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * SeverityLevel factory method.
     *
     * @param securityLevelName level name, such as "ignore", "info", etc.
     * @return the {@code SeverityLevel}
     *     associated with {@code securityLevelName}
     */
    public static SeverityLevel getInstance(String securityLevelName) {
        return valueOf(SeverityLevel.class, securityLevelName.trim()
            .toUpperCase(Locale.ENGLISH));
    }

}
