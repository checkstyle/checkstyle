////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
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

        assertArrayEquals(expected, catchParameterNameCheck.getRequiredTokens(),
                "Default required tokens are invalid");
        assertArrayEquals(expected, catchParameterNameCheck.getAcceptableTokens(),
                "Default acceptable tokens are invalid");
    }

    @Test
    public void testDefaultConfigurationOnCorrectFile() throws Exception {
        final Configuration checkConfig = createModuleConfig(CatchParameterNameCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputCatchParameterNameSimple.java"), expected);
    }

    @Test
    public void testDefaultConfigurationOnFileWithViolations() throws Exception {
        final Configuration checkConfig = createModuleConfig(CatchParameterNameCheck.class);
        final String defaultFormat = "^(e|t|ex|[a-z][a-z][a-zA-Z]+)$";

        final String[] expected = {
            "18:28: " + getCheckMessage(MSG_INVALID_PATTERN, "exception1", defaultFormat),
            "28:39: " + getCheckMessage(MSG_INVALID_PATTERN, "ie", defaultFormat),
            "31:28: " + getCheckMessage(MSG_INVALID_PATTERN, "iException", defaultFormat),
            "34:28: " + getCheckMessage(MSG_INVALID_PATTERN, "ok", defaultFormat),
            "38:28: " + getCheckMessage(MSG_INVALID_PATTERN, "e1", defaultFormat),
            "40:32: " + getCheckMessage(MSG_INVALID_PATTERN, "e2", defaultFormat),
            "44:28: " + getCheckMessage(MSG_INVALID_PATTERN, "t1", defaultFormat),
            "46:32: " + getCheckMessage(MSG_INVALID_PATTERN, "t2", defaultFormat),
        };

        verify(checkConfig, getPath("InputCatchParameterName.java"), expected);
    }

    @Test
    public void testCustomFormatFromJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CatchParameterNameCheck.class);
        checkConfig.addAttribute("format", "^[a-z][a-zA-Z0-9]+$");

        final String[] expected = {
            "6:28: " + getCheckMessage(MSG_INVALID_PATTERN, "e", "^[a-z][a-zA-Z0-9]+$"),
            "24:28: " + getCheckMessage(MSG_INVALID_PATTERN, "t", "^[a-z][a-zA-Z0-9]+$"),
        };

        verify(checkConfig, getPath("InputCatchParameterName.java"), expected);
    }

    @Test
    public void testCustomFormatWithNoAnchors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(CatchParameterNameCheck.class);
        checkConfig.addAttribute("format", "[a-z]");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputCatchParameterName.java"), expected);
    }

}
