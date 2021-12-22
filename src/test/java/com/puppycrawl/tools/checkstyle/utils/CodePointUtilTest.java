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

}
