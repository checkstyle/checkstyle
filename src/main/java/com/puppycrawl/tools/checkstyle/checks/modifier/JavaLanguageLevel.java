///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.modifier;

/**
 * Represents the Java language level.
 *
 * @see RedundantModifierCheck
 */
public enum JavaLanguageLevel {

    /**
     * Represents Java language level less than 17.
     */
    LEVEL_LT_17(16),

    /**
     * Represents Java language level 17.
     */
    LEVEL_17(17),;

    /**
     * The value of the language level.
     */
    private final int value;

    /**
     * Creates a new instance.
     *
     * @param value the value of the language level
     */
    JavaLanguageLevel(int value) {
        this.value = value;
    }

    /**
     * Getter for the value.
     *
     * @return the value of the language level
     */
    public int getIntValue() {
        return value;
    }

    /**
     * Returns the language level based on the JDK version.
     *
     * @param jdkVersion the JDK version
     * @return the java language level
     */
    public static JavaLanguageLevel getLevel(String jdkVersion) {
        final JavaLanguageLevel level;
        final int Jdk17 = 17;
        if (jdkVersion.startsWith("1.")
                || Integer.parseInt(jdkVersion) < Jdk17) {
            level = LEVEL_LT_17;
        }
        else {
            level = LEVEL_17;
        }
        return level;
    }
}
