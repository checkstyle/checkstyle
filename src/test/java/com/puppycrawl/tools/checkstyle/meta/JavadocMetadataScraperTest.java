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

package com.puppycrawl.tools.checkstyle.meta;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JavadocMetadataScraperTest {

    private static final String CHECK_PACKAGE_BASE =
            "com.puppycrawl.tools.checkstyle.meta.javadocmetadatascraper.checks.";

    private static Map<String, ModuleDetails> moduleDetailsStore;

    @BeforeAll
    public static void fillModuleDetailsStore() throws Exception {
        MetadataGeneratorUtil.generate(System.getProperty("user.dir")
                + "/src/test/resources/com/puppycrawl/tools/checkstyle"
                + "/meta/javadocmetadatascraper");
        moduleDetailsStore = JavadocMetadataScraper.getModuleDetailsStore();
    }

    @Test
    public void testPropertyNameWithNoCodeTag() {
        final String testCheck = "custom.InputJavadocMetadataScraperPropertyWithNoCodeTagCheck";
        final ModuleDetails testCheckMeta = moduleDetailsStore.get(CHECK_PACKAGE_BASE + testCheck);

        assertNull(testCheckMeta.getProperties().get(0).getName(),
                "Check with no property name {@code } tag should have null property name");
    }
}
