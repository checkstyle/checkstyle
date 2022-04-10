////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.Scope;

/**
 * Represents a Java visibility access modifier.
 *
 */
public enum AccessModifier {

    /** Nothing access modifier. */
    NOTHING,
    /** Public access modifier. */
    PUBLIC,
    /** Protected access modifier. */
    PROTECTED,
    /** Package or default access modifier. */
    PACKAGE,
    /** Private access modifier. */
    PRIVATE;


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
     * Checks if this scope is a subscope of another scope.
     * Example: PUBLIC is a subscope of PRIVATE.
     *
     * @param accessModifier a {@code AccessModifier} value
     * @return if {@code this} is a subscope of {@code scope}.
     */
    public boolean isIn(AccessModifier accessModifier) {
        return compareTo(accessModifier) <= 0;
    }

    /**
     * Scope factory method.
     *
     * @param scopeName scope name, such as "nothing", "public", etc.
     * @return the {@code Scope} associated with {@code scopeName}
     */
    public static Scope getInstance(String scopeName) {
        return valueOf(Scope.class, scopeName.trim().toUpperCase(Locale.ENGLISH));
    }

}
