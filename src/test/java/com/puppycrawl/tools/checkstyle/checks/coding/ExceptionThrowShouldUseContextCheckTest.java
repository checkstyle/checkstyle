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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.ExceptionThrowShouldUseContextCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ExceptionThrowShouldUseContextCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/exceptionthrowshouldusecontext";
    }

    @Test
    public void testGetRequiredTokens() {
        final ExceptionThrowShouldUseContextCheck check =
                new ExceptionThrowShouldUseContextCheck();
        final int[] expected = {
            TokenTypes.LITERAL_CATCH,
        };
        assertWithMessage("Required tokens are invalid")
                .that(check.getRequiredTokens())
                .isEqualTo(expected);
        assertWithMessage("Acceptable tokens are invalid")
                .that(check.getAcceptableTokens())
                .isEqualTo(expected);
        assertWithMessage("Default tokens are invalid")
                .that(check.getDefaultTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_KEY),
            "46:13: " + getCheckMessage(MSG_KEY),
            "51:13: " + getCheckMessage(MSG_KEY),
            "64:13: " + getCheckMessage(MSG_KEY),
            "75:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionThrowShouldUseContext.java"),
                expected);
    }

    @Test
    public void testEdgeCases1() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_KEY),
            "54:13: " + getCheckMessage(MSG_KEY),
            "68:13: " + getCheckMessage(MSG_KEY),
            "105:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionThrowShouldUseContextEdgeCases1.java"),
                expected);
    }

    @Test
    public void testEdgeCases2() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionThrowShouldUseContextEdgeCases2.java"),
                expected);
    }

    @Test
    public void testEdgeCases3() throws Exception {
        final String[] expected = {
            "46:17: " + getCheckMessage(MSG_KEY),
            "58:17: " + getCheckMessage(MSG_KEY),
            "89:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionThrowShouldUseContextEdgeCases3.java"),
                expected);
    }

    @Test
    public void testRecord() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionThrowShouldUseContextRecord.java"),
                expected);
    }

}
