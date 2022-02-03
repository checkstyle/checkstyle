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
import java.util.stream.IntStream;

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
        return Arrays.stream(codePoints)
                .allMatch(Character::isWhitespace);
    }

    /**
     * Returns sub array removing trailing whitespaces.
     *
     * @param codePoints array of unicode code points
     * @return array with trailing whitespaces removed
     */
    public static int[] stripTrailing(int... codePoints) {

        final int endIndex = IntStream.iterate(codePoints.length - 1, index -> index - 1)
                .limit(codePoints.length)
                .filter(index -> !Character.isWhitespace(codePoints[index]))
                .findFirst()
                .orElse(0);
        return Arrays.copyOfRange(codePoints, 0, endIndex + 1);
    }

    /**
     * Tests if the unicode code points array beginning at the
     * specified index starts with the specified prefix.
     *
     * @param prefix the prefix
     * @param codePoints the array of unicode code point to check
     * @return {@code true}, if the unicode code point array ends with provided prefix
     */
    public static boolean endsWith(int[] codePoints, String prefix) {
        return codePoints.length > prefix.length()
            && Arrays.equals(Arrays.copyOfRange(codePoints, codePoints.length - prefix.length(),
                codePoints.length), prefix.codePoints().toArray());
    }
}
