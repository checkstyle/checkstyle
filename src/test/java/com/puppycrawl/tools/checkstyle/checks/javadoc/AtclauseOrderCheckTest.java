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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AtclauseOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/atclauseorder";
    }

    @Test
    public void testGetAcceptableTokens() {
        final AtclauseOrderCheck checkObj = new AtclauseOrderCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final AtclauseOrderCheck checkObj = new AtclauseOrderCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testCorrect1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderCorrect1.java"), expected);
    }

    @Test
    public void testCorrect2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderCorrect2.java"), expected);
    }

    @Test
    public void testCorrect3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderCorrect3.java"), expected);
    }

    @Test
    public void testCorrect4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderCorrect4.java"), expected);
    }

    @Test
    public void testIncorrect1() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final String[] expected = {
            "21: " + getCheckMessage(MSG_KEY, tagOrder),
            "23: " + getCheckMessage(MSG_KEY, tagOrder),
            "24: " + getCheckMessage(MSG_KEY, tagOrder),
            "51: " + getCheckMessage(MSG_KEY, tagOrder),
            "62: " + getCheckMessage(MSG_KEY, tagOrder),
            "63: " + getCheckMessage(MSG_KEY, tagOrder),
            "64: " + getCheckMessage(MSG_KEY, tagOrder),
            "73: " + getCheckMessage(MSG_KEY, tagOrder),
            "80: " + getCheckMessage(MSG_KEY, tagOrder),
            "97: " + getCheckMessage(MSG_KEY, tagOrder),
            "98: " + getCheckMessage(MSG_KEY, tagOrder),
            "112: " + getCheckMessage(MSG_KEY, tagOrder),
            "113: " + getCheckMessage(MSG_KEY, tagOrder),
            "114: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrect1.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY, tagOrder),
            "25: " + getCheckMessage(MSG_KEY, tagOrder),
            "26: " + getCheckMessage(MSG_KEY, tagOrder),
            "37: " + getCheckMessage(MSG_KEY, tagOrder),
            "47: " + getCheckMessage(MSG_KEY, tagOrder),
            "48: " + getCheckMessage(MSG_KEY, tagOrder),
            "60: " + getCheckMessage(MSG_KEY, tagOrder),
            "61: " + getCheckMessage(MSG_KEY, tagOrder),
            "73: " + getCheckMessage(MSG_KEY, tagOrder),
            "74: " + getCheckMessage(MSG_KEY, tagOrder),
            "86: " + getCheckMessage(MSG_KEY, tagOrder),
            "89: " + getCheckMessage(MSG_KEY, tagOrder),
            "101: " + getCheckMessage(MSG_KEY, tagOrder),
            "112: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrect2.java"), expected);
    }

    @Test
    public void testIncorrect3() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY, tagOrder),
            "25: " + getCheckMessage(MSG_KEY, tagOrder),
            "26: " + getCheckMessage(MSG_KEY, tagOrder),
            "37: " + getCheckMessage(MSG_KEY, tagOrder),
            "45: " + getCheckMessage(MSG_KEY, tagOrder),
            "54: " + getCheckMessage(MSG_KEY, tagOrder),
            "68: " + getCheckMessage(MSG_KEY, tagOrder),
            "76: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrect3.java"), expected);
    }

    @Test
    public void testIncorrect4() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final String[] expected = {
            "21: " + getCheckMessage(MSG_KEY, tagOrder),
            "23: " + getCheckMessage(MSG_KEY, tagOrder),
            "24: " + getCheckMessage(MSG_KEY, tagOrder),
            "34: " + getCheckMessage(MSG_KEY, tagOrder),
            "42: " + getCheckMessage(MSG_KEY, tagOrder),
            "53: " + getCheckMessage(MSG_KEY, tagOrder),
            "55: " + getCheckMessage(MSG_KEY, tagOrder),
            "68: " + getCheckMessage(MSG_KEY, tagOrder),
            "69: " + getCheckMessage(MSG_KEY, tagOrder),
            "81: " + getCheckMessage(MSG_KEY, tagOrder),
            "83: " + getCheckMessage(MSG_KEY, tagOrder),
            "98: " + getCheckMessage(MSG_KEY, tagOrder),
            "100: " + getCheckMessage(MSG_KEY, tagOrder),
            "101: " + getCheckMessage(MSG_KEY, tagOrder),
            "111: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrect4.java"), expected);
    }

    @Test
    public void testIncorrectCustom1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrectCustom1.java"), expected);
    }

    @Test
    public void testIncorrectCustom2() throws Exception {
        final String tagOrder = "[@since, @version, @param, @return, @throws, @exception,"
                + " @deprecated, @see, @serial, @serialField, @serialData, @author]";
        final String[] expected = {
            "31: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrectCustom2.java"), expected);
    }

    @Test
    public void testIncorrectCustom3() throws Exception {
        final String tagOrder = "[@since, @version, @param, @return, @throws, @exception,"
                + " @deprecated, @see, @serial, @serialField, @serialData, @author]";
        final String[] expected = {
            "31: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrectCustom3.java"), expected);
    }

    @Test
    public void testIncorrectCustom4() throws Exception {
        final String tagOrder = "[@since, @version, @param, @return, @throws, @exception,"
                + " @deprecated, @see, @serial, @serialField, @serialData, @author]";
        final String[] expected = {
            "31: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderIncorrectCustom4.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void testAtclauseOrderRecords() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception,"
            + " @see, @since, @serial, @serialField, @serialData, @deprecated]";

        final String[] expected = {
            "40: " + getCheckMessage(MSG_KEY, tagOrder),
            "41: " + getCheckMessage(MSG_KEY, tagOrder),
            "42: " + getCheckMessage(MSG_KEY, tagOrder),
            "54: " + getCheckMessage(MSG_KEY, tagOrder),
            "55: " + getCheckMessage(MSG_KEY, tagOrder),
            "64: " + getCheckMessage(MSG_KEY, tagOrder),
            "85: " + getCheckMessage(MSG_KEY, tagOrder),
            "102: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderRecords.java"), expected);
    }

    @Test
    public void testMethodReturningArrayType() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final String[] expected = {
            "21: " + getCheckMessage(MSG_KEY, tagOrder),
            "35: " + getCheckMessage(MSG_KEY, tagOrder),
            "36: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderMethodReturningArrayType.java"), expected);
    }

    @Test
    public void testAtclauseOrderLotsOfRecords1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderLotsOfRecords1.java"), expected);
    }

    @Test
    public void testAtclauseOrderLotsOfRecords2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderLotsOfRecords2.java"), expected);
    }

    @Test
    public void testAtclauseOrderLotsOfRecords3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderLotsOfRecords3.java"), expected);
    }

    @Test
    public void testAtclauseOrderLotsOfRecords4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderLotsOfRecords4.java"), expected);
    }

    @Test
    public void testAtclause() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY, tagOrder),
            "23: " + getCheckMessage(MSG_KEY, tagOrder),
            "24: " + getCheckMessage(MSG_KEY, tagOrder),
            "37: " + getCheckMessage(MSG_KEY, tagOrder),
            "38: " + getCheckMessage(MSG_KEY, tagOrder),
            "39: " + getCheckMessage(MSG_KEY, tagOrder),
            "52: " + getCheckMessage(MSG_KEY, tagOrder),
            "53: " + getCheckMessage(MSG_KEY, tagOrder),
            "54: " + getCheckMessage(MSG_KEY, tagOrder),
            "68: " + getCheckMessage(MSG_KEY, tagOrder),
            "69: " + getCheckMessage(MSG_KEY, tagOrder),
            "70: " + getCheckMessage(MSG_KEY, tagOrder),
            "82: " + getCheckMessage(MSG_KEY, tagOrder),
            "83: " + getCheckMessage(MSG_KEY, tagOrder),
            "84: " + getCheckMessage(MSG_KEY, tagOrder),
            "99: " + getCheckMessage(MSG_KEY, tagOrder),
            "100: " + getCheckMessage(MSG_KEY, tagOrder),
            "101: " + getCheckMessage(MSG_KEY, tagOrder),
            "114: " + getCheckMessage(MSG_KEY, tagOrder),
            "115: " + getCheckMessage(MSG_KEY, tagOrder),
            "116: " + getCheckMessage(MSG_KEY, tagOrder),
        };

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderWithAnnotationsOutsideJavadoc.java"), expected);
    }

    @Test
    public void testNewArrayDeclaratorStructure() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";

        final String[] expected = {
            "42: " + getCheckMessage(MSG_KEY, tagOrder),
            "60: " + getCheckMessage(MSG_KEY, tagOrder),
            "83: " + getCheckMessage(MSG_KEY, tagOrder),
            "84: " + getCheckMessage(MSG_KEY, tagOrder),
            "85: " + getCheckMessage(MSG_KEY, tagOrder),
        };

        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrderNewArrayDeclaratorStructure.java"), expected);
    }

    @Test
    public void testTrim() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputAtclauseOrder1.java"),
                expected);
    }

}
