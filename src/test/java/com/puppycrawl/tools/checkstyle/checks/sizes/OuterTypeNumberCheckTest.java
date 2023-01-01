///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.OuterTypeNumberCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OuterTypeNumberCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/outertypenumber";
    }

    @Test
    public void testGetRequiredTokens() {
        final OuterTypeNumberCheck checkObj = new OuterTypeNumberCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final OuterTypeNumberCheck outerTypeNumberObj =
            new OuterTypeNumberCheck();
        final int[] actual = outerTypeNumberObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeNumberSimple.java"), expected);
    }

    @Test
    public void testMax30() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeNumberSimple1.java"), expected);
    }

    @Test
    public void testWithInnerClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeNumberEmptyInner.java"), expected);
    }

    @Test
    public void testWithRecords() throws Exception {

        final int max = 1;

        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOuterTypeNumberRecords.java"), expected);
    }

}
