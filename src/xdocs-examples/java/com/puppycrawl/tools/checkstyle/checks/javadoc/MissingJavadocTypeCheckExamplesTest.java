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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck.MSG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class MissingJavadocTypeCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "13:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "A"),
            "29:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "F"),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "A"),
            "17:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
            "19:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "C"),
            "23:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "App"),
            "26:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Config"),
            "31:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "F"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "18:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
            "24:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "App"),
            "27:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Config"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "22:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "A"),
            "24:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
            "26:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "C"),
            "38:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "F"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "31:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "F"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "32:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Class3"),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

}
