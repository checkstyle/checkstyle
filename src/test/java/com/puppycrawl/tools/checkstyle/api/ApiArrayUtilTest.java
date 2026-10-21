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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import org.junit.jupiter.api.Test;

public class ApiArrayUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor must be private")
                .that(isUtilsClassHasPrivateConstructor(ApiArrayUtil.class))
                .isTrue();
    }

    @Test
    public void testCopyOfArray() {
        final String[] original = {"first", "second"};
        final String[] copy = ApiArrayUtil.copyOfArray(original, original.length);
        assertWithMessage("The copy must be independent")
                .that(copy)
                .isNotSameInstanceAs(original);
        assertWithMessage("The runtime array type must be preserved")
                .that(copy.getClass())
                .isEqualTo(String[].class);
        original[0] = "changed";
        assertWithMessage("Changes to the source must not affect the copy")
                .that(copy)
                .asList()
                .containsExactly("first", "second")
                .inOrder();
        copy[1] = "updated";
        assertWithMessage("Changes to the copy must not affect the source")
                .that(original)
                .asList()
                .containsExactly("changed", "second")
                .inOrder();
    }

    @Test
    public void testCopyOfArrayWithDifferentLengths() {
        final String[] original = {"first", "second"};
        assertWithMessage("A shorter copy must truncate")
                .that(ApiArrayUtil.copyOfArray(original, 1))
                .asList()
                .containsExactly("first");
        assertWithMessage("A longer copy must pad with null")
                .that(ApiArrayUtil.copyOfArray(original, 3))
                .asList()
                .containsExactly("first", "second", null)
                .inOrder();
        assertWithMessage("An empty copy must be independent")
                .that(ApiArrayUtil.copyOfArray(original, 0))
                .isEmpty();
    }

    @Test
    public void testInvalidCopyOfArrayArguments() {
        final NullPointerException nullArray = getExpectedThrowable(NullPointerException.class,
                () -> ApiArrayUtil.copyOfArray(null, 0));
        assertWithMessage("A null array must be rejected")
                .that(nullArray.getClass())
                .isEqualTo(NullPointerException.class);
        final NegativeArraySizeException negativeLength = getExpectedThrowable(
                NegativeArraySizeException.class,
                () -> ApiArrayUtil.copyOfArray(new Object[0], -1));
        assertWithMessage("A negative length must be rejected")
                .that(negativeLength.getClass())
                .isEqualTo(NegativeArraySizeException.class);
    }

}
