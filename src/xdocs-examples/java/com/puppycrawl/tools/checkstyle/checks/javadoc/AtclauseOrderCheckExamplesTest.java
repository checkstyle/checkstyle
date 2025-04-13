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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class AtclauseOrderCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/atclauseorder";
    }

    @Test
    public void testExample1() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws"
            + ", @exception, @see,"
            + " @since, @serial, @serialField, @serialData, @deprecated]";

        final String[] expected = {
            "42: " + getCheckMessage(MSG_KEY, tagOrder),
            "44: " + getCheckMessage(MSG_KEY, tagOrder),
            "52: " + getCheckMessage(MSG_KEY, tagOrder),
            "54: " + getCheckMessage(MSG_KEY, tagOrder),
            "55: " + getCheckMessage(MSG_KEY, tagOrder),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String tagOrder = "[@author, @since, @version, @param, @return"
            + ", @throws, @exception,"
            + " @deprecated, @see, @serial, @serialField, @serialData]";

        final String[] expected = {
            "29: " + getCheckMessage(MSG_KEY, tagOrder),
            "55: " + getCheckMessage(MSG_KEY, tagOrder),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String tagOrder = "[@author, @since, @version, @param, @return"
            + ", @throws, @exception,"
            + " @deprecated, @see, @serial, @serialField, @serialData]";

        final String[] expected = {
            "55: " + getCheckMessage(MSG_KEY, tagOrder),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}
