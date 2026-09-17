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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck.MSG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocVariableCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "a"),
            "18:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "c"),
            "19:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "d"),
            "20:15: " + getCheckMessage(MSG_JAVADOC_MISSING, "e"),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "d"),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "a"),
            "20:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "c"),
            "21:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "d"),
            "22:15: " + getCheckMessage(MSG_JAVADOC_MISSING, "e"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "a"),
            "20:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "c"),
            "21:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "d"),
            "22:15: " + getCheckMessage(MSG_JAVADOC_MISSING, "e"),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "a"),
            "22:15: " + getCheckMessage(MSG_JAVADOC_MISSING, "e"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "variablePublic"),
            "17:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "variableProtected"),
            "19:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "variablePackage"),
            "21:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "variablePrivate"),
            "24:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
        };

        verifyWithInlineConfigParser(getPath("UseCase2.java"), expected);
    }

}
