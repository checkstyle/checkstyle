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

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.jupiter.api.Test;

public class UnnecessaryNullCheckWithInstanceOfCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessarynullcheckwithinstanceof";
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceof() throws Exception {

        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "16:38: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "28:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),

        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOf.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofClass() throws Exception {

        final String[] expected = {
            "14:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "29:24: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfAnonymousClass.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofConditions() throws Exception {

        final String[] expected = {
            "11:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "15:31: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "19:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "28:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "28:55: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfMultipleConditions.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofNested() throws Exception {

        final String[] expected = {
            "12:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "19:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "28:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfNestedIf.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofLambda() throws Exception {

        final String[] expected = {
            "19:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "24:45: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "26:33: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfLambda.java"), expected);
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceofSwitch() throws Exception {

        final String[] expected = {
            "13:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "35:21: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfSwitch.java"), expected);
    }

     @Test
    public void testUnnecessaryNullCheckWithInstanceofTernary() throws Exception {

        final String[] expected = {
            "11:16: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "20:25: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "25:24: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfTernary.java"), expected);
    }

      @Test
    public void testUnnecessaryNullCheckWithInstanceofTryCatch() throws Exception {

        final String[] expected = {
            "12:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "24:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "34:17: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfTryCatch.java"), expected);
    }

      @Test
    public void testUnnecessaryNullCheckWithInstanceofTryVariable() throws Exception {

        final String[] expected = {
            "11:27: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "18:36: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };
        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOfVariableAssignment.java"), expected);
    }
}
