///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileImportControlTest {

    private final PkgImportControl root = new PkgImportControl("com.kazgroup.courtlink",
            false, MismatchStrategy.FORBIDDEN);

    private final FileImportControl fileNode = new FileImportControl(root, "MyClass",
            false);
    private final FileImportControl fileRegexpNode = new FileImportControl(root, ".*Other.*",
            true);

    @BeforeEach
    public void setUp() {
        root.addChild(fileNode);
        root.addChild(fileRegexpNode);

        root.addImportRule(
            new PkgImportRule(false, false, "org.springframework", false, false));
        root.addImportRule(
            new PkgImportRule(false, false, "org.hibernate", false, false));
        root.addImportRule(
            new PkgImportRule(true, false, "org.apache.commons", false, false));
    }

    @Test
    public void testLocateFinest() {
        assertWithMessage("Unexpected response")
            .that(root.locateFinest("com.kazgroup.courtlink.domain", "Random"))
            .isEqualTo(root);
        assertWithMessage("Unexpected response")
            .that(root.locateFinest("com.kazgroup.courtlink.common.api", "MyClass"))
            .isEqualTo(fileNode);
        assertWithMessage("Unexpected response")
            .that(root.locateFinest("com.kazgroup.courtlink.common.api", "SomeOtherName"))
            .isEqualTo(fileRegexpNode);
        assertWithMessage("Unexpected response")
            .that(root.locateFinest("com.kazgroup.courtlink", null))
            .isEqualTo(root);
    }

}
