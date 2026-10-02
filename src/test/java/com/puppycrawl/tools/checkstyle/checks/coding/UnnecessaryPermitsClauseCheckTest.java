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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryPermitsClauseCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnnecessaryPermitsClauseCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessarypermitsclause";
    }

    @Test
    public void testGetAcceptableTokens() {
        final UnnecessaryPermitsClauseCheck check =
                new UnnecessaryPermitsClauseCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.PERMITS_CLAUSE,
        };

        assertWithMessage("Acceptable tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testSealedClasses() throws Exception {
        final String[] expected = {
            "12:22: " + getCheckMessage(MSG_KEY),
            "22:26: " + getCheckMessage(MSG_KEY),
            "32:34: " + getCheckMessage(MSG_KEY),
            "39:27: " + getCheckMessage(MSG_KEY),
            "45:35: " + getCheckMessage(MSG_KEY),
            "49:33: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnnecessaryPermitsClauseSealedClasses.java"),
                expected);
    }

    @Test
    public void testSubClassesInAnotherFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryPermitsClauseSubClassesInAnotherFile.java"),
            expected);
    }

    @Test
    public void testClearStateAfterFile() throws Exception {
        final String[] expected = {
            "21:35: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getPath("InputUnnecessaryPermitsClauseSubClassesInAnotherFile2.java"),
            getPath("InputUnnecessaryPermitsClauseSubClassesInAnotherFile.java"),
            expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "8:33: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath("compact/InputUnnecessaryPermitsClauseCompactSourceFile.java"),
            expected);
    }

}
