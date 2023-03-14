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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.InnerTypeLastCheck.MSG_KEY;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class InnerTypeLastCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/innertypelast";
    }

    @Test
    public void testGetRequiredTokens() {
        final InnerTypeLastCheck checkObj = new InnerTypeLastCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testMembersBeforeInner() throws Exception {
        final String[] expected = {
            "50:9: " + getCheckMessage(MSG_KEY),
            "71:9: " + getCheckMessage(MSG_KEY),
            "75:9: " + getCheckMessage(MSG_KEY),
            "84:5: " + getCheckMessage(MSG_KEY),
            "101:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClass.java"), expected);
    }

    @Test
    public void testIfRootClassChecked() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClassRootClass.java"), expected);
    }

    @Test
    public void testIfRootClassChecked2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClassRootClass2.java"), expected);
    }

    @Test
    public void testIfRootClassChecked3() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InnerTypeLastCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(createChecker(checkConfig), new File[] {
            new File(getPath("InputInnerTypeLastClassRootClass.java")),
            new File(getPath("InputInnerTypeLastClassRootClass.java")),
        }, "InputInnerTypeLastClassRootClass.java", expected);
    }

    @Test
    public void testInnerTypeBeforeCtor() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY),
            "22:5: " + getCheckMessage(MSG_KEY),
            "31:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClassCtorsInitBlocks.java"), expected);
    }

    @Test
    public void testInnerTypeLastRecords() throws Exception {

        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_KEY),
            "21:5: " + getCheckMessage(MSG_KEY),
            "30:9: " + getCheckMessage(MSG_KEY),
            "40:13: " + getCheckMessage(MSG_KEY),
            "46:13: " + getCheckMessage(MSG_KEY),
            "50:9: " + getCheckMessage(MSG_KEY),
            "52:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputInnerTypeLastRecords.java"), expected);
    }

    @Test
    public void testInnerTypeLastCstyleArray() throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_KEY),
            "12:5: " + getCheckMessage(MSG_KEY),
            "13:5: " + getCheckMessage(MSG_KEY),
            "14:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastArray.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final InnerTypeLastCheck obj = new InnerTypeLastCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(obj.getAcceptableTokens())
            .isEqualTo(expected);
    }

}
