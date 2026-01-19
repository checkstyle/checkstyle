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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocTagContinuationIndentationCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctagcontinuationindentation";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY, 4),
            "39: " + getCheckMessage(MSG_KEY, 4),
            "40: " + getCheckMessage(MSG_KEY, 4),
            "41: " + getCheckMessage(MSG_KEY, 4),
            "42: " + getCheckMessage(MSG_KEY, 4),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "41: " + getCheckMessage(MSG_KEY, 2),
            "42: " + getCheckMessage(MSG_KEY, 2),
            "43: " + getCheckMessage(MSG_KEY, 2),
            "44: " + getCheckMessage(MSG_KEY, 2),
        };
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_KEY, 4),
            "40: " + getCheckMessage(MSG_KEY, 4),
            "41: " + getCheckMessage(MSG_KEY, 4),
            "42: " + getCheckMessage(MSG_KEY, 4),
            "43: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_KEY, 4),
            "38: " + getCheckMessage(MSG_KEY, 4),
            "39: " + getCheckMessage(MSG_KEY, 4),
            "40: " + getCheckMessage(MSG_KEY, 4),
            "41: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}
