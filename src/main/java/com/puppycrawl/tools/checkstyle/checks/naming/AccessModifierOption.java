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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.Locale;

/**
 * This enum represents access modifiers.
 * Access modifiers names are taken from
 * <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.6">JLS</a>
 *
 */
public enum AccessModifierOption {

    /** Public access modifier. */
    PUBLIC,
    /** Protected access modifier. */
    PROTECTED,
    /** Package access modifier. */
    PACKAGE,
    /** Private access modifier. */
    PRIVATE;

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Returns the access modifier name.
     *
     * @return the access modifier name
     */
    private String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * Factory method which returns an AccessModifier instance that corresponds to the
     * given access modifier name represented as a {@link String}.
     * The access modifier name can be formatted both as lower case or upper case string.
     * For example, passing PACKAGE or package as a modifier name
     * will return {@link AccessModifierOption#PACKAGE}.
     *
     * @param modifierName access modifier name represented as a {@link String}.
     * @return the AccessModifier associated with given access modifier name.
     */
    public static AccessModifierOption getInstance(String modifierName) {
        return valueOf(AccessModifierOption.class, modifierName.trim().toUpperCase(Locale.ENGLISH));
    }

}
