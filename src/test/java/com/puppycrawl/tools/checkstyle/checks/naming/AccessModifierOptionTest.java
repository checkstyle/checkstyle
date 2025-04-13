///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

public class AccessModifierOptionTest {

    @Test
    public void testDefaultCase() {
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PUBLIC.name())
                .isEqualTo("PUBLIC");
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PROTECTED.name())
                .isEqualTo("PROTECTED");
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PACKAGE.name())
                .isEqualTo("PACKAGE");
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PRIVATE.name())
                .isEqualTo("PRIVATE");
    }

    @Test
    public void testCase() {
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PUBLIC.toString())
                .isEqualTo("public");
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PROTECTED.toString())
                .isEqualTo("protected");
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PACKAGE.toString())
                .isEqualTo("package");
        assertWithMessage("Case mismatch.")
                .that(AccessModifierOption.PRIVATE.toString())
                .isEqualTo("private");
    }

}
