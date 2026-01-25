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

package com.puppycrawl.tools.checkstyle.checks;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Represents the options for line ending settings.
 *
 * @see LineEndingCheck
 */
public enum LineEndingOption {

    /** Unix-style line ending. **/
    LF("\n"),

    /** Windows-style line ending. **/
    CRLF("\r\n"),

    /** Mac-style line ending. **/
    CR("\r");

    /** The line ending representation. */
    private final byte[] lineEnding;

    /**
     * Creates a new {@code LineEndingOption} instance.
     *
     * @param end the line ending, e.g. "\n"
     */
    LineEndingOption(String end) {
        lineEnding = end.getBytes(StandardCharsets.US_ASCII);
    }

    /**
     * Checks that bytes is equal to the byte representation of this line ending.
     *
     * @param bytes a bytes array to check
     * @return true if bytes is equal to the byte representation of this line ending.
     */
    public boolean matches(byte... bytes) {
        return Arrays.equals(lineEnding, bytes);
    }

}
