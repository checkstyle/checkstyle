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
import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY_EXT;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class VariableDeclarationUsageDistanceCheckTest extends
        AbstractModuleTestSupport {

    @Test
    public void dumpViolations() throws Exception {
        String[] configs = {"Default", "Final", "General", "RegExp", "Scopes"};
        String[] parts = {"", "Methods", "Blocks", "Logic", "Listeners", "Issue32A", "Issue32B", "Loops", "Nested", "TryFinally"};
        StringBuilder sb = new StringBuilder();
        for (String cfg : configs) {
            for (String p : parts) {
                String fname = "InputVariableDeclarationUsageDistance" + cfg + p + ".java";
                String fpath = getPath(fname);
                TestInputConfiguration conf = InlineConfigParser.parse(fpath);
                getStream().flush();
                getStream().reset();
                Checker checker = createChecker(conf.createConfiguration());
                checker.process(Collections.singletonList(new File(fpath)));
                checker.destroy();
                String raw = getStream().toString(StandardCharsets.UTF_8);
                for (String line : raw.split("\\R")) {
                    if (line.contains(": ") && !line.startsWith("Starting audit...") && !line.startsWith("Audit done.")) {
                        sb.append("VIOLATION:").append(fname).append(':').append(line).append('\n');
                    }
                }
            }
        }
        java.nio.file.Files.writeString(java.nio.file.Path.of("target/violations.txt"), sb.toString());
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/variabledeclarationusagedistance";
    }

    @Test
    public void testGeneralLogic() throws Exception {
        final String[] expected = {
            "40:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "48:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "54:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "67:9: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "77:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "102:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneral.java"), expected);
    }

    @Test
    public void testGeneralLogicMethods() throws Exception {
        final String[] expected = {
            "55:9: " + getCheckMessage(MSG_KEY, "m", 3, 1),
            "56:9: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "97:9: " + getCheckMessage(MSG_KEY, "result", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralMethods.java"), expected);
    }

    @Test
    public void testGeneralLogicBlocks() throws Exception {
        final String[] expected = {
            "39:9: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "42:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "43:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralBlocks.java"), expected);
    }

    @Test
    public void testGeneralLogicLogic() throws Exception {
        final String[] expected = {
            "41:9: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "42:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "67:9: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "80:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralLogic.java"), expected);
    }

    @Test
    public void testGeneralLogicListeners() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "30:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "54:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralListeners.java"), expected);
    }

    @Test
    public void testGeneralLogicIssue32A() throws Exception {
        final String[] expected = {
            "78:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "79:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralIssue32A.java"), expected);
    }

    @Test
    public void testGeneralLogicIssue32B() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "16:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "27:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "39:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "49:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "50:9: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "79:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "81:13: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralIssue32B.java"), expected);
    }

    @Test
    public void testGeneralLogicLoops() throws Exception {
        final String[] expected = {
            "64:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "74:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralLoops.java"), expected);
    }

    @Test
    public void testGeneralLogicNested() throws Exception {
        final String[] expected = {
            "57:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "69:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "90:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralNested.java"), expected);
    }

    @Test
    public void testGeneralLogicTryFinally() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "a", 3, 1),
            "51:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneralTryFinally.java"), expected);
    }

    @Test
    public void testGeneralLogic2() throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_KEY, "first", 5, 1),
            "29:9: " + getCheckMessage(MSG_KEY, "allInvariants", 2, 1),
            "58:9: " + getCheckMessage(MSG_KEY, "b", 3, 1),
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
            "77:9: " + getCheckMessage(MSG_KEY, "count", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testDistanceMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceMethods.java"), expected);
    }

    @Test
    public void testDistanceBlocks() throws Exception {
        final String[] expected = {
            "39:9: " + getCheckMessage(MSG_KEY, "t", 5, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceBlocks.java"), expected);
    }

    @Test
    public void testDistanceLogic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceLogic.java"), expected);
    }

    @Test
    public void testDistanceListeners() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceListeners.java"), expected);
    }

    @Test
    public void testDistanceIssue32A() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceIssue32A.java"), expected);
    }

    @Test
    public void testDistanceIssue32B() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 3),
            "36:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 3),
            "46:9: " + getCheckMessage(MSG_KEY, "count", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceIssue32B.java"), expected);
    }

    @Test
    public void testDistanceLoops() throws Exception {
        final String[] expected = {
            "64:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "74:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceLoops.java"), expected);
    }

    @Test
    public void testDistanceNested() throws Exception {
        final String[] expected = {
            "57:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "90:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceNested.java"), expected);
    }

    @Test
    public void testDistanceTryFinally() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceTryFinally.java"), expected);
    }

    @Test
    public void testVariableRegExp() throws Exception {
        final String[] expected = {
            "48:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "54:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "67:9: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "77:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "102:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExp.java"), expected);
    }

    @Test
    public void testVariableRegExpMethods() throws Exception {
        final String[] expected = {
            "56:9: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "97:9: " + getCheckMessage(MSG_KEY, "result", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpMethods.java"), expected);
    }

    @Test
    public void testVariableRegExpBlocks() throws Exception {
        final String[] expected = {
            "43:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpBlocks.java"), expected);
    }

    @Test
    public void testVariableRegExpLogic() throws Exception {
        final String[] expected = {
            "41:9: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "42:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "67:9: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "80:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpLogic.java"), expected);
    }

    @Test
    public void testVariableRegExpListeners() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "30:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "54:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpListeners.java"), expected);
    }

    @Test
    public void testVariableRegExpIssue32A() throws Exception {
        final String[] expected = {
            "78:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "79:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpIssue32A.java"), expected);
    }

    @Test
    public void testVariableRegExpIssue32B() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "16:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "27:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "39:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "49:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "50:9: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "79:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "81:13: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpIssue32B.java"), expected);
    }

    @Test
    public void testVariableRegExpLoops() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpLoops.java"), expected);
    }

    @Test
    public void testVariableRegExpNested() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpNested.java"), expected);
    }

    @Test
    public void testVariableRegExpTryFinally() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceRegExpTryFinally.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOption() throws Exception {
        final String[] expected = {
            "40:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "48:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "54:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "77:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "102:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopes.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesMethods.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionBlocks() throws Exception {
        final String[] expected = {
            "39:9: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "42:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "43:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesBlocks.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionLogic() throws Exception {
        final String[] expected = {
            "41:9: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "42:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "80:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesLogic.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionListeners() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "30:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "54:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesListeners.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionIssue32A() throws Exception {
        final String[] expected = {
            "78:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "79:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesIssue32A.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionIssue32B() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "16:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "27:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "39:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "49:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "50:9: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "79:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "81:13: " + getCheckMessage(MSG_KEY, "parentId", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesIssue32B.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionLoops() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "28:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "40:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "52:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "64:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "74:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "85:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesLoops.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionNested() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "a", 5, 1),
            "57:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "69:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "90:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesNested.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOptionTryFinally() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "51:9: " + getCheckMessage(MSG_KEY, "c", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceScopesTryFinally.java"), expected);
    }

    @Test
    public void testIgnoreFinalOption() throws Exception {
        final String[] expected = {
            "40:9: " + getCheckMessage(MSG_KEY_EXT, "a", 2, 1),
            "48:9: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "54:9: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "67:9: " + getCheckMessage(MSG_KEY_EXT, "count", 2, 1),
            "77:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "102:9: " + getCheckMessage(MSG_KEY_EXT, "arg", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinal.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionMethods() throws Exception {
        final String[] expected = {
            "55:9: " + getCheckMessage(MSG_KEY_EXT, "m", 3, 1),
            "56:9: " + getCheckMessage(MSG_KEY_EXT, "n", 2, 1),
            "97:9: " + getCheckMessage(MSG_KEY_EXT, "result", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalMethods.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionBlocks() throws Exception {
        final String[] expected = {
            "39:9: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 1),
            "42:9: " + getCheckMessage(MSG_KEY_EXT, "c", 3, 1),
            "43:9: " + getCheckMessage(MSG_KEY_EXT, "d2", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalBlocks.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionLogic() throws Exception {
        final String[] expected = {
            "41:9: " + getCheckMessage(MSG_KEY_EXT, "sel", 2, 1),
            "42:9: " + getCheckMessage(MSG_KEY_EXT, "model", 2, 1),
            "67:9: " + getCheckMessage(MSG_KEY_EXT, "sw", 2, 1),
            "80:9: " + getCheckMessage(MSG_KEY_EXT, "wh", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalLogic.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionListeners() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_KEY_EXT, "green", 2, 1),
            "30:9: " + getCheckMessage(MSG_KEY_EXT, "blue", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalListeners.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionIssue32A() throws Exception {
        final String[] expected = {
            "78:9: " + getCheckMessage(MSG_KEY_EXT, "aOpt", 3, 1),
            "79:9: " + getCheckMessage(MSG_KEY_EXT, "bOpt", 2, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalIssue32A.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionIssue32B() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_EXT, "l1", 3, 1),
            "16:9: " + getCheckMessage(MSG_KEY_EXT, "l2", 2, 1),
            "27:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 1),
            "39:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 1),
            "49:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "50:9: " + getCheckMessage(MSG_KEY_EXT, "files", 2, 1),
            "79:13: " + getCheckMessage(MSG_KEY_EXT, "id", 2, 1),
            "81:13: " + getCheckMessage(MSG_KEY_EXT, "parentId", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalIssue32B.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionLoops() throws Exception {
        final String[] expected = {
            "64:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "74:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalLoops.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionNested() throws Exception {
        final String[] expected = {
            "57:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "90:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalNested.java"), expected);
    }

    @Test
    public void testIgnoreFinalOptionTryFinally() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_EXT, "a", 3, 1),
            "51:9: " + getCheckMessage(MSG_KEY_EXT, "c", 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceFinalTryFinally.java"), expected);
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
            "77:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefault.java"), expected);
    }

    @Test
    public void testDefaultConfigurationMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultMethods.java"), expected);
    }

    @Test
    public void testDefaultConfigurationBlocks() throws Exception {
        final String[] expected = {
            "39:9: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultBlocks.java"), expected);
    }

    @Test
    public void testDefaultConfigurationLogic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultLogic.java"), expected);
    }

    @Test
    public void testDefaultConfigurationListeners() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultListeners.java"), expected);
    }

    @Test
    public void testDefaultConfigurationIssue32A() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultIssue32A.java"), expected);
    }

    @Test
    public void testDefaultConfigurationIssue32B() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 3),
            "36:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 3),
            "46:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 3),
            "78:13: " + getCheckMessage(MSG_KEY_EXT, "parentId", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultIssue32B.java"), expected);
    }

    @Test
    public void testDefaultConfigurationLoops() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "28:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "40:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "52:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "64:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "74:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultLoops.java"), expected);
    }

    @Test
    public void testDefaultConfigurationNested() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_EXT, "a", 5, 3),
            "57:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
            "90:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultNested.java"), expected);
    }

    @Test
    public void testDefaultConfigurationTryFinally() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
            "51:9: " + getCheckMessage(MSG_KEY_EXT, "c", 4, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceDefaultTryFinally.java"), expected);
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
            "33:17: " + getCheckMessage(MSG_KEY, "arg", 2, maxDistance),
            "50:17: " + getCheckMessage(MSG_KEY, "m", 3, maxDistance),
            "51:17: " + getCheckMessage(MSG_KEY, "n", 2, maxDistance),
            "83:17: " + getCheckMessage(MSG_KEY, "arg", 2, maxDistance),
            "100:17: " + getCheckMessage(MSG_KEY, "m", 3, maxDistance),
            "101:17: " + getCheckMessage(MSG_KEY, "n", 2, maxDistance),
        };

        final String filename = "InputVariableDeclarationUsageDistanceCheckSwitchExpressions.java";
        verifyWithInlineConfigParser(getPath(filename), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceSwitchExpressions2() throws Exception {
        final int maxDistance = 1;
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY, "i", 2, maxDistance),
        };

        final String filename = "InputVariableDeclarationUsageDistanceCheckSwitchExpressions2.java";
        verifyWithInlineConfigParser(getPath(filename), expected);
    }

    @Test
    public void testGeneralClass3() throws Exception {
        final String[] expected = {
            "46:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "92:9: " + getCheckMessage(MSG_KEY, "t", 3, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneral3.java"), expected);
    }

    @Test
    public void testGeneralClass4() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY, "z", 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceGeneral4.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceTryResources() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceTryResources.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceTryBlock() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
            "28:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
            "40:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
            "76:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
            "88:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceTryBlock.java"), expected);
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

    @Test
    public void testVariableDeclarationUsageDistanceMethodDef() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVariableDeclarationUsageDistanceMethodDef.java"), expected);
    }

    @Test
    public void testInitializationSequence1() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "40:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "49:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
            "55:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "69:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "83:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "91:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
            "97:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
        };

        verifyWithInlineConfigParser(
            getPath(
                "InputVariableDeclarationUsageDistanceInitializationSequence1.java"),
            expected);
    }

    @Test
    public void testInitializationSequence2() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "33:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
            "40:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "53:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "61:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
            "68:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "82:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "90:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
            "94:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "101:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
            "105:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
        };

        verifyWithInlineConfigParser(
            getPath(
                "InputVariableDeclarationUsageDistanceInitializationSequence2.java"),
            expected);
    }

    @Test
    public void testInitializationSequence3() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "38:9: " + getCheckMessage(MSG_KEY, "list2", 3, 1),
            "51:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "70:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
        };

        verifyWithInlineConfigParser(
            getPath(
                "InputVariableDeclarationUsageDistanceInitializationSequence3.java"),
            expected);
    }

    @Test
    public void testInitializationSequence4() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "40:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "57:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "67:9: " + getCheckMessage(MSG_KEY, "list", 2, 1),
            "74:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "93:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
        };

        verifyWithInlineConfigParser(
            getPath(
                "InputVariableDeclarationUsageDistanceInitializationSequence4.java"),
            expected);
    }

    @Test
    public void testInitializationSequence5() throws Exception {
        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "44:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
            "60:9: " + getCheckMessage(MSG_KEY, "list2", 3, 1),
            "80:9: " + getCheckMessage(MSG_KEY, "list2", 2, 1),
        };

        verifyWithInlineConfigParser(
            getPath(
                "InputVariableDeclarationUsageDistanceInitializationSequence5.java"),
            expected);
    }

    @Test
    public void testFalsePositive1() throws Exception {
        final String[] expected = {
            "33:17: " + getCheckMessage(MSG_KEY, "localMap", 5, 3),
            "35:17: " + getCheckMessage(MSG_KEY, "nl", 5, 3),
        };

        verifyWithInlineConfigParser(
            getPath(
                "InputVariableDeclarationUsageDistanceInitializationFalsePositive1.java"),
            expected);
    }

    @Test
    public void testReturn() throws Exception {
        final String[] expected = {
            "47:9: " + getCheckMessage(MSG_KEY, "function", 5, 3),
        };

        verifyWithInlineConfigParser(
            getPath(
                "InputVariableDeclarationUsageDistanceInitializationReturn.java"),
            expected);
    }

    @Test
    public void testTopLevelTypes() throws Exception {
        final String[] expected = {
            "33:9: " + getCheckMessage(MSG_KEY_EXT, "f", 4, 3),
        };

        verifyWithInlineConfigParser(
            getPath("InputVariableDeclarationUsageDistanceTopLevelTypes.java"),
            expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY_EXT, "b", 4, 3),
            "63:5: " + getCheckMessage(MSG_KEY_EXT, "ignoredValue", 4, 3),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "compact/InputVariableDeclarationUsageDistanceCompactSourceFile.java"),
            expected);
    }

    @Test
    public void testCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "b", 4, 1),
            "42:5: " + getCheckMessage(MSG_KEY, "atLimit", 3, 1),
            "54:5: " + getCheckMessage(MSG_KEY, "notIgnored", 2, 1),
            "60:5: " + getCheckMessage(MSG_KEY, "finalValue", 4, 1),
            "76:5: " + getCheckMessage(MSG_KEY, "nested", 4, 1),
        };

        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "compact/InputVariableDeclarationUsageDistanceCompactSourceFileProperties.java"),
            expected);
    }

    @Test
    public void testCompactSourceFileField() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "compact/InputVariableDeclarationUsageDistanceCompactSourceFileField.java"),
            expected);
    }

}
