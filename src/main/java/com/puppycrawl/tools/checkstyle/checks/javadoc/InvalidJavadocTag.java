///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
 * Value object for storing data about an invalid Javadoc validTags.
 */
public final class InvalidJavadocTag {

    /** The line in which the invalid tag occurs. */
    private final int line;
    /** The column in which the invalid tag occurs. */
    private final int col;
    /** The name of the invalid tag. */
    private final String name;

    /**
     * Creates an instance.
     *
     * @param line the line of the tag
     * @param col the column of the tag
     * @param name the name of the invalid tag
     */
    public InvalidJavadocTag(int line, int col, String name) {
        this.line = line;
        this.col = col;
        this.name = name;
    }

    /**
     *  Getter for line field.
     *
     *  @return line field
     */
    public int getLine() {
        return line;
    }

    /**
     *  Getter for col field.
     *
     *  @return col field
     */
    public int getCol() {
        return col;
    }

    /**
     *  Getter for name field.
     *
     *  @return name field
     */
    public String getName() {
        return name;
    }

}
