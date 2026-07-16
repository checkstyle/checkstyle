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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMethodCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    /**
     * Creates a new {@code JavadocMethodCheckExamplesTest} instance.
     */
    public JavadocMethodCheckExamplesTest() {
        // no code by default
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "19: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "19:21: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "29: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "35:15: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "40: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "43: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "21:21: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "37:15: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "21: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "21:21: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "37:15: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "42: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "21: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "21:21: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "21:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "31: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "37:15: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "42: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "21: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "21:21: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "31: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "37:15: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };
        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

}
