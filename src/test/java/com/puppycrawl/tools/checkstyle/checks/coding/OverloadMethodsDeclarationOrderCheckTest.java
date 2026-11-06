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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck.MSG_ORDER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OverloadMethodsDeclarationOrderCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/overloadmethodsdeclarationorder";
    }

    @Test
    public void testDefaultPart1() throws Exception {
        final String[] expected = {
            "34:5: " + getCheckMessage(MSG_KEY, 22),
            "63:9: " + getCheckMessage(MSG_KEY, 51),
            "68:9: " + getCheckMessage(MSG_KEY, 63),
            "88:5: " + getCheckMessage(MSG_KEY, 84),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder1.java"), expected);
    }

    @Test
    public void testDefaultPart2() throws Exception {
        final String[] expected = {
            "54:9: " + getCheckMessage(MSG_KEY, 42),
            "66:9: " + getCheckMessage(MSG_KEY, 59),
            "78:13: " + getCheckMessage(MSG_KEY, 71),
            "81:9: " + getCheckMessage(MSG_KEY, 66),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderRecords() throws Exception {

        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY, 16),
            "44:9: " + getCheckMessage(MSG_KEY, 37),
            "60:9: " + getCheckMessage(MSG_KEY, 53),
            "67:9: " + getCheckMessage(MSG_KEY, 60),
        };
        verifyWithInlineConfigParser(
                getPath("InputOverloadMethodsDeclarationOrderRecords.java"),
            expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderPrivateAndStaticMethods() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath(
                        "InputOverloadMethodsDeclarationOrderPrivateAndStaticMethods.java"
                ), expected);
    }

    @Test
    public void testOrderByIncreasingArity() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_ORDER),
            "25:5: " + getCheckMessage(MSG_ORDER),
            "33:9: " + getCheckMessage(MSG_ORDER),
            "36:9: " + getCheckMessage(MSG_ORDER),
            "49:9: " + getCheckMessage(MSG_ORDER),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderArity.java"), expected);
    }

    @Test
    public void testMisc() throws Exception {

        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY, 12),
            "20:5: " + getCheckMessage(MSG_ORDER),
            "34:9: " + getCheckMessage(MSG_KEY, 27),
            "34:9: " + getCheckMessage(MSG_ORDER),
            "37:9: " + getCheckMessage(MSG_ORDER),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputOverloadMethodsDeclarationOrderMisc.java"
                ), expected);
    }

    @Test
    public void testCompactSourceFileOverloads() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, 9),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "InputOverloadMethodsDeclarationOrderCompactSourceFile.java"),
            expected);
    }

    @Test
    public void testCompactSourceFileValid() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "InputOverloadMethodsDeclarationOrderCompactSourceFileValid.java"),
            expected);
    }

    @Test
    public void testCompactSourceFileFirstMethodOverload() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY, 9),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "InputOverloadMethodsDeclarationOrderCompactSourceFileFirstMethodOverload.java"),
            expected);
    }

    @Test
    public void testModifierGroups() throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, 15),
            "43:9: " + getCheckMessage(MSG_KEY, 33),
            "71:9: " + getCheckMessage(MSG_KEY, 63),
            "85:9: " + getCheckMessage(MSG_KEY, 78),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderModifierGroups.java"), expected);
    }

    @Test
    public void testModifierGroupsOverlap() throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, 21),
            "32:5: " + getCheckMessage(MSG_KEY, 24),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderModifierGroupsOverlap.java"), expected);
    }

    @Test
    public void testModifierGroupsMultiple() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, 12),
            "32:5: " + getCheckMessage(MSG_KEY, 25),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderModifierGroupsMultiple.java"), expected);
    }

    @Test
    public void testModifierGroupsRecord() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, 12),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderModifierGroupsRecord.java"), expected);
    }

    @Test
    public void testModifierGroupsSpaces() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, 12),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderModifierGroupsSpaces.java"), expected);
    }

    @Test
    public void testModifierGroupsDefault() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, 12),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderModifierGroupsDefault.java"), expected);
    }

    @Test
    public void testModifierGroupsNearest() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY, 12),
            "26:5: " + getCheckMessage(MSG_KEY, 19),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderModifierGroupsNearest.java"), expected);
    }

    @Test
    public void testModifierGroupsCompactSource() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, 9),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "InputOverloadMethodsDeclarationOrderModifierGroupsCompactSource.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final OverloadMethodsDeclarationOrderCheck check =
            new OverloadMethodsDeclarationOrderCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

}
