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
import static com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EmptyStatementCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/emptystatement";
    }

    @Test
    public void testEmptyStatements()
            throws Exception {
        final String[] expected = {
            "17:4: " + getCheckMessage(MSG_KEY),
            "18:4: " + getCheckMessage(MSG_KEY),
            "22:7: " + getCheckMessage(MSG_KEY),
            "25:4: " + getCheckMessage(MSG_KEY),
            "29:7: " + getCheckMessage(MSG_KEY),
            "34:19: " + getCheckMessage(MSG_KEY),
            "38:10: " + getCheckMessage(MSG_KEY),
            "41:16: " + getCheckMessage(MSG_KEY),
            "45:10: " + getCheckMessage(MSG_KEY),
            "55:10: " + getCheckMessage(MSG_KEY),
            "61:13: " + getCheckMessage(MSG_KEY),
            "63:13: " + getCheckMessage(MSG_KEY),
            "66:19: " + getCheckMessage(MSG_KEY),
            "70:10: " + getCheckMessage(MSG_KEY),
            "73:9: " + getCheckMessage(MSG_KEY),
            "78:10: " + getCheckMessage(MSG_KEY),
            "84:10: " + getCheckMessage(MSG_KEY),
            "88:10: " + getCheckMessage(MSG_KEY),
            "92:10: " + getCheckMessage(MSG_KEY),
            "96:22: " + getCheckMessage(MSG_KEY),
            "97:13: " + getCheckMessage(MSG_KEY),
            "98:6: " + getCheckMessage(MSG_KEY),
            "99:27: " + getCheckMessage(MSG_KEY),
            "100:19: " + getCheckMessage(MSG_KEY),
            "101:34: " + getCheckMessage(MSG_KEY),
            "105:29: " + getCheckMessage(MSG_KEY),
            "107:38: " + getCheckMessage(MSG_KEY),
            "110:52: " + getCheckMessage(MSG_KEY),
            "114:39: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputEmptyStatement.java"), expected);
    }

    @Test
    public void testAllowEmptyLoopBody() throws Exception {
        final String[] expected = {
            "15:11: " + getCheckMessage(MSG_KEY),
            "17:15: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputEmptyStatementAllowEmptyLoopBody.java"), expected);
    }

    @Test
    public void testTopLevelSemicolons() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_KEY),
            "14:2: " + getCheckMessage(MSG_KEY),
            "15:1: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputEmptyStatementTopLevel.java"), expected);
    }

    @Test
    public void testEnumSemicolons() throws Exception {
        final String[] expected = {
            "15:6: " + getCheckMessage(MSG_KEY),
            "20:10: " + getCheckMessage(MSG_KEY),
            "30:10: " + getCheckMessage(MSG_KEY),
            "35:9: " + getCheckMessage(MSG_KEY),
            "40:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputEmptyStatementEnum.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final EmptyStatementCheck check = new EmptyStatementCheck();
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

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY),
            "13:5: " + getCheckMessage(MSG_KEY),
            "16:18: " + getCheckMessage(MSG_KEY),
            "18:14: " + getCheckMessage(MSG_KEY),
            "22:9: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputEmptyStatementCompactSourceFile.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileAllowEmptyLoopBody() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputEmptyStatementCompactSourceFileAllowEmptyLoopBody.java"),
                expected);
    }

}
