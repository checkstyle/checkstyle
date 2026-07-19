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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * Value object for storing data about an invalid Javadoc tag.
 *
 * @param line the line of the invalid tag
 * @param col the column of the invalid tag
 * @param name the name of the invalid tag
 */
public record InvalidJavadocTag(int line, int col, String name) {

    /**
     * Creates a new {@code InvalidJavadocTag} instance.
     *
     * @param line the line number
     * @param col the column number
     * @param name the name of the invalid tag
     */
    public InvalidJavadocTag {
    }

    /**
     * Legacy getter for line (backward compatibility).
     *
     * @return the line of the invalid tag
     */
    public int getLine() {
        return line;
    }

    /**
     * Legacy getter for col (backward compatibility).
     *
     * @return the column of the invalid tag
     */
    public int getCol() {
        return col;
    }

    /**
     * Legacy getter for name (backward compatibility).
     *
     * @return the name of the invalid tag
     */
    public String getName() {
        return name;
    }

}
