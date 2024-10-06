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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_LINE_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_MISPLACED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck.MSG_PRECEDED_BLOCK_TAG;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocParagraphCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparagraph";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "21:4: " + getCheckMessage(MSG_MISPLACED_TAG),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "18:4: " + getCheckMessage(MSG_LINE_BEFORE),
            "20:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "23:4: " + getCheckMessage(MSG_MISPLACED_TAG),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "21:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "pre"),
            "24:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "20:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "h1"),
            "24:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "24:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "pre"),
            "27:4: " + getCheckMessage(MSG_MISPLACED_TAG),
            "27:4: " + getCheckMessage(MSG_PRECEDED_BLOCK_TAG, "ul"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}
