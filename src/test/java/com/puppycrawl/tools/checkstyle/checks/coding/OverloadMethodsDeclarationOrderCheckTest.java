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
            "33:5: " + getCheckMessage(MSG_KEY, 21),
            "62:9: " + getCheckMessage(MSG_KEY, 50),
            "67:9: " + getCheckMessage(MSG_KEY, 62),
            "87:5: " + getCheckMessage(MSG_KEY, 83),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder1.java"), expected);
    }

    @Test
    public void testDefaultPart2() throws Exception {
        final String[] expected = {
            "53:9: " + getCheckMessage(MSG_KEY, 41),
            "65:9: " + getCheckMessage(MSG_KEY, 58),
            "77:13: " + getCheckMessage(MSG_KEY, 70),
            "80:9: " + getCheckMessage(MSG_KEY, 65),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder2.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderRecords() throws Exception {

        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_KEY, 15),
            "43:9: " + getCheckMessage(MSG_KEY, 36),
            "59:9: " + getCheckMessage(MSG_KEY, 52),
            "66:9: " + getCheckMessage(MSG_KEY, 59),
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
            "21:5: " + getCheckMessage(MSG_ORDER),
            "24:5: " + getCheckMessage(MSG_ORDER),
            "32:9: " + getCheckMessage(MSG_ORDER),
            "35:9: " + getCheckMessage(MSG_ORDER),
            "48:9: " + getCheckMessage(MSG_ORDER),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrderArity.java"), expected);
    }

    @Test
    public void testMisc() throws Exception {

        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY, 11),
            "19:5: " + getCheckMessage(MSG_ORDER),
            "33:9: " + getCheckMessage(MSG_KEY, 26),
            "33:9: " + getCheckMessage(MSG_ORDER),
            "36:9: " + getCheckMessage(MSG_ORDER),
        };

        verifyWithInlineConfigParser(
                getPath(
                        "InputOverloadMethodsDeclarationOrderMisc.java"
                ), expected);
    }

    @Test
    public void testCompactSourceFileOverloads() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY, 8),
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
            "11:1: " + getCheckMessage(MSG_KEY, 8),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "InputOverloadMethodsDeclarationOrderCompactSourceFileFirstMethodOverload.java"),
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
