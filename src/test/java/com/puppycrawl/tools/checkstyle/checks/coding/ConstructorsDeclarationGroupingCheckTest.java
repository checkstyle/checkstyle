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
import static com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck.MSG_ORDER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class ConstructorsDeclarationGroupingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/constructorsdeclarationgrouping";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, 20),
            "29:5: " + getCheckMessage(MSG_KEY, 20),
            "46:9: " + getCheckMessage(MSG_KEY, 40),
            "56:13: " + getCheckMessage(MSG_KEY, 50),
            "60:9: " + getCheckMessage(MSG_KEY, 40),
            "64:5: " + getCheckMessage(MSG_KEY, 20),
            "67:5: " + getCheckMessage(MSG_KEY, 20),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGrouping.java"), expected);
    }

    @Test
    public void testConstructorsDeclarationGroupingInner() throws Exception {
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_KEY, 26),
            "35:9: " + getCheckMessage(MSG_KEY, 26),
            "38:9: " + getCheckMessage(MSG_KEY, 26),
            "42:5: " + getCheckMessage(MSG_KEY, 12),

        };
        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGroupingInner.java"), expected);
    }

    @Test
    public void testConstructorsDeclarationGroupingRecords() throws Exception {

        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_KEY, 13),
            "24:9: " + getCheckMessage(MSG_KEY, 13),
            "29:9: " + getCheckMessage(MSG_KEY, 13),
            "46:9: " + getCheckMessage(MSG_KEY, 40),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputConstructorsDeclarationGroupingRecords.java"),
                expected);
    }

    @Test
    public void testConstructorsDeclarationGroupingArity() throws Exception {

        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_ORDER),
            "17:5: " + getCheckMessage(MSG_ORDER),
            "20:5: " + getCheckMessage(MSG_ORDER),
            "31:5: " + getCheckMessage(MSG_ORDER),
        };

        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGroupingArity.java"),
                expected);
    }

    @Test
    public void testConstructorsDeclarationGroupingRecordArity() throws Exception {

        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_ORDER),
            "33:9: " + getCheckMessage(MSG_ORDER),
            "37:9: " + getCheckMessage(MSG_ORDER),
            "41:9: " + getCheckMessage(MSG_ORDER),
        };

        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGroupingRecord.java"),
                expected);
    }

    @Test
    public void testConstructorsDeclarationGroupingMisc() throws Exception {

        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, 15),
            "20:5: " + getCheckMessage(MSG_ORDER),
            "30:9: " + getCheckMessage(MSG_KEY, 25),
            "35:9: " + getCheckMessage(MSG_KEY, 25),
            "35:9: " + getCheckMessage(MSG_ORDER),
        };

        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGroupingMisc.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final ConstructorsDeclarationGroupingCheck check =
                new ConstructorsDeclarationGroupingCheck();
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
