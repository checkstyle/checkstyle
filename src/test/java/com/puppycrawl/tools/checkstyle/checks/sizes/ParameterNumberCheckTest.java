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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ParameterNumberCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/parameternumber";
    }

    @Test
    public void testGetRequiredTokens() {
        final ParameterNumberCheck checkObj = new ParameterNumberCheck();
        assertWithMessage("ParameterNumberCheck#getRequiredTokens should return empty array "
                + "by default")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ParameterNumberCheck paramNumberCheckObj =
            new ParameterNumberCheck();
        final int[] actual = paramNumberCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultOne()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberSimpleOne.java"), expected);
    }

    @Test
    public void testDefaultTwo()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberSimpleTwo.java"), expected);
    }

    @Test
    public void testDefaultThree()
            throws Exception {
        final String[] expected = {
            "48:10: " + getCheckMessage(MSG_KEY, 7, 9),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberSimpleThree.java"), expected);
    }

    @Test
    public void testNum()
            throws Exception {
        final String[] expected = {
            "76:9: " + getCheckMessage(MSG_KEY, 2, 3),
            "199:10: " + getCheckMessage(MSG_KEY, 2, 9),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberSimple2.java"), expected);
    }

    @Test
    public void testMaxParam()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberSimple3.java"), expected);
    }

    @Test
    public void shouldLogActualParameterNumber()
            throws Exception {
        final String[] expected = {
            "199:10: " + getCheckMessage(MSG_KEY, 7, 9),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberSimple4.java"), expected);
    }

    @Test
    public void testIgnoreOverriddenMethods()
            throws Exception {
        final String[] expected = {
            "16:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "21:10: " + getCheckMessage(MSG_KEY, 7, 8),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNumber.java"), expected);
    }

    @Test
    public void testIgnoreOverriddenMethodsFalse()
            throws Exception {
        final String[] expected = {
            "16:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "21:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "29:10: " + getCheckMessage(MSG_KEY, 7, 8),
            "34:10: " + getCheckMessage(MSG_KEY, 7, 8),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNumber2.java"), expected);
    }

    @Test
    public void testIgnoreAnnotatedBy() throws Exception {
        final String[] expected = {
            "26:10: " + getCheckMessage(MSG_KEY, 2, 3),
            "33:10: " + getCheckMessage(MSG_KEY, 2, 4),
            "38:14: " + getCheckMessage(MSG_KEY, 2, 3),
            "46:9: " + getCheckMessage(MSG_KEY, 2, 4),
            "61:30: " + getCheckMessage(MSG_KEY, 2, 3),
            "65:29: " + getCheckMessage(MSG_KEY, 2, 3),
            "80:34: " + getCheckMessage(MSG_KEY, 2, 4),
            "100:10: " + getCheckMessage(MSG_KEY, 2, 3),
            "109:14: " + getCheckMessage(MSG_KEY, 2, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberIgnoreAnnotatedBy.java"), expected);
    }

    @Test
    public void testIgnoreAnnotatedByFullyQualifiedClassName() throws Exception {
        final String[] expected = {
            "18:10: " + getCheckMessage(MSG_KEY, 2, 3),
            "20:10: " + getCheckMessage(MSG_KEY, 2, 3),
            "26:10: " + getCheckMessage(MSG_KEY, 2, 3),
            "30:10: " + getCheckMessage(MSG_KEY, 2, 3),
            "44:14: " + getCheckMessage(MSG_KEY, 2, 3),
            "48:14: " + getCheckMessage(MSG_KEY, 2, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputParameterNumberIgnoreAnnotatedByFullyQualifiedClassName.java"),
                expected);
    }
}
