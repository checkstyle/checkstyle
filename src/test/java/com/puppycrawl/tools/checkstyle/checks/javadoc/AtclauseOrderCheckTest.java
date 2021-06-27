////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AtclauseOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
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
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputAtclauseOrderCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        final String[] expected = {
            "20: " + getCheckMessage(MSG_KEY, tagOrder),
            "22: " + getCheckMessage(MSG_KEY, tagOrder),
            "23: " + getCheckMessage(MSG_KEY, tagOrder),
            "51: " + getCheckMessage(MSG_KEY, tagOrder),
            "61: " + getCheckMessage(MSG_KEY, tagOrder),
            "62: " + getCheckMessage(MSG_KEY, tagOrder),
            "63: " + getCheckMessage(MSG_KEY, tagOrder),
            "73: " + getCheckMessage(MSG_KEY, tagOrder),
            "80: " + getCheckMessage(MSG_KEY, tagOrder),
            "97: " + getCheckMessage(MSG_KEY, tagOrder),
            "98: " + getCheckMessage(MSG_KEY, tagOrder),
            "110: " + getCheckMessage(MSG_KEY, tagOrder),
            "111: " + getCheckMessage(MSG_KEY, tagOrder),
            "112: " + getCheckMessage(MSG_KEY, tagOrder),
            "126: " + getCheckMessage(MSG_KEY, tagOrder),
            "134: " + getCheckMessage(MSG_KEY, tagOrder),
            "135: " + getCheckMessage(MSG_KEY, tagOrder),
            "145: " + getCheckMessage(MSG_KEY, tagOrder),
            "146: " + getCheckMessage(MSG_KEY, tagOrder),
            "156: " + getCheckMessage(MSG_KEY, tagOrder),
            "157: " + getCheckMessage(MSG_KEY, tagOrder),
            "164: " + getCheckMessage(MSG_KEY, tagOrder),
            "172: " + getCheckMessage(MSG_KEY, tagOrder),
            "183: " + getCheckMessage(MSG_KEY, tagOrder),
            "194: " + getCheckMessage(MSG_KEY, tagOrder),
            "196: " + getCheckMessage(MSG_KEY, tagOrder),
            "210: " + getCheckMessage(MSG_KEY, tagOrder),
            "213: " + getCheckMessage(MSG_KEY, tagOrder),
            "224: " + getCheckMessage(MSG_KEY, tagOrder),
            "234: " + getCheckMessage(MSG_KEY, tagOrder),
            "241: " + getCheckMessage(MSG_KEY, tagOrder),
            "248: " + getCheckMessage(MSG_KEY, tagOrder),
            "258: " + getCheckMessage(MSG_KEY, tagOrder),
            "259: " + getCheckMessage(MSG_KEY, tagOrder),
            "270: " + getCheckMessage(MSG_KEY, tagOrder),
            "272: " + getCheckMessage(MSG_KEY, tagOrder),
            "286: " + getCheckMessage(MSG_KEY, tagOrder),
            "288: " + getCheckMessage(MSG_KEY, tagOrder),
            "289: " + getCheckMessage(MSG_KEY, tagOrder),
            "299: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verify(checkConfig, getPath("InputAtclauseOrderIncorrect.java"), expected);
    }

    @Test
    public void testIncorrectCustom() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        checkConfig.addAttribute("target", "CLASS_DEF");
        checkConfig.addAttribute("tagOrder", "@since, @version, @param, @return, @throws, "
                + "@exception, @deprecated, @see, @serial, @serialField, @serialData,@author");

        final String tagOrder = "[@since, @version, @param, @return, @throws, @exception,"
                + " @deprecated, @see, @serial, @serialField, @serialData, @author]";
        final String[] expected = {
            "123: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verify(checkConfig, getPath("InputAtclauseOrderIncorrectCustom.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("package-info.java"), expected);
    }

    @Test
    public void testAtclauseOrderRecords() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        checkConfig.addAttribute("target", "CLASS_DEF , INTERFACE_DEF , ENUM_DEF , METHOD_DEF ,"
            + " CTOR_DEF , VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF");

        checkConfig.addAttribute("tagOrder", "@author, @version, @param, @return, @throws,"
            + " @exception, @see, @since, @serial, @serialField, @serialData, @deprecated");

        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception,"
            + " @see, @since, @serial, @serialField, @serialData, @deprecated]";

        final String[] expected = {
            "36: " + getCheckMessage(MSG_KEY, tagOrder),
            "37: " + getCheckMessage(MSG_KEY, tagOrder),
            "38: " + getCheckMessage(MSG_KEY, tagOrder),
            "48: " + getCheckMessage(MSG_KEY, tagOrder),
            "49: " + getCheckMessage(MSG_KEY, tagOrder),
            "58: " + getCheckMessage(MSG_KEY, tagOrder),
            "77: " + getCheckMessage(MSG_KEY, tagOrder),
            "92: " + getCheckMessage(MSG_KEY, tagOrder),

            };
        verify(checkConfig, getNonCompilablePath("InputAtclauseOrderRecords.java"), expected);
    }

    @Test
    public void testMethodReturningArrayType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final String[] expected = {
            "20: " + getCheckMessage(MSG_KEY, tagOrder),
            "32: " + getCheckMessage(MSG_KEY, tagOrder),
            "33: " + getCheckMessage(MSG_KEY, tagOrder),
        };
        verify(checkConfig, getPath("InputAtclauseOrderMethodReturningArrayType.java"), expected);
    }

    @Test
    public void testAtclauseOrderLotsOfRecords() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        checkConfig.addAttribute("target", "CLASS_DEF , INTERFACE_DEF , ENUM_DEF , METHOD_DEF ,"
            + " CTOR_DEF , VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF");

        checkConfig.addAttribute("tagOrder", "@author, @version, @param, @return, @throws,"
            + " @exception, @see, @since, @serial, @serialField, @serialData, @deprecated");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("InputAtclauseOrderLotsOfRecords.java"), expected);
    }

    @Test
    public void testAtclause() throws Exception {
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        final String[] expected = {
            "20: " + getCheckMessage(MSG_KEY, tagOrder),
            "21: " + getCheckMessage(MSG_KEY, tagOrder),
            "22: " + getCheckMessage(MSG_KEY, tagOrder),
            "32: " + getCheckMessage(MSG_KEY, tagOrder),
            "33: " + getCheckMessage(MSG_KEY, tagOrder),
            "34: " + getCheckMessage(MSG_KEY, tagOrder),
            "44: " + getCheckMessage(MSG_KEY, tagOrder),
            "45: " + getCheckMessage(MSG_KEY, tagOrder),
            "46: " + getCheckMessage(MSG_KEY, tagOrder),
            "59: " + getCheckMessage(MSG_KEY, tagOrder),
            "60: " + getCheckMessage(MSG_KEY, tagOrder),
            "61: " + getCheckMessage(MSG_KEY, tagOrder),
            "73: " + getCheckMessage(MSG_KEY, tagOrder),
            "74: " + getCheckMessage(MSG_KEY, tagOrder),
            "75: " + getCheckMessage(MSG_KEY, tagOrder),
            "88: " + getCheckMessage(MSG_KEY, tagOrder),
            "89: " + getCheckMessage(MSG_KEY, tagOrder),
            "90: " + getCheckMessage(MSG_KEY, tagOrder),
            "100: " + getCheckMessage(MSG_KEY, tagOrder),
            "101: " + getCheckMessage(MSG_KEY, tagOrder),
            "102: " + getCheckMessage(MSG_KEY, tagOrder),
        };

        verify(checkConfig,
                getPath("InputAtclauseOrderWithAnnotationsOutsideJavadoc.java"), expected);
    }

    @Test
    public void testNewArrayDeclaratorStructure() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AtclauseOrderCheck.class);
        final String tagOrder = "[@author, @version, @param, @return, @throws, @exception, @see,"
                + " @since, @serial, @serialField, @serialData, @deprecated]";

        final String[] expected = {
            "41: " + getCheckMessage(MSG_KEY, tagOrder),
            "58: " + getCheckMessage(MSG_KEY, tagOrder),
            "78: " + getCheckMessage(MSG_KEY, tagOrder),
            "79: " + getCheckMessage(MSG_KEY, tagOrder),
            "80: " + getCheckMessage(MSG_KEY, tagOrder),
        };

        verify(checkConfig,
                getPath("InputAtclauseOrderNewArrayDeclaratorStructure.java"), expected);
    }
}
