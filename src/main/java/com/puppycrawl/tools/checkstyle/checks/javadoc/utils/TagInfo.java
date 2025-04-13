///
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

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
 * Value object for storing data about a parsed tag.
 *
 */
public final class TagInfo {

    /**
     * Name of the tag ("link", "see", etc).
     */
    private final String name;

    /**
     * Value of the tag.
     */
    private final String value;

    /**
     * Position of the tag in the given comment.
     */
    private final LineColumn position;

    /**
     * Constructor.
     *
     * @param name The name of the tag.
     * @param value The value of the tag.
     * @param position The position of the tag in the comment.
     */
    public TagInfo(String name, String value, LineColumn position) {
        this.name = name;
        this.value = value;
        this.position = position;
    }

    /**
     * Return name of tag.
     *
     * @return Name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Return value of tag.
     *
     * @return Value of the tag.
     */
    public String getValue() {
        return value;
    }

    /**
     * Return position of tag.
     *
     * @return Value of the tag.
     */
    public LineColumn getPosition() {
        return position;
    }

}

