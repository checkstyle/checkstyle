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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLinkWellKnownApiCheck.MSG_WELL_KNOWN_API;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLinkWellKnownApiCheck.MSG_WELL_KNOWN_PACKAGE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class JavadocLinkWellKnownApiCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoclinkwellknownapi";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
            "28:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.lang.String"),
            "34:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
            "46:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.lang.Class"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkWellKnownApi.java"), expected);
    }

    @Test
    public void testCustomProperties() throws Exception {
        final String[] expected = {
            "24:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "Integer"),
            "30:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "Object"),
            "36:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.util.List"),
            "42:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.io.File"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkWellKnownApiCustom.java"), expected);
    }

}
