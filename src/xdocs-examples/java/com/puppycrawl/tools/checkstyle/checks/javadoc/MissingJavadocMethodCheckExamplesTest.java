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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck.MSG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class MissingJavadocMethodCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadocmethod";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Example1"),
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod1"),
            "32:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod6"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Example2"),
            "16:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod1"),
            "28:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod3"),
            "30:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod4"),
            "31:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod5"),
            "34:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod6"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Example3"),
            "17:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod1"),
            "29:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod3"),
            "32:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod5"),
            "35:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod6"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Example4"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Example5"),
            "16:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod1"),
            "34:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod6"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "Example6"),
            "16:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod1"),
            "34:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod6"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "34:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod6"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "16:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod1"),
            "34:3: " + getCheckMessage(MSG_JAVADOC_MISSING, "testMethod6"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

}
