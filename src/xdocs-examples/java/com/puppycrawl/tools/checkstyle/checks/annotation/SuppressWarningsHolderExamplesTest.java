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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck;

public class SuppressWarningsHolderExamplesTest extends AbstractExamplesModuleTestSupport {

    /**
     * Creates a new {@code SuppressWarningsHolderExamplesTest} instance.
     */
    public SuppressWarningsHolderExamplesTest() {
        // no code by default
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/suppresswarningsholder";
    }

    @Test
    public void testExample1() throws Exception {
        final String pattern1 = "^[a-z][a-zA-Z0-9]*$";
        final String[] expected = {
            "20:15: " + getCheckMessage(MemberNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "K", pattern1),
            "28:15: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                        NoWhitespaceAfterCheck.MSG_KEY, "int"),
            "28:18: " + getCheckMessage(MemberNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "ARR", pattern1),
            "34:17: " + getCheckMessage(ParameterNumberCheck.class,
                        ParameterNumberCheck.MSG_KEY, 7, 8),

        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String pattern1 = "^[a-z][a-zA-Z0-9]*$";
        final String[] expected = {
            "24:15: " + getCheckMessage(MemberNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "K", pattern1),
            "32:15: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                        NoWhitespaceAfterCheck.MSG_KEY, "int"),
            "32:18: " + getCheckMessage(MemberNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "ARR", pattern1),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "29:15: " + getCheckMessage(ParameterNumberCheck.class,
                        ParameterNumberCheck.MSG_KEY, 7, 8),
            "35:15: " + getCheckMessage(ParameterNumberCheck.class,
                        ParameterNumberCheck.MSG_KEY, 7, 8),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {
            "29:15: " + getCheckMessage(ParameterNumberCheck.class,
                        ParameterNumberCheck.MSG_KEY, 7, 8),
            "35:15: " + getCheckMessage(ParameterNumberCheck.class,
                        ParameterNumberCheck.MSG_KEY, 7, 8),
        };

        verifyWithInlineConfigParser(getPath("UseCase2.java"), expected);
    }

}
