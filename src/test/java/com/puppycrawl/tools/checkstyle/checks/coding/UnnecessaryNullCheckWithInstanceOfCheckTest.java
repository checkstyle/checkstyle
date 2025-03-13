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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryNullCheckWithInstanceOfCheck.MSG_UNNECESSARY_NULLCHECK;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class UnnecessaryNullCheckWithInstanceOfCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessarynullcheckwithinstanceof";
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceof() throws Exception {

        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "13:39: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "22:14: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),

        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOf.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofClass() throws Exception {

        final String[] expected = {
            "14:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "28:24: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfAnonymousClass.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofConditions() throws Exception {

        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "17:31: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "22:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "27:54: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "36:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "36:55: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "50:14: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfMultipleConditions.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofNested() throws Exception {

        final String[] expected = {
            "11:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "18:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "26:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfNestedIf.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofLambda() throws Exception {

        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "23:45: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "26:33: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfLambda.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofSwitch() throws Exception {

        final String[] expected = {
            "13:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "35:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfSwitch.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofTernary() throws Exception {

        final String[] expected = {
            "11:16: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "18:25: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "23:24: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfTernary.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofTryCatch() throws Exception {

        final String[] expected = {
            "11:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "23:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "33:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfTryCatch.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofTryVariable() throws Exception {

        final String[] expected = {
            "12:27: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "19:36: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath(
            "InputUnnecessaryNullCheckWithInstanceOfVariableAssignment.java"), expected);
    }
}
