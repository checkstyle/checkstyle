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

package com.puppycrawl.tools.checkstyle.utils;

import java.util.Arrays;

/**
 * Contains utility methods for code point.
 */
public final class CodePointUtil {

    /** Stop instances being created. **/
    private CodePointUtil() {
    }

    /**
     * Checks if given code point array is blank by either being empty,
     * or contains only whitespace characters.
     *
     * @param codePoints The array of unicode code points of string to check.
     * @return true if codePoints is blank.
     */
    public static boolean isBlank(int... codePoints) {
        return hasWhitespaceBefore(codePoints.length, codePoints);
    }

    /**
     * Checks if the given code point array contains only whitespace up to specified index.
     *
     * @param codePoints
     *            array of Unicode code point of string to check
     * @param index
     *            index to check up to (exclusive)
     * @return true if all code points preceding given index are whitespace
     */
    public static boolean hasWhitespaceBefore(int index, int... codePoints) {
        return Arrays.stream(codePoints, 0, index)
                .allMatch(Character::isWhitespace);
    }

}
