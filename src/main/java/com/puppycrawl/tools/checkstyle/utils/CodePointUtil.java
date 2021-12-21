////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

/**
 * Contains String Utility methods.
 *
 */
public final class CodePointUtil {

    /** Stop instances being created. **/
    private CodePointUtil() {
    }

    /**
     * Computes no. of code units in string represented,
     * as array of Unicode code points.
     *
     * @param codePoints the array of Unicode code points
     * @return length of string
     */
    public static int getNoOfCodeUnits(int...codePoints) {
        int length = 0;
        for (int codePoint : codePoints) {
            length += Character.charCount(codePoint);
        }
        return length;
    }

    /**
     * Returns an array that is sub array of this array. The sub array begins
     * at beginIndex and ends at endIndex
     *
     * @param codePoints Array
     * @param beginIndex - the beginning index, inclusive
     * @param endIndex - the ending index, exclusive
     * @return the specified sub array
     */
    public static int[] subArray(int[] codePoints, int beginIndex, int endIndex) {
        final int[] newCodePoints = new int[endIndex - beginIndex];
        System.arraycopy(codePoints, beginIndex, newCodePoints, 0, endIndex - beginIndex);
        return newCodePoints;
    }

    /**
     * Returns sub array removing all leading and trailing space.
     *
     * @param codePoints Array of unicode code point with leading or trailing whitespace
     * @return array with spaces removed
     */
    public static int[] trim(int...codePoints) {

        int beginIndex = 0;
        int endIndex = codePoints.length - 1;

        while (beginIndex < codePoints.length
                && CommonUtil.isCodePointWhitespace(codePoints, beginIndex)) {
            beginIndex++;
        }
        while (endIndex >= 0 && CommonUtil.isCodePointWhitespace(codePoints, endIndex)) {
            endIndex--;
        }
        int size = 0;
        if (beginIndex <= endIndex) {
            size = endIndex - beginIndex + 1;
        }
        final int[] newCodePoints = new int[size];
        System.arraycopy(codePoints, beginIndex, newCodePoints, 0, size);
        return newCodePoints;
    }

    /**
     * Finds index of character after the specified index.
     *
     * @param codePoints the array to search for index of character
     * @param after the position from where character needs to be searched
     * @param characterToMatch the character whose index needs to be searched
     * @return index of ch.
     */
    public static int indexOfCharacter(int[] codePoints, int after, char characterToMatch) {
        final int lengthOfCodePoint = codePoints.length;
        int index = after;
        while (lengthOfCodePoint > index) {
            final int codePointAt = codePoints[index];
            if (codePointAt == characterToMatch) {
                break;
            }
            index += 1;
        }
        if (index >= lengthOfCodePoint) {
            index = -1;
        }
        return index;
    }

    /**
     * Tests if the array starts with specified prefix.
     *
     * @param prefix the prefix
     * @param codePoints the array of Unicode code point
     * @param fromIndex index from which sequence needs to be matched
     * @return true, if the code point array from specified index starts with
     *      character sequence represented by argument; false otherwise
     */
    public static boolean startsWith(int[] codePoints, String prefix, int fromIndex) {
        final int[] prefixCodePoints = prefix.codePoints().toArray();
        boolean flag = true;
        if (fromIndex < 0 || fromIndex > codePoints.length - prefixCodePoints.length) {
            flag = false;
        }
        if (flag) {
            int start = fromIndex;
            int index = 0;
            while (start < codePoints.length && index < prefix.length()) {
                if (prefixCodePoints[index] != codePoints[start]) {
                    flag = false;
                    break;
                }
                start += 1;
                index += 1;
            }
        }
        return flag;
    }

}
