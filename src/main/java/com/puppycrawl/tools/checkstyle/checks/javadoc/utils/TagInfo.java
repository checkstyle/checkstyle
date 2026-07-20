///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
 * Value object for storing data about a parsed tag.
 *
 * @param name Name of the tag ("link", "see", etc)
 * @param value Value of the tag
 * @param position Position of the tag in the given comment
 */
public record TagInfo(String name, String value, LineColumn position) {

    /**
     * Creates a new {@code TagInfo} instance.
     *
     * @param name Name of the tag ("link", "see", etc)
     * @param value Value of the tag
     * @param position Position of the tag in the given comment
     */
    public TagInfo {
    }

    /**
     * Legacy getter for tag name (backward compatibility).
     *
     * @return Name of the tag ("link", "see", etc)
     */
    public String getName() {
        return name;
    }

    /**
     * Legacy getter for tag value (backward compatibility).
     *
     * @return Value of the tag
     */
    public String getValue() {
        return value;
    }

    /**
     * Legacy getter for tag position (backward compatibility).
     *
     * @return Position of the tag in the given comment
     */
    public LineColumn getPosition() {
        return position;
    }

}
