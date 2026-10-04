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
import static com.puppycrawl.tools.checkstyle.checks.coding.ExceptionWrappingShouldUseContextCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ExceptionWrappingShouldUseContextCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/exceptionwrappingshouldusecontext";
    }

    @Test
    public void testGetRequiredTokens() {
        final ExceptionWrappingShouldUseContextCheck check =
                new ExceptionWrappingShouldUseContextCheck();
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
            "76:13: " + getCheckMessage(MSG_KEY),
            "87:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionWrappingShouldUseContext.java"),
                expected);
    }

    @Test
    public void testEdgeCases1() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_KEY),
            "57:13: " + getCheckMessage(MSG_KEY),
            "77:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionWrappingShouldUseContextEdgeCases1.java"),
                expected);
    }

    @Test
    public void testEdgeCases2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputExceptionWrappingShouldUseContextEdgeCases2.java"),
                expected);
    }

    @Test
    public void testEdgeCases3() throws Exception {
        final String[] expected = {
            "46:17: " + getCheckMessage(MSG_KEY),
            "58:17: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionWrappingShouldUseContextEdgeCases3.java"),
                expected);
    }

    @Test
    public void testRecord() throws Exception {
        final String[] expected = {
            "33:17: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputExceptionWrappingShouldUseContextRecord.java"),
                expected);
    }

}
