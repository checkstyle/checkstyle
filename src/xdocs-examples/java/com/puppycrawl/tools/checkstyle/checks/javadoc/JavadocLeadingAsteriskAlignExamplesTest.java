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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class JavadocLeadingAsteriskAlignExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocleadingasteriskalign";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY, 0, 1),
            "18:5: " + getCheckMessage(MSG_KEY, 2, 1),
            "28:3: " + getCheckMessage(MSG_KEY, 0, 1),
            "29:3: " + getCheckMessage(MSG_KEY, 0, 1),
            "39:1: " + getCheckMessage(MSG_KEY, -2, 1),
            "50:5: " + getCheckMessage(MSG_KEY, 0, 1),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16:2: " + getCheckMessage(MSG_KEY, 1, 0),
            "20:5: " + getCheckMessage(MSG_KEY, 2, 0),
            "21:4: " + getCheckMessage(MSG_KEY, 1, 0),
            "25:4: " + getCheckMessage(MSG_KEY, 1, 0),
            "26:4: " + getCheckMessage(MSG_KEY, 1, 0),
            "35:4: " + getCheckMessage(MSG_KEY, 1, 0),
            "36:4: " + getCheckMessage(MSG_KEY, 1, 0),
            "41:1: " + getCheckMessage(MSG_KEY, -2, 0),
            "45:4: " + getCheckMessage(MSG_KEY, 1, 0),
            "46:4: " + getCheckMessage(MSG_KEY, 1, 0),
            "53:6: " + getCheckMessage(MSG_KEY, 1, 0),
            "57:6: " + getCheckMessage(MSG_KEY, 1, 0),
            "58:6: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

}
