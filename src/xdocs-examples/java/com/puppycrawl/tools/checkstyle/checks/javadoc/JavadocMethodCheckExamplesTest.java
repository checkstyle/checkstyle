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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMethodCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "16: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "16:30: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "23: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "28:25: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
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
            "15:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "18:30: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "30:25: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
            "18: " + getCheckMessage(MSG_RETURN_EXPECTED, "@return"),
            "18:30: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "30:25: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "x"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "30:17: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "FileNotFoundException"),
            "63:17: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "66:17: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "79:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }
}
