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

package com.puppycrawl.tools.checkstyle.api;

import java.util.Locale;

/**
 * <p>
 * Severity level for a check violation.
 * </p>
 * <p>
 * Each violation of an audit check is assigned one of the severity levels
 * defined here.
 * </p>
 *
 * @author David Schneider
 * @author Travis Schneeberger
 * @author Mehmet Can Cömert
 */
public enum SeverityLevel {
    /** security level ignore. */
    IGNORE,
    /** security level info. */
    INFO,
    /** security level warning. */
    WARNING,
    /** security level error. */
    ERROR;

    @Override
    public String toString() {
        return getName();
    }

    /**
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
     * associated with {@code securityLevelName}
     */
    public static SeverityLevel getInstance(String securityLevelName) {
        return valueOf(SeverityLevel.class, securityLevelName.trim()
                .toUpperCase(Locale.ENGLISH));
    }
}
