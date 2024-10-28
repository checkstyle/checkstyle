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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.ParameterPlacementCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ParameterPlacementCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/parameterplacement";
    }

    @Test
    public void testGetRequiredTokens() {
        final ParameterPlacementCheck checkObj = new ParameterPlacementCheck();
        assertWithMessage("ParameterPlacementCheck#getRequiredTokens should return empty array "
                + "by default")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ParameterPlacementCheck paramNumberCheckObj =
            new ParameterPlacementCheck();
        final int[] actual = paramNumberCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.METHOD_CALL,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_NEW,
            TokenTypes.ANNOTATION,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testOwnLine()
            throws Exception {
        final String[] expected = {
            "19:36: " + getCheckMessage(MSG_KEY),
            "29:36: " + getCheckMessage(MSG_KEY),
            "34:36: " + getCheckMessage(MSG_KEY),
            "42:16: " + getCheckMessage(MSG_KEY),
            "59:34: " + getCheckMessage(MSG_KEY),
            "69:19: " + getCheckMessage(MSG_KEY),
            "74:20: " + getCheckMessage(MSG_KEY),
            "80:20: " + getCheckMessage(MSG_KEY),
            "82:20: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterPlacementOwnLine.java"), expected);
    }

    @Test
    public void testOwnLineAllowSingleLine()
            throws Exception {
        final String[] expected = {
            "32:51: " + getCheckMessage(MSG_KEY),
            "40:20: " + getCheckMessage(MSG_KEY),
            "70:20: " + getCheckMessage(MSG_KEY),
            "76:20: " + getCheckMessage(MSG_KEY),
            "78:20: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterPlacementOwnLineAllowSingleLine.java"), expected);
    }

    @Test
    public void testSeparateLine()
            throws Exception {
        final String[] expected = {
            "28:48: " + getCheckMessage(MSG_KEY),
            "40:20: " + getCheckMessage(MSG_KEY),
            "66:26: " + getCheckMessage(MSG_KEY),
            "71:27: " + getCheckMessage(MSG_KEY),
            "78:20: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterPlacementSeparateLine.java"), expected);
    }

    @Test
    public void testSeparateLineAllowSingleLine()
            throws Exception {
        final String[] expected = {
            "36:20: " + getCheckMessage(MSG_KEY),
            "52:25: " + getCheckMessage(MSG_KEY),
            "62:25: " + getCheckMessage(MSG_KEY),
            "69:27: " + getCheckMessage(MSG_KEY),
            "76:20: " + getCheckMessage(MSG_KEY),
            "81:19: " + getCheckMessage(MSG_KEY),
            "88:20: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterPlacementSeparateLineAllowSingleLine.java"), expected);
    }

    @Test
    public void testRecordOwnLine()
            throws Exception {
        final String[] expected = {
            "15:23: " + getCheckMessage(MSG_KEY),
            "19:20: " + getCheckMessage(MSG_KEY),
            "22:23: " + getCheckMessage(MSG_KEY),
            "29:20: " + getCheckMessage(MSG_KEY),
            "31:20: " + getCheckMessage(MSG_KEY),
            "35:23: " + getCheckMessage(MSG_KEY),
            "40:23: " + getCheckMessage(MSG_KEY),
            "45:23: " + getCheckMessage(MSG_KEY),
            "47:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParameterPlacementRecordOwnLine.java"), expected);
    }

    @Test
    public void testRecordOwnLineAllowSingleLine()
            throws Exception {
        final String[] expected = {
            "20:23: " + getCheckMessage(MSG_KEY),
            "27:20: " + getCheckMessage(MSG_KEY),
            "29:20: " + getCheckMessage(MSG_KEY),
            "33:23: " + getCheckMessage(MSG_KEY),
            "38:23: " + getCheckMessage(MSG_KEY),
            "43:23: " + getCheckMessage(MSG_KEY),
            "45:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParameterPlacementRecordOwnLineAllowSingleLine.java"),
                expected);
    }

    @Test
    public void testRecordSeparateLine()
            throws Exception {
        final String[] expected = {
            "15:30: " + getCheckMessage(MSG_KEY),
            "19:20: " + getCheckMessage(MSG_KEY),
            "28:20: " + getCheckMessage(MSG_KEY),
            "30:20: " + getCheckMessage(MSG_KEY),
            "38:31: " + getCheckMessage(MSG_KEY),
            "44:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParameterPlacementRecordSeparateLine.java"), expected);
    }

    @Test
    public void testRecordSeparateLineAllowSingleLine()
            throws Exception {
        final String[] expected = {
            "26:20: " + getCheckMessage(MSG_KEY),
            "28:20: " + getCheckMessage(MSG_KEY),
            "36:31: " + getCheckMessage(MSG_KEY),
            "42:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParameterPlacementRecordSeparateLine"
                        + "AllowSingleLine.java"),
                expected);
    }

}
