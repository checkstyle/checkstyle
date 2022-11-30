///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class OverloadMethodsDeclarationOrderCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/overloadmethodsdeclarationorder";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "32:5: " + getCheckMessage(MSG_KEY, 21),
            "60:9: " + getCheckMessage(MSG_KEY, 49),
            "72:5: " + getCheckMessage(MSG_KEY, 70),
            "115:5: " + getCheckMessage(MSG_KEY, 104),
        };
        verifyWithInlineConfigParser(
                getPath("InputOverloadMethodsDeclarationOrder.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderRecords() throws Exception {

        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_KEY, 15),
            "41:9: " + getCheckMessage(MSG_KEY, 35),
            "57:9: " + getCheckMessage(MSG_KEY, 50),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOverloadMethodsDeclarationOrderRecords.java"),
            expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderRecords2() throws Exception {

        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_KEY, 16),
            "30:9: " + getCheckMessage(MSG_KEY, 25),
            "33:9: " + getCheckMessage(MSG_KEY, 27),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputOverloadMethodsDeclarationOrderRecords2.java"),
            expected);
    }

    @Test
    public void testBackwardsCompatibilityCatchAllGroup() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, 15),
            "35:9: " + getCheckMessage(MSG_KEY, 30),
            "48:5: " + getCheckMessage(MSG_KEY, 45),
            "80:5: " + getCheckMessage(MSG_KEY, 75),
        };

        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder2.java"),
            expected
        );
    }

    @Test
    public void testBackwardsCompatibilityEmptyModifiers() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, 15),
            "35:9: " + getCheckMessage(MSG_KEY, 30),
            "48:5: " + getCheckMessage(MSG_KEY, 45),
            "80:5: " + getCheckMessage(MSG_KEY, 75),
        };

        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder2.java"),
            expected
        );
    }

    @Test
    public void testOverloadMethodsDeclarationOrderStaticGrouped() throws Exception {
        final String[] expected = {
            "29:5: " + getCheckMessage(MSG_KEY, 24),
            "37:5: " + getCheckMessage(MSG_KEY, 34),
            "39:5: " + getCheckMessage(MSG_KEY, 35),
            "47:5: " + getCheckMessage(MSG_KEY, 44),
            "49:5: " + getCheckMessage(MSG_KEY, 45),
            "57:5: " + getCheckMessage(MSG_KEY, 54),
            "61:5: " + getCheckMessage(MSG_KEY, 58),
            "68:5: " + getCheckMessage(MSG_KEY, 65),
            "79:5: " + getCheckMessage(MSG_KEY, 74),
            "92:5: " + getCheckMessage(MSG_KEY, 87),
            "105:5: " + getCheckMessage(MSG_KEY, 102),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder3.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderAbstractAndPublicOrProtectedAndStatic()
            throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, 25),
            "30:5: " + getCheckMessage(MSG_KEY, 23),
            "34:5: " + getCheckMessage(MSG_KEY, 26),
            "44:5: " + getCheckMessage(MSG_KEY, 40),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder4.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderOrderMatters() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, 11),
            "19:5: " + getCheckMessage(MSG_KEY, 16),
            "22:5: " + getCheckMessage(MSG_KEY, 19),
            "30:5: " + getCheckMessage(MSG_KEY, 27),
            "34:5: " + getCheckMessage(MSG_KEY, 30),
            "36:5: " + getCheckMessage(MSG_KEY, 32),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder5.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderComplexRegex() throws Exception {
        final String[] expected = {
            "29:5: " + getCheckMessage(MSG_KEY, 26),
            "39:5: " + getCheckMessage(MSG_KEY, 33),
            "47:5: " + getCheckMessage(MSG_KEY, 44),
            "56:5: " + getCheckMessage(MSG_KEY, 51),
            "64:5: " + getCheckMessage(MSG_KEY, 61),
            "77:5: " + getCheckMessage(MSG_KEY, 70),
            "80:5: " + getCheckMessage(MSG_KEY, 74),
            "92:5: " + getCheckMessage(MSG_KEY, 84),
            "95:5: " + getCheckMessage(MSG_KEY, 88),
            "103:5: " + getCheckMessage(MSG_KEY, 100),
            "106:5: " + getCheckMessage(MSG_KEY, 103),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder6.java"), expected);
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
