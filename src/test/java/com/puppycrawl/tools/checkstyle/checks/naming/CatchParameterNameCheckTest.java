///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class CatchParameterNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/catchparametername";
    }

    @Test
    public void testTokens() {
        final CatchParameterNameCheck catchParameterNameCheck = new CatchParameterNameCheck();
        final int[] expected = {TokenTypes.PARAMETER_DEF};

        assertWithMessage("Default required tokens are invalid")
            .that(catchParameterNameCheck.getRequiredTokens())
            .isEqualTo(expected);
        assertWithMessage("Default acceptable tokens are invalid")
            .that(catchParameterNameCheck.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultConfigurationOnCorrectFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputCatchParameterNameSimple.java"), expected);
    }

    @Test
    public void testDefaultConfigurationOnFileWithViolations() throws Exception {
        final String defaultFormat = "^(e|t|ex|[a-z][a-z][a-zA-Z]+)$";

        final String[] expected = {
            "25:28: " + getCheckMessage(MSG_INVALID_PATTERN, "exception1", defaultFormat),
            "35:39: " + getCheckMessage(MSG_INVALID_PATTERN, "ie", defaultFormat),
            "38:28: " + getCheckMessage(MSG_INVALID_PATTERN, "iException", defaultFormat),
            "41:28: " + getCheckMessage(MSG_INVALID_PATTERN, "ok", defaultFormat),
            "45:28: " + getCheckMessage(MSG_INVALID_PATTERN, "e1", defaultFormat),
            "47:32: " + getCheckMessage(MSG_INVALID_PATTERN, "e2", defaultFormat),
            "51:28: " + getCheckMessage(MSG_INVALID_PATTERN, "t1", defaultFormat),
            "53:32: " + getCheckMessage(MSG_INVALID_PATTERN, "t2", defaultFormat),
        };

        verifyWithInlineConfigParser(
                getPath("InputCatchParameterName.java"), expected);
    }

    @Test
    public void testCustomFormatFromJavadoc() throws Exception {

        final String[] expected = {
            "13:28: " + getCheckMessage(MSG_INVALID_PATTERN, "e", "^[a-z][a-zA-Z0-9]+$"),
            "31:28: " + getCheckMessage(MSG_INVALID_PATTERN, "t", "^[a-z][a-zA-Z0-9]+$"),
        };

        verifyWithInlineConfigParser(
                getPath("InputCatchParameterName2.java"), expected);
    }

    @Test
    public void testCustomFormatWithNoAnchors() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputCatchParameterName3.java"), expected);
    }

}
