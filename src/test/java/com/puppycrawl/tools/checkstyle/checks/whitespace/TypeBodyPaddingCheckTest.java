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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_END_REQUIRED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_START_REQUIRED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TypeBodyPaddingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/typebodypadding";
    }

    @Test
    public void testEmptyNotAllowed1() throws Exception {
        final String[] expected = {
            "15:44: " + getCheckMessage(MSG_START_REQUIRED),
            "16:1: " + getCheckMessage(MSG_END_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEmptyNotAllowed1.java"),
                expected);
    }

    @Test
    public void testEmptyNotAllowed2() throws Exception {
        final String[] expected = {
            "15:44: " + getCheckMessage(MSG_START_REQUIRED),
            "15:45: " + getCheckMessage(MSG_END_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEmptyNotAllowed2.java"),
                expected);
    }

    @Test
    public void testEmptyNotAllowed3() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEmptyNotAllowed3.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEmptyAllowed1() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEmptyAllowed1.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEmptyAllowed2() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEmptyAllowed2.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEmptyAllowed3() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEmptyAllowed3.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEmptyAllowed4() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEmptyAllowed4.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testStarting() throws Exception {
        final String[] expected = {
            "15:33: " + getCheckMessage(MSG_START_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingStart.java"),
                expected);
    }

    @Test
    public void testEnding1() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_END_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEnd1.java"),
                expected);
    }

    @Test
    public void testEnding2() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingEnd2.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testBoth() throws Exception {
        final String[] expected = {
            "15:39: " + getCheckMessage(MSG_START_REQUIRED),
            "17:1: " + getCheckMessage(MSG_END_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingBoth.java"),
                expected);
    }

    @Test
    public void testCommentStart() throws Exception {
        final String[] expected = {
            "14:47: " + getCheckMessage(MSG_START_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingCommentStart.java"),
                expected);
    }

    @Test
    public void testCommentEnd() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_END_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingCommentEnd.java"),
                expected);
    }

    @Test
    public void testInner() throws Exception {
        final String[] expected = {
            "19:17: " + getCheckMessage(MSG_START_REQUIRED),
        };

        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingInner.java"),
                expected);
    }

    @Test
    public void testInnerSkip() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingInnerSkip.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testInnerInterface() throws Exception {
        final String[] expected = {
            "16:46: " + getCheckMessage(MSG_START_REQUIRED),
            "17:18: " + getCheckMessage(MSG_START_REQUIRED),
            "19:5: " + getCheckMessage(MSG_END_REQUIRED),
            "20:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingInnerInterface.java"), expected);
    }

    @Test
    public void testInnerEnum() throws Exception {
        final String[] expected = {
            "15:36: " + getCheckMessage(MSG_START_REQUIRED),
            "17:18: " + getCheckMessage(MSG_START_REQUIRED),
            "19:5: " + getCheckMessage(MSG_END_REQUIRED),
            "20:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingInnerEnum.java"), expected);
    }

    @Test
    public void testInnerRecord() throws Exception {
        final String[] expected = {
            "15:42: " + getCheckMessage(MSG_START_REQUIRED),
            "16:18: " + getCheckMessage(MSG_START_REQUIRED),
            "18:5: " + getCheckMessage(MSG_END_REQUIRED),
            "19:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingInnerRecord.java"), expected);
    }

    @Test
    public void testInnerAnnotation() throws Exception {
        final String[] expected = {
            "16:48: " + getCheckMessage(MSG_START_REQUIRED),
            "17:18: " + getCheckMessage(MSG_START_REQUIRED),
            "19:5: " + getCheckMessage(MSG_END_REQUIRED),
            "20:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingInnerAnnotation.java"), expected);
    }

    @Test
    public void testLocalMethod() throws Exception {
        final String[] expected = {
            "15:39: " + getCheckMessage(MSG_START_REQUIRED),
            "17:21: " + getCheckMessage(MSG_START_REQUIRED),
            "19:9: " + getCheckMessage(MSG_END_REQUIRED),
            "21:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingLocalMethod.java"), expected);
    }

    @Test
    public void testLocalConstructor() throws Exception {
        final String[] expected = {
            "16:37: " + getCheckMessage(MSG_START_REQUIRED),
            "18:21: " + getCheckMessage(MSG_START_REQUIRED),
            "20:9: " + getCheckMessage(MSG_END_REQUIRED),
            "22:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingLocalCtor.java"), expected);
    }

    @Test
    public void testLocalInitBlocks() throws Exception {
        final String[] expected = {
            "16:43: " + getCheckMessage(MSG_START_REQUIRED),
            "18:27: " + getCheckMessage(MSG_START_REQUIRED),
            "20:9: " + getCheckMessage(MSG_END_REQUIRED),
            "23:25: " + getCheckMessage(MSG_START_REQUIRED),
            "25:9: " + getCheckMessage(MSG_END_REQUIRED),
            "27:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingLocalInitBlocks.java"), expected);
    }

    @Test
    public void testLocalLambda() throws Exception {
        final String[] expected = {
            "15:39: " + getCheckMessage(MSG_START_REQUIRED),
            "17:21: " + getCheckMessage(MSG_START_REQUIRED),
            "19:9: " + getCheckMessage(MSG_END_REQUIRED),
            "21:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingLocalLambda.java"), expected);
    }

    @Test
    public void testCoverage() throws Exception {
        final String[] expected = {
            "16:36: " + getCheckMessage(MSG_START_REQUIRED),
            "17:17: " + getCheckMessage(MSG_START_REQUIRED),
            "19:5: " + getCheckMessage(MSG_END_REQUIRED),
            "21:30: " + getCheckMessage(MSG_START_REQUIRED),
            "21:31: " + getCheckMessage(MSG_END_REQUIRED),
            "23:20: " + getCheckMessage(MSG_START_REQUIRED),
            "23:21: " + getCheckMessage(MSG_END_REQUIRED),
            "26:21: " + getCheckMessage(MSG_START_REQUIRED),
            "28:9: " + getCheckMessage(MSG_END_REQUIRED),
            "30:1: " + getCheckMessage(MSG_END_REQUIRED),
        };
        verifyWithInlineConfigParser(getPath("InputTypeBodyPaddingCoverage.java"), expected);
    }

}
