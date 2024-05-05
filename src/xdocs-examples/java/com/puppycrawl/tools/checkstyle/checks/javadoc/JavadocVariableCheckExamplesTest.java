///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck.MSG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocVariableCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:15: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:15: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:15: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}
