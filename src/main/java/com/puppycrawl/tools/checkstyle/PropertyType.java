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

package com.puppycrawl.tools.checkstyle;

/**
 * Represents the custom property type used in documentation and configuration files.
 */
public enum PropertyType {

    /** This property is a file. */
    FILE("File"),

    /** This property is a string represents an ISO 3166 2-letter code. */
    LOCALE_COUNTRY("String (either the empty string or an uppercase ISO 3166 2-letter code)"),

    /** This property is a string represents an ISO 639 code. */
    LOCALE_LANGUAGE("String (either the empty string or a lowercase ISO 639 code)"),

    /** This property is a regular expression pattern. */
    PATTERN("Pattern"),

    /** This property is a string. */
    STRING("String"),

    /** This property is a set of tokens. */
    TOKEN_ARRAY("subset of tokens TokenTypes");

    /** The human-readable property description. */
    private final String description;

    /**
     * Creates a new {@code PropertyType} instance.
     *
     * @param description the human-readable property description
     */
    PropertyType(String description) {
        this.description = description;
    }

    /**
     * Returns the human-readable property description.
     *
     * @return human-readable property description
     */
    public String getDescription() {
        return description;
    }

}
