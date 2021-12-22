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

}
