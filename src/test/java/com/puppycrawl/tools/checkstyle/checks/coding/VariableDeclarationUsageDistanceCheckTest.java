////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY_EXT;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class VariableDeclarationUsageDistanceCheckTest extends
        AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/variabledeclarationusagedistance";
    }

    @Test
    public void testGeneralLogic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "38:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "44:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "57:9: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "71:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "96:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "144:9: " + getCheckMessage(MSG_KEY, "m", 3, 1),
            "145:9: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "184:9: " + getCheckMessage(MSG_KEY, "result", 2, 1),
            "219:9: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "222:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "223:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "260:12: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "261:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "287:9: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "300:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "343:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "344:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "367:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "454:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "455:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "471:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "471:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "479:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "504:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "505:15: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "540:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "542:13: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
            "891:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "901:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "967:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
            "978:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "989:9: " + getCheckMessage(MSG_KEY, "a", 3, 1),
            "1024:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "1054:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
        };
        verify(checkConfig, getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testDistance() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "3");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "71:9: " + getCheckMessage(MSG_KEY, "count", 4, 3),
            "219:9: " + getCheckMessage(MSG_KEY, "t", 5, 3),
            "479:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 3),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 3),
            "504:9: " + getCheckMessage(MSG_KEY, "count", 4, 3),
            "891:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "901:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "967:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
            "1054:9: " + getCheckMessage(MSG_KEY, "a", 4, 3),
        };
        verify(checkConfig, getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testVariableRegExp() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern",
                "a|b|c|d|block|dist|t|m");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "38:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "44:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "57:9: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "71:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "96:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "145:9: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "184:9: " + getCheckMessage(MSG_KEY, "result", 2, 1),
            "223:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "260:12: " + getCheckMessage(MSG_KEY, "sel", 2, 1),
            "261:9: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "287:9: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "300:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "343:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "344:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "367:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "454:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "455:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "471:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "471:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "479:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "504:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "505:15: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "540:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "542:13: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
        };
        verify(checkConfig, getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOption() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "false");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "38:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "44:9: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "71:9: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "96:9: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "219:9: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "222:9: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "223:9: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "300:9: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "343:9: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "344:9: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "367:9: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "454:9: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "455:9: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "471:9: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "471:9: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "479:9: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "491:9: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "505:15: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "540:13: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "542:13: " + getCheckMessage(MSG_KEY, "parentId", 4, 1),
            "978:9: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "1024:9: " + getCheckMessage(MSG_KEY, "c", 4, 1),
            "1054:9: " + getCheckMessage(MSG_KEY, "a", 4, 1),
        };
        verify(checkConfig, getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testIgnoreFinalOption() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "true");
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_KEY_EXT, "a", 2, 1),
            "38:9: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "44:9: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "57:9: " + getCheckMessage(MSG_KEY_EXT, "count", 2, 1),
            "71:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "96:9: " + getCheckMessage(MSG_KEY_EXT, "arg", 2, 1),
            "144:9: " + getCheckMessage(MSG_KEY_EXT, "m", 3, 1),
            "145:9: " + getCheckMessage(MSG_KEY_EXT, "n", 2, 1),
            "184:9: " + getCheckMessage(MSG_KEY_EXT, "result", 2, 1),
            "219:9: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 1),
            "222:9: " + getCheckMessage(MSG_KEY_EXT, "c", 3, 1),
            "223:9: " + getCheckMessage(MSG_KEY_EXT, "d2", 3, 1),
            "260:12: " + getCheckMessage(MSG_KEY_EXT, "sel", 2, 1),
            "261:9: " + getCheckMessage(MSG_KEY_EXT, "model", 2, 1),
            "287:9: " + getCheckMessage(MSG_KEY_EXT, "sw", 2, 1),
            "300:9: " + getCheckMessage(MSG_KEY_EXT, "wh", 2, 1),
            "343:9: " + getCheckMessage(MSG_KEY_EXT, "green", 2, 1),
            "344:9: " + getCheckMessage(MSG_KEY_EXT, "blue", 3, 1),
            "454:9: " + getCheckMessage(MSG_KEY_EXT, "aOpt", 3, 1),
            "455:9: " + getCheckMessage(MSG_KEY_EXT, "bOpt", 2, 1),
            "471:9: " + getCheckMessage(MSG_KEY_EXT, "l1", 3, 1),
            "471:9: " + getCheckMessage(MSG_KEY_EXT, "l2", 2, 1),
            "479:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 1),
            "491:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 1),
            "504:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "505:15: " + getCheckMessage(MSG_KEY_EXT, "files", 2, 1),
            "540:13: " + getCheckMessage(MSG_KEY_EXT, "id", 2, 1),
            "542:13: " + getCheckMessage(MSG_KEY_EXT, "parentId", 3, 1),
            "891:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "901:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "967:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
            "989:9: " + getCheckMessage(MSG_KEY_EXT, "a", 3, 1),
            "1024:9: " + getCheckMessage(MSG_KEY_EXT, "c", 3, 1),
            "1054:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 1),
        };
        verify(checkConfig, getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final VariableDeclarationUsageDistanceCheck check =
            new VariableDeclarationUsageDistanceCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        final String[] expected = {
            "71:9: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 3),
            "219:9: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 3),
            "479:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 3),
            "491:9: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 3),
            "542:13: " + getCheckMessage(MSG_KEY_EXT, "parentId", 4, 3),
            "1024:9: " + getCheckMessage(MSG_KEY_EXT, "c", 4, 3),
            "1054:9: " + getCheckMessage(MSG_KEY_EXT, "a", 4, 3),
        };

        verify(checkConfig, getPath("InputVariableDeclarationUsageDistance.java"), expected);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        final String[] expected = {
            "9:9: " + getCheckMessage(MSG_KEY_EXT, "prefs", 4, 3),
        };

        verify(checkConfig, getPath("InputVariableDeclarationUsageDistanceAnonymous.java"),
                expected);
    }

    @Test
    public void testLabels() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputVariableDeclarationUsageDistanceLabels.java"), expected);
    }

    @Test
    public void testVariableDeclarationUsageDistanceSwitchExpressions() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "false");

        final int maxDistance = 1;
        final String[] expected = {
            "32:17: " + getCheckMessage(MSG_KEY, "arg", 2, maxDistance),
            "77:17: " + getCheckMessage(MSG_KEY, "m", 3, maxDistance),
            "78:17: " + getCheckMessage(MSG_KEY, "n", 2, maxDistance),
            "110:17: " + getCheckMessage(MSG_KEY, "arg", 2, maxDistance),
            "152:17: " + getCheckMessage(MSG_KEY, "m", 3, maxDistance),
            "153:17: " + getCheckMessage(MSG_KEY, "n", 2, maxDistance),
            "174:17: " + getCheckMessage(MSG_KEY, "count", 3, maxDistance),
            "194:17: " + getCheckMessage(MSG_KEY, "count", 3, maxDistance),
            };

        final String filename = "InputVariableDeclarationUsageDistanceCheckSwitchExpressions.java";
        verify(checkConfig, getNonCompilablePath(filename), expected);
    }

}
