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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import org.junit.jupiter.api.Test;

public class NullUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(NullUtil.class))
                .isTrue();
    }

    @Test
    public void testNotNullWithNonNullValue() {
        final String value = "test";
        final String result = NullUtil.notNull(value);
        assertWithMessage("Result should equal input value")
                .that(result)
                .isEqualTo(value);
    }

    @Test
    public void testNotNullWithNullValue() {
        final AssertionError ex = getExpectedThrowable(AssertionError.class, () -> {
            NullUtil.notNull(null);
        });
        assertWithMessage("Error should indicate misuse")
                .that(ex)
                .hasMessageThat()
                .contains("Misuse of notNull");
    }
}
