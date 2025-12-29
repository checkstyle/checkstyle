///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryNullCheckWithInstanceOfCheck.MSG_UNNECESSARY_NULLCHECK;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class UnnecessaryNullCheckWithInstanceOfCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessarynullcheckwithinstanceof";
    }

    @Test
    public void unnecessaryNullCheckWithInstanceof() throws Exception {

        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "15:39: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "24:14: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "54:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "57:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "60:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "69:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "74:26: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "85:19: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfOne.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofClass() throws Exception {

        final String[] expected = {
            "14:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "28:24: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfAnonymousClass.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofConditions() throws Exception {

        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "17:31: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "22:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "27:54: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "36:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "36:55: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "50:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfMultipleConditions.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofNested() throws Exception {

        final String[] expected = {
            "11:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "18:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "26:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfNestedIf.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofLambda() throws Exception {

        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "23:45: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "26:33: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfLambda.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofSwitch() throws Exception {

        final String[] expected = {
            "13:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "35:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfSwitch.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofTernary() throws Exception {

        final String[] expected = {
            "11:16: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "18:25: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "23:24: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "26:16: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "27:20: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "33:16: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "34:19: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "43:24: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "43:61: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "49:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfTernary.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofTryCatch() throws Exception {

        final String[] expected = {
            "11:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "23:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "33:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfTryCatch.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofTryVariable() throws Exception {

        final String[] expected = {
            "12:27: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "19:36: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfVariableAssignment.java"), expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofPattern() throws Exception {

        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(
                getPath(
                "InputUnnecessaryNullCheckWithInstanceOfTwo.java"),
            expected);
    }

    @Test
    public void unnecessaryNullCheckWithInstanceofPair() throws Exception {

        final String[] expected = {
            "15:56: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(
                getPath(
                "InputUnnecessaryNullCheckWithInstanceOfPair.java"),
            expected);
    }

    @Test
    public void tokensNotNull() {
        final UnnecessaryNullCheckWithInstanceOfCheck check =
            new UnnecessaryNullCheckWithInstanceOfCheck();
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
