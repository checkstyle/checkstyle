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
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import org.junit.jupiter.api.Test;

public class ApiUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor must be private")
                .that(isUtilsClassHasPrivateConstructor(ApiUtil.class))
                .isTrue();
    }

    @Test
    public void testLengthExpandedTabsAfterSupplementaryCharacter() {
        // the emoji is one code point, so the tab expands from column 1 to 8
        final String text = "\uD83D\uDE00" + "\tx";
        assertWithMessage("Invalid expanded tabs length")
            .that(ApiUtil.lengthExpandedTabs(text, 2, 8))
            .isEqualTo(8);
        assertWithMessage("Invalid expanded tabs length")
            .that(ApiUtil.lengthExpandedTabs(text, 3, 8))
            .isEqualTo(9);
    }

}
