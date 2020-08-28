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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalIdentifierNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/illegalidentifiername";
    }

    @Test
    public void testGetAcceptableTokens() {
        final IllegalIdentifierNameCheck illegalIdentifierNameCheck =
            new IllegalIdentifierNameCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.PATTERN_VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_COMPONENT_DEF,
        };

        assertArrayEquals(expected, illegalIdentifierNameCheck.getAcceptableTokens(),
            "Default acceptable tokens are invalid");
    }

    @Test
    public void testGetRequiredTokens() {
        final IllegalIdentifierNameCheck illegalIdentifierNameCheck =
            new IllegalIdentifierNameCheck();
        final int[] expected = CommonUtil.EMPTY_INT_ARRAY;

        assertArrayEquals(expected, illegalIdentifierNameCheck.getRequiredTokens(),
            "Default required tokens are invalid");
    }

    @Test
    public void testIllegalIdentifierNameDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalIdentifierNameCheck.class);

        final String format = "(?i)^(?!(record|yield|var|permits|sealed|_)$).+$";

        final String[] expected = {
            "15:25: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "16:24: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "22:13: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "24:21: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "39:9: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "51:13: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "53:13: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "55:16: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "57:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Record", format),
            "58:25: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "68:37: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "68:52: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "68:69: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            };
        verify(checkConfig, getNonCompilablePath("InputIllegalIdentifierName.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameOpenTransitive() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalIdentifierNameCheck.class);
        checkConfig.addAttribute("format",
            "(?i)^(?!(record|yield|var|permits|sealed|open|transitive)$).+$");

        final String format = "(?i)^(?!(record|yield|var|permits|sealed|open|transitive)$).+$";

        final String[] expected = {
            "15:25: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "16:24: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "22:13: " + getCheckMessage(MSG_INVALID_PATTERN, "open", format),
            "24:21: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "39:9: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "51:13: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "53:13: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "55:16: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "57:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Record", format),
            "58:25: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "67:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Transitive", format),
            "70:37: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
            "70:56: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "70:72: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            };
        verify(checkConfig,
            getNonCompilablePath("InputIllegalIdentifierNameOpenTransitive.java"), expected);
    }

    @Test
    public void testIllegalIdentifierNameParameterReceiver() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalIdentifierNameCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
            getNonCompilablePath("InputIllegalIdentifierNameParameterReceiver.java"),
            expected);
    }

    @Test
    public void testIllegalIdentifierNameUnderscore() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalIdentifierNameCheck.class);
        final String format = "(?i)^(?!(record|yield|var|permits|sealed|_)$).+$";

        final String[] expected = {
            "11:12: " + getCheckMessage(MSG_INVALID_PATTERN, "_", format),
            };
        verify(checkConfig, getPath("InputIllegalIdentifierNameUnderscore.java"), expected);
    }
}
