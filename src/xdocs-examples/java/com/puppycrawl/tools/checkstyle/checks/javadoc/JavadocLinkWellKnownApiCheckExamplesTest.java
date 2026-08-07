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

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocLinkWellKnownApiCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoclinkwellknownapi";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
            "29:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.lang.String"),
            "34:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
            "22:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "Integer"),
            "27:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.util.List"),
            "32:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.lang.String"),
            "37:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
            "42:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "Object"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
            "22:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "Integer"),
            "32:13: " + getCheckMessage(MSG_WELL_KNOWN_PACKAGE, "java.lang.String"),
            "37:13: " + getCheckMessage(MSG_WELL_KNOWN_API, "String"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

}
