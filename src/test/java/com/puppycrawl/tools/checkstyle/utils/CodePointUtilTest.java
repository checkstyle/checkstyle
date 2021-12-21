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

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;

public class CodePointUtilTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/codepointutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(CodePointUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testGetNoOfCodeUnits() {
        final int[] codePoints = "😂🤣🤩".codePoints().toArray();
        assertEquals(6, CodePointUtil.getNoOfCodeUnits(codePoints),
                "Invalid parameter provided");
    }

    @Test
    public void testSubArray() {
        final int[] codePoints = "😂🤣🤩🤨🎄🙃😎".codePoints().toArray();
        final int[] expected1 = "😂🤣🤩".codePoints().toArray();
        assertEquals(Arrays.toString(expected1),
                Arrays.toString(CodePointUtil.subArray(codePoints, 0, 3)),
                "Invalid begin and end index");
        final int[] expected2 = "🤨🎄🙃".codePoints().toArray();
        assertEquals(Arrays.toString(expected2),
                Arrays.toString(CodePointUtil.subArray(codePoints, 3, 6)),
                "Invalid begin and end index");
        final int[] expected3 = "".codePoints().toArray();
        assertEquals(Arrays.toString(expected3),
                Arrays.toString(CodePointUtil.subArray(codePoints, 0, 0)),
                "Invalid begin and end index");
    }

    @Test
    public void testTrim() {
        final int[] whitespaceArray1 = "   trim".codePoints().toArray();
        final int[] expected1 = "trim".codePoints().toArray();
        assertEquals(Arrays.toString(expected1),
                Arrays.toString(CodePointUtil.trim(whitespaceArray1)),
                "Invalid array provided");
        final int[] whitespaceArray2 = "   trim   ".codePoints().toArray();
        assertEquals(Arrays.toString(expected1),
                Arrays.toString(CodePointUtil.trim(whitespaceArray2)),
                "Invalid array provided");
        final int[] whitespaceArray3 = " tr im ".codePoints().toArray();
        final int[] expected2 = "tr im".codePoints().toArray();
        assertEquals(Arrays.toString(expected2),
                Arrays.toString(CodePointUtil.trim(whitespaceArray3)),
                "Invalid array provided");
        final int[] whitespaceArray4 = "  ".codePoints().toArray();
        final int[] expected3 = "".codePoints().toArray();
        assertEquals(Arrays.toString(expected3),
                Arrays.toString(CodePointUtil.trim(whitespaceArray4)),
                "Invalid array provided");
    }

    @Test
    public void testIndexOfCharacter() {
        final int[] input = "test case".codePoints().toArray();
        assertEquals(4, CodePointUtil.indexOfCharacter(input, 2, ' '),
                "Invalid index provided");
        assertEquals(-1, CodePointUtil.indexOfCharacter(input, 5, ' '),
                "Invalid index provided");
        assertEquals(-1, CodePointUtil.indexOfCharacter(input, 9, ' '),
                "Invalid index provided");
        final int[] input2 = "test case😃&".codePoints().toArray();
        assertEquals(10, CodePointUtil.indexOfCharacter(input2, 7, '&'),
                "Invalid index provided");

    }

    @Test
    public void testStartsWith() {
        final int[] input = "// comment".codePoints().toArray();
        assertTrue(CodePointUtil.startsWith(input, "/", 0),
                "Should return true when elements from fromIndex matches prefix.");
        assertFalse(CodePointUtil.startsWith(input, "/*", 0),
                "Should return false when elements from fromIndex doesn't matches prefix.");
        assertFalse(CodePointUtil.startsWith(input, "/", -1),
                "Should return false when fromIndex is not a valid index");
        assertFalse(CodePointUtil.startsWith(input, "/", 9),
                "Should return false when fromIndex is not a valid index");
    }

}
