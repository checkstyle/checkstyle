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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.InnerTypeLastCheck.MSG_KEY;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InnerTypeLastCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
            "51:9: " + getCheckMessage(MSG_KEY),
            "73:9: " + getCheckMessage(MSG_KEY),
            "78:9: " + getCheckMessage(MSG_KEY),
            "88:9: " + getCheckMessage(MSG_KEY),
            "106:17: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClass.java"), expected);
    }

    @Test
    public void testIfRootClassChecked() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY),
            "23:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClassRootClass.java"), expected);
    }

    @Test
    public void testIfRootClassChecked2() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClassRootClass2.java"), expected);
    }

    @Test
    public void testIfRootClassChecked3() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(InnerTypeLastCheck.class);
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY),
            "23:5: " + getCheckMessage(MSG_KEY),
            "20:5: " + getCheckMessage(MSG_KEY),
            "23:5: " + getCheckMessage(MSG_KEY),
        };
        verify(createChecker(checkConfig), new File[] {
            new File(getPath("InputInnerTypeLastClassRootClass.java")),
            new File(getPath("InputInnerTypeLastClassRootClass.java")),
        }, getPath("InputInnerTypeLastClassRootClass.java"), expected);
    }

    @Test
    public void testInnerTypeBeforeCtor() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY),
            "24:5: " + getCheckMessage(MSG_KEY),
            "34:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastClassCtorsInitBlocks.java"), expected);
    }

    @Test
    public void testInnerTypeLastRecords() throws Exception {

        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY),
            "23:5: " + getCheckMessage(MSG_KEY),
            "33:9: " + getCheckMessage(MSG_KEY),
            "44:13: " + getCheckMessage(MSG_KEY),
            "51:13: " + getCheckMessage(MSG_KEY),
            "56:9: " + getCheckMessage(MSG_KEY),
            "59:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInnerTypeLastRecords.java"), expected);
    }

    @Test
    public void testInnerTypeLastCstyleArray() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY),
            "14:5: " + getCheckMessage(MSG_KEY),
            "16:5: " + getCheckMessage(MSG_KEY),
            "18:5: " + getCheckMessage(MSG_KEY),
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

    @Test
    public void testInnerTypeLastCompactSourceFile() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputInnerTypeLastCompactSourceFile.java"), expected);
    }

    @Test
    public void testInnerTypeLastCompactSourceFileNested() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputInnerTypeLastCompactSourceFileNested.java"),
                expected);
    }

}
