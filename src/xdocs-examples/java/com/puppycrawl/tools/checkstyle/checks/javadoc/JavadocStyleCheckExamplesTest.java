///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_INCOMPLETE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_NO_PERIOD;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocStyleCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "11: " + getCheckMessage(MSG_NO_PERIOD),
            "30: " + getCheckMessage(MSG_NO_PERIOD),
            "36: " + getCheckMessage(MSG_NO_PERIOD),
            "42: " + getCheckMessage(MSG_NO_PERIOD),
            "49: " + getCheckMessage(MSG_NO_PERIOD),
            "51: " + getCheckMessage(MSG_INCOMPLETE_TAG, "   * <p"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_NO_PERIOD),
            "38: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "33: " + getCheckMessage(MSG_NO_PERIOD),
            "45: " + getCheckMessage(MSG_NO_PERIOD),
            "52: " + getCheckMessage(MSG_NO_PERIOD),
            "54: " + getCheckMessage(MSG_INCOMPLETE_TAG, "   * <p"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "53: " + getCheckMessage(MSG_INCOMPLETE_TAG, "   * <p"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_NO_PERIOD),
            "32: " + getCheckMessage(MSG_NO_PERIOD),
            "38: " + getCheckMessage(MSG_NO_PERIOD),
            "44: " + getCheckMessage(MSG_NO_PERIOD),
            "51: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }
}
