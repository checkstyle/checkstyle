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

import java.io.Closeable;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;

public class StringUtilTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/stringutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        Assertions.assertTrue(isUtilsClassHasPrivateConstructor(StringUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testGetNoOfCodeUnits() {
        final int[] codePoints = {128514, 129315, 129321};
        Assertions.assertEquals(6, StringUtil.getNoOfCodeUnits(codePoints));
    }

    @Test
    public void testSubArray() {
        final int[] codePoints = {128514, 129315, 129321, 129320, 127876, 128579, 128526};
        final int[] expected1 = {128514, 129315, 129321};
        Assertions.assertEquals(Arrays.toString(expected1),
                Arrays.toString(StringUtil.subArray(codePoints, 0, 3)));
        final int[] expected2 = {129320, 127876, 128579};
        Assertions.assertEquals(Arrays.toString(expected2),
                Arrays.toString(StringUtil.subArray(codePoints, 3, 6)));
        final int[] expected3 = {};
        Assertions.assertEquals(Arrays.toString(expected3),
                Arrays.toString(StringUtil.subArray(codePoints, 0, 0)));
    }

    @Test
    public void testTrim() {
        final int[] whitespaceArray1 = {32, 32, 32, 116, 114, 105, 109};
        final int[] expected1 = {116, 114, 105, 109};
        Assertions.assertEquals(Arrays.toString(expected1),
                Arrays.toString(StringUtil.trim(whitespaceArray1)));
        final int[] whitespaceArray2 = {32, 32, 32, 116, 114, 105, 109, 32, 32, 32};
        final int[] expected2 = {116, 114, 105, 109};
        Assertions.assertEquals(Arrays.toString(expected2),
                Arrays.toString(StringUtil.trim(whitespaceArray2)));
    }

    @Test
    public void testIndexOfCharacter() {
        final int[] input1 = {116, 32, 115, 116, 32, 99, 97, 115, 101};
        Assertions.assertEquals(4, StringUtil.indexOfCharacter(input1, 2, ' '));
        final int[] input2 = {102, 105, 110, 100, 38, 97, 102, 116, 101, 114, 53};
        Assertions.assertEquals(-1, StringUtil.indexOfCharacter(input2, 5, '&'));
    }

    @Test
    public void testStartsWith() {
        final int[] input1 = {47, 47, 32, 99, 111, 109, 109, 101, 110, 116};
        Assertions.assertTrue(StringUtil.startsWith(input1, "/", 0),
                "Should return true when elements from fromIndex matches prefix.");
        Assertions.assertFalse(StringUtil.startsWith(input1, "/*", 0),
                "Should return false when elements from fromIndex doesn't matches prefix.");
        Assertions.assertFalse(StringUtil.startsWith(input1, "/", -1),
                "Should return false when fromIndex is not a valid index");
    }

    private static class TestCloseable implements Closeable {

        private boolean closed;

        @Override
        public void close() {
            closed = true;
        }

    }
}
