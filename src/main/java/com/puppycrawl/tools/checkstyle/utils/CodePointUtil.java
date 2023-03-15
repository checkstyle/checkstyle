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

    /**
     * Removes trailing whitespaces.
     *
     * @param codePoints array of unicode code points
     * @return unicode code points array with trailing whitespaces removed
     */
    public static int[] stripTrailing(int... codePoints) {
        int lastIndex = codePoints.length;
        while (lastIndex > 0 && CommonUtil.isCodePointWhitespace(codePoints, lastIndex - 1)) {
            lastIndex--;
        }
        return Arrays.copyOfRange(codePoints, 0, lastIndex);
    }

    /**
     * Removes leading whitespaces.
     *
     * @param codePoints array of unicode code points
     * @return unicode code points array with leading whitespaces removed
     */
    public static int[] stripLeading(int... codePoints) {
        int startIndex = 0;
        while (startIndex < codePoints.length
            && CommonUtil.isCodePointWhitespace(codePoints, startIndex)) {
            startIndex++;
        }
        return Arrays.copyOfRange(codePoints, startIndex, codePoints.length);
    }

    /**
     * Removes leading and trailing whitespaces.
     *
     * @param codePoints array of unicode code points
     * @return unicode code points array with leading and trailing whitespaces removed
     */
    public static int[] trim(int... codePoints) {
        final int[] strippedCodePoints = stripTrailing(codePoints);
        return stripLeading(strippedCodePoints);
    }

    /**
     * Tests if the unicode code points array
     * ends with the specified suffix.
     *
     * @param suffix the suffix
     * @param codePoints the array of unicode code points to check
     * @return {@code true}, if the unicode code points array ends with provided suffix
     */
    public static boolean endsWith(int[] codePoints, String suffix) {
        final int startIndex = codePoints.length - suffix.length();
        return startIndex > -1 && Arrays.equals(Arrays
                    .copyOfRange(codePoints, startIndex, codePoints.length),
                    suffix.codePoints().toArray());
    }
}
