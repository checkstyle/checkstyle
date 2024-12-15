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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNKNOWN_TAG;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocTypeCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "28:6: " + getCheckMessage(MSG_UNKNOWN_TAG, "unknownTag"),
            "36:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "39:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "30:6: " + getCheckMessage(MSG_UNKNOWN_TAG, "unknownTag"),
            "38:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "30:6: " + getCheckMessage(MSG_UNKNOWN_TAG, "unknownTag"),
            "38:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "41:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "30:6: " + getCheckMessage(MSG_UNKNOWN_TAG, "unknownTag"),
            "38:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "41:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),

        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "42:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "30:6: " + getCheckMessage(MSG_UNKNOWN_TAG, "unknownTag"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "38:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "41:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "30:6: " + getCheckMessage(MSG_UNKNOWN_TAG, "unknownTag"),
            "38:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "41:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "44:3: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }
}
