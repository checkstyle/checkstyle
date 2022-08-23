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
            "24:9: " + getCheckMessage(MSG_KEY, 15),
            "41:9: " + getCheckMessage(MSG_KEY, 35),
            "57:9: " + getCheckMessage(MSG_KEY, 50),
            "63:9: " + getCheckMessage(MSG_KEY, 50),
            "66:9: " + getCheckMessage(MSG_KEY, 50),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("InputOverloadMethodsDeclarationOrderRecords.java"),
            expected);
    }

    @Test
    public void testBackwardsCompatibilityCatchAllGroup() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, 15),
            "36:9: " + getCheckMessage(MSG_KEY, 31),
            "48:5: " + getCheckMessage(MSG_KEY, 46),
            "79:5: " + getCheckMessage(MSG_KEY, 74),
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
            "36:9: " + getCheckMessage(MSG_KEY, 31),
            "48:5: " + getCheckMessage(MSG_KEY, 46),
            "79:5: " + getCheckMessage(MSG_KEY, 74),
        };

        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder7.java"),
            expected
        );
    }

    @Test
    public void testOverloadMethodsDeclarationOrderStaticGrouped() throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, 24),
            "35:5: " + getCheckMessage(MSG_KEY, 33),
            "36:5: " + getCheckMessage(MSG_KEY, 34),
            "43:5: " + getCheckMessage(MSG_KEY, 41),
            "44:5: " + getCheckMessage(MSG_KEY, 42),
            "51:5: " + getCheckMessage(MSG_KEY, 49),
            "52:5: " + getCheckMessage(MSG_KEY, 49),
            "54:5: " + getCheckMessage(MSG_KEY, 49),
            "60:5: " + getCheckMessage(MSG_KEY, 58),
            "61:5: " + getCheckMessage(MSG_KEY, 58),
            "62:5: " + getCheckMessage(MSG_KEY, 58),
            "70:5: " + getCheckMessage(MSG_KEY, 66),
            "71:5: " + getCheckMessage(MSG_KEY, 66),
            "72:5: " + getCheckMessage(MSG_KEY, 66),
            "73:5: " + getCheckMessage(MSG_KEY, 66),
            "74:5: " + getCheckMessage(MSG_KEY, 66),
            "82:5: " + getCheckMessage(MSG_KEY, 78),
            "83:5: " + getCheckMessage(MSG_KEY, 78),
            "84:5: " + getCheckMessage(MSG_KEY, 78),
            "85:5: " + getCheckMessage(MSG_KEY, 78),
            "86:5: " + getCheckMessage(MSG_KEY, 78),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder3.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderPublicOrProtectedAndStatic() throws Exception {
        final String[] expected = {
            "27:5: " + getCheckMessage(MSG_KEY, 25),
            "28:5: " + getCheckMessage(MSG_KEY, 23),
            "30:5: " + getCheckMessage(MSG_KEY, 23),
            "31:5: " + getCheckMessage(MSG_KEY, 26),
            "29:5: " + getCheckMessage(MSG_KEY, 23),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder4.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderOrderMatters() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY, 18),
            "22:5: " + getCheckMessage(MSG_KEY, 20),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder5.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderComplexRegex() throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, 26),
            "37:5: " + getCheckMessage(MSG_KEY, 32),
            "44:5: " + getCheckMessage(MSG_KEY, 42),
            "52:5: " + getCheckMessage(MSG_KEY, 48),
            "53:5: " + getCheckMessage(MSG_KEY, 48),
            "59:5: " + getCheckMessage(MSG_KEY, 57),
            "60:5: " + getCheckMessage(MSG_KEY, 57),
            "71:5: " + getCheckMessage(MSG_KEY, 65),
            "73:5: " + getCheckMessage(MSG_KEY, 69),
            "84:5: " + getCheckMessage(MSG_KEY, 77),
            "86:5: " + getCheckMessage(MSG_KEY, 81),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder6.java"), expected);
    }

    @Test
    public void testOverloadMethodsDeclarationOrderGroupingProtectedAndPrivate() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, 12),
            "18:5: " + getCheckMessage(MSG_KEY, 14),
            "22:5: " + getCheckMessage(MSG_KEY, 12),
        };
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder8.java"), expected);

    }

    @Test
    void testOverloadMethodsDeclarationOrderOverloadDescriptorEquality() throws Exception {
        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder9.java")
        );

        verifyWithInlineConfigParser(
            getPath("InputOverloadMethodsDeclarationOrder10.java")
        );
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
