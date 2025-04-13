///
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY_EXT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class VariableDeclarationUsageDistanceCheckTest extends
        AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/variabledeclarationusagedistance";
    }

    @Test
    public void testGeneralLogic() throws Exception {
        final String[] expected = {
            "42:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "50:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "56:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "69:9: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "83:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "108:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "156:9: " + getCheckMessage(MSG_KEY, "m", 3, 1),
            "157:9: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "196:9: " + getCheckMessage(MSG_KEY, "result", 2, 1),
            "231:9: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "234:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "235:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "272:9: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "273:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "299:9: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "312:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "355:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "356:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "379:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "466:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "467:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "483:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "483:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "503:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "516:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "517:9: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "552:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "554:13: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
            "903:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "913:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "979:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "990:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "1001:9: " + getCheckMessage(MSG_KEY, "a", 3, 1),
            "1036:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "1066:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneral.java"), expected);
    }

    @Test
    public void testGeneralLogic2() throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_KEY, "first", 5, 1),
            "29:9: " + getCheckMessage(MSG_KEY, "allInvariants", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneral2.java"), expected);
    }

    @Test
    public void testIfStatements() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "28:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "32:9: " + getCheckMessage(MSG_KEY, "b", 2, 1),
            "38:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "49:9: " + getCheckMessage(MSG_KEY, "b", 2, 1),
            "50:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "51:9: " + getCheckMessage(MSG_KEY, "d", 4, 1),
            "63:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),

        };
        verifyWithInlineConfigParser(
            getPath("InputVariableDeclarationUsageDistanceIfStatements.java"), expected);
    }

    @Test
    public void testDistance() throws Exception {
        final String[] expected = {
            "83:9: " + getCheckMessage(MSG_KEY, "count", 4, 3),
            "231:9: " + getCheckMessage(MSG_KEY, "t", 5, 3),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 3),
            "503:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 3),
            "516:9: " + getCheckMessage(MSG_KEY, "count", 4, 3),
            "903:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "913:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "979:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "1066:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testVariableRegExp() throws Exception {
        final String[] expected = {
            "50:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "56:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "69:9: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "83:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "108:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "157:9: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "196:9: " + getCheckMessage(MSG_KEY, "result", 2, 1),
            "235:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "272:9: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "273:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "299:9: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "312:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "355:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "356:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "379:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "466:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "467:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "483:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "483:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "503:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "516:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "517:9: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "552:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "554:13: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExp.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOption() throws Exception {
        final String[] expected = {
            "42:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "50:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "56:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "83:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "108:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "231:9: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "234:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "235:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "272:9: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "273:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "312:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "355:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "356:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "379:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "466:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "467:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "483:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "483:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "503:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "516:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "517:9: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "552:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "554:13: " + getCheckMessage(MSG_KEY, "parentId", 4, 1),
            "855:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "867:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "879:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "891:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "903:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "913:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "924:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "945:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "990:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "1001:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "1036:9: " + getCheckMessage(MSG_KEY, "c", 4, 1),
            "1066:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopes.java"), expected);
    }

    @Test
    public void testIgnoreFinalOption() throws Exception {
        final String[] expected = {
            "42:9: " + getCheckMessage(MSG_KEY_EXT, "a", 2, 1),
            "50:9: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "56:9: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "69:9: " + getCheckMessage(MSG_KEY_EXT, "count", 2, 1),
            "83:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "108:9: " + getCheckMessage(MSG_KEY_EXT, "arg", 2, 1),
            "156:9: " + getCheckMessage(MSG_KEY_EXT, "m", 3, 1),
            "157:9: " + getCheckMessage(MSG_KEY_EXT, "n", 2, 1),
            "196:9: " + getCheckMessage(MSG_KEY_EXT, "result", 2, 1),
            "231:9: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 1),
            "234:9: " + getCheckMessage(MSG_KEY_EXT, "c", 3, 1),
            "235:9: " + getCheckMessage(MSG_KEY_EXT, "d2", 3, 1),
            "272:9: " + getCheckMessage(MSG_KEY_EXT, "sel", 2, 1),
            "273:9: " + getCheckMessage(MSG_KEY_EXT, "model", 2, 1),
            "299:9: " + getCheckMessage(MSG_KEY_EXT, "sw", 2, 1),
            "312:9: " + getCheckMessage(MSG_KEY_EXT, "wh", 2, 1),
            "355:9: " + getCheckMessage(MSG_KEY_EXT, "green", 2, 1),
            "356:9: " + getCheckMessage(MSG_KEY_EXT, "blue", 3, 1),
            "466:9: " + getCheckMessage(MSG_KEY_EXT, "aOpt", 3, 1),
            "467:9: " + getCheckMessage(MSG_KEY_EXT, "bOpt", 2, 1),
            "483:9: " + getCheckMessage(MSG_KEY_EXT, "l1", 3, 1),
            "483:9: " + getCheckMessage(MSG_KEY_EXT, "l2", 2, 1),
            "491:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 1),
            "503:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 1),
            "516:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "517:9: " + getCheckMessage(MSG_KEY_EXT, "files", 2, 1),
            "552:13: " + getCheckMessage(MSG_KEY_EXT, "id", 2, 1),
            "554:13: " + getCheckMessage(MSG_KEY_EXT, "parentId", 3, 1),
            "903:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "913:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "979:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "1001:9: " + getCheckMessage(MSG_KEY_EXT, "a", 3, 1),
            "1036:9: " + getCheckMessage(MSG_KEY_EXT, "c", 3, 1),
            "1066:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinal.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final VariableDeclarationUsageDistanceCheck check =
            new VariableDeclarationUsageDistanceCheck();
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
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = {
            "83:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 3),
            "231:9: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 3),
            "491:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 3),
            "503:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 3),
            "516:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 3),
            "554:13: " + getCheckMessage(MSG_KEY_EXT, "parentId", 4, 3),
            "855:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "867:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "879:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "891:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "903:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "913:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "945:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "1036:9: " + getCheckMessage(MSG_KEY_EXT, "c", 4, 3),
            "1001:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
            "1066:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefault.java"), expected);
    }

    @Test
    public void testDefaultConfiguration2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputVariableDeclarationUsageDistanceDefault2.java"), expected);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_KEY_EXT, "prefs", 4, 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceAnonymous.java"),
                expected);
    }

    @Test
    public void testLabels() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY_EXT, "eol", 5, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceLabels.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceSwitchExpressions() throws Exception {

        final int maxDistance = 1;
        final String[] expected = {
            "35:17: " + getCheckMessage(MSG_KEY, "arg", 2, maxDistance),
            "80:17: " + getCheckMessage(MSG_KEY, "m", 3, maxDistance),
            "81:17: " + getCheckMessage(MSG_KEY, "n", 2, maxDistance),
            "113:17: " + getCheckMessage(MSG_KEY, "arg", 2, maxDistance),
            "155:17: " + getCheckMessage(MSG_KEY, "m", 3, maxDistance),
            "156:17: " + getCheckMessage(MSG_KEY, "n", 2, maxDistance),
            "177:17: " + getCheckMessage(MSG_KEY, "count", 3, maxDistance),
            "197:17: " + getCheckMessage(MSG_KEY, "count", 3, maxDistance),
        };

        final String filename = "InputVariableDeclarationUsageDistanceCheckSwitchExpressions.java";
        verifyWithInlineConfigParser(getNonCompilablePath(filename), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceSwitchExpressions2() throws Exception {
        final int maxDistance = 1;
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "i", 2, maxDistance),
        };

        final String filename = "InputVariableDeclarationUsageDistanceCheckSwitchExpressions2.java";
        verifyWithInlineConfigParser(getNonCompilablePath(filename), expected);
    }

    @Test
    public void testGeneralClass3() throws Exception {
        final String[] expected = {
            "46:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneral3.java"), expected);
    }

    @Test
    public void testGeneralClass4() throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_KEY, "z", 3, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneral4.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceTryResources() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "20:9: " + getCheckMessage(MSG_KEY, "b", 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceTryResources.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistance4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistance3.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceScope2() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "i", 5, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScope2.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistance1() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY, "i", 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistance1.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceCloseToBlock() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_KEY, "a", 13, 1),
            "16:9: " + getCheckMessage(MSG_KEY, "b", 13, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceCloseToBlock.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistancePatternVariables() throws Exception {
        final String[] expected = {
            "35:9: " + getCheckMessage(MSG_KEY, "b", 5, 1),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputVariableDeclarationUsageDistancePatternVariables.java"),
                expected);
    }
}
