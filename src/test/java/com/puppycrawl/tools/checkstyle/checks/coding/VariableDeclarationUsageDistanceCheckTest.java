////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class VariableDeclarationUsageDistanceCheckTest extends
        BaseCheckTestSupport {
    @Test
    public void testGeneralLogic() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "30: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "38: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "44: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "57: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "71: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "96: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "144: " + getCheckMessage(MSG_KEY, "m", 3, 1),
            "145: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "184: " + getCheckMessage(MSG_KEY, "result", 2, 1),
            "219: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "222: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "223: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "260: " + getCheckMessage(MSG_KEY, "selected", 2, 1),
            "261: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "287: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "300: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "343: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "344: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "367: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "454: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "455: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "471: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "471: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "479: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "491: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "504: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "505: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "540: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "542: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
        };
        verify(checkConfig, getPath("coding/InputVariableDeclarationUsageDistanceCheck.java"), expected);
    }

    @Test
    public void testDistance() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "3");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "71: " + getCheckMessage(MSG_KEY, "count", 4, 3),
            "219: " + getCheckMessage(MSG_KEY, "t", 5, 3),
            "479: " + getCheckMessage(MSG_KEY, "myOption", 7, 3),
            "491: " + getCheckMessage(MSG_KEY, "myOption", 6, 3),
            "504: " + getCheckMessage(MSG_KEY, "count", 4, 3),
        };
        verify(checkConfig, getPath("coding/InputVariableDeclarationUsageDistanceCheck.java"), expected);
    }

    @Test
    public void testVariableRegExp() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern",
                "a|b|c|d|block|dist|t|m");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "38: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "44: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "57: " + getCheckMessage(MSG_KEY, "count", 2, 1),
            "71: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "96: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "145: " + getCheckMessage(MSG_KEY, "n", 2, 1),
            "184: " + getCheckMessage(MSG_KEY, "result", 2, 1),
            "223: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "260: " + getCheckMessage(MSG_KEY, "selected", 2, 1),
            "261: " + getCheckMessage(MSG_KEY, "model", 2, 1),
            "287: " + getCheckMessage(MSG_KEY, "sw", 2, 1),
            "300: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "343: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "344: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "367: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "454: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "455: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "471: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "471: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "479: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "491: " + getCheckMessage(MSG_KEY, "myOption", 6, 1),
            "504: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "505: " + getCheckMessage(MSG_KEY, "files", 2, 1),
            "540: " + getCheckMessage(MSG_KEY, "id", 2, 1),
            "542: " + getCheckMessage(MSG_KEY, "parentId", 3, 1),
        };
        verify(checkConfig, getPath("coding/InputVariableDeclarationUsageDistanceCheck.java"), expected);
    }

    @Test
    public void testValidateBetweenScopesOption() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "false");
        checkConfig.addAttribute("ignoreFinal", "false");
        final String[] expected = {
            "30: " + getCheckMessage(MSG_KEY, "a", 2, 1),
            "38: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "44: " + getCheckMessage(MSG_KEY, "temp", 2, 1),
            "71: " + getCheckMessage(MSG_KEY, "count", 4, 1),
            "96: " + getCheckMessage(MSG_KEY, "arg", 2, 1),
            "219: " + getCheckMessage(MSG_KEY, "t", 5, 1),
            "222: " + getCheckMessage(MSG_KEY, "c", 3, 1),
            "223: " + getCheckMessage(MSG_KEY, "d2", 3, 1),
            "300: " + getCheckMessage(MSG_KEY, "wh", 2, 1),
            "343: " + getCheckMessage(MSG_KEY, "green", 2, 1),
            "344: " + getCheckMessage(MSG_KEY, "blue", 3, 1),
            "367: " + getCheckMessage(MSG_KEY, "intervalMs", 2, 1),
            "454: " + getCheckMessage(MSG_KEY, "aOpt", 3, 1),
            "455: " + getCheckMessage(MSG_KEY, "bOpt", 2, 1),
            "471: " + getCheckMessage(MSG_KEY, "l1", 3, 1),
            "471: " + getCheckMessage(MSG_KEY, "l2", 2, 1),
            "479: " + getCheckMessage(MSG_KEY, "myOption", 7, 1),
            "491: Distance between variable 'myOption' declaration and its first usage is 6, but allowed 1.",
            "505: Distance between variable 'files' declaration and its first usage is 2, but allowed 1.",
            "540: Distance between variable 'id' declaration and its first usage is 2, but allowed 1.",
            "542: Distance between variable 'parentId' declaration and its first usage is 4, but allowed 1.",
        };
        verify(checkConfig, getPath("coding/InputVariableDeclarationUsageDistanceCheck.java"), expected);
    }

    @Test
    public void testIgnoreFinalOption() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(VariableDeclarationUsageDistanceCheck.class);
        checkConfig.addAttribute("allowedDistance", "1");
        checkConfig.addAttribute("ignoreVariablePattern", "");
        checkConfig.addAttribute("validateBetweenScopes", "true");
        checkConfig.addAttribute("ignoreFinal", "true");
        final String[] expected = {
            "30: " + getCheckMessage(MSG_KEY_EXT, "a", 2, 1),
            "38: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "44: " + getCheckMessage(MSG_KEY_EXT, "temp", 2, 1),
            "57: " + getCheckMessage(MSG_KEY_EXT, "count", 2, 1),
            "71: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "96: " + getCheckMessage(MSG_KEY_EXT, "arg", 2, 1),
            "144: " + getCheckMessage(MSG_KEY_EXT, "m", 3, 1),
            "145: " + getCheckMessage(MSG_KEY_EXT, "n", 2, 1),
            "184: " + getCheckMessage(MSG_KEY_EXT, "result", 2, 1),
            "219: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 1),
            "222: " + getCheckMessage(MSG_KEY_EXT, "c", 3, 1),
            "223: " + getCheckMessage(MSG_KEY_EXT, "d2", 3, 1),
            "260: " + getCheckMessage(MSG_KEY_EXT, "selected", 2, 1),
            "261: " + getCheckMessage(MSG_KEY_EXT, "model", 2, 1),
            "287: " + getCheckMessage(MSG_KEY_EXT, "sw", 2, 1),
            "300: " + getCheckMessage(MSG_KEY_EXT, "wh", 2, 1),
            "343: " + getCheckMessage(MSG_KEY_EXT, "green", 2, 1),
            "344: " + getCheckMessage(MSG_KEY_EXT, "blue", 3, 1),
            "454: " + getCheckMessage(MSG_KEY_EXT, "aOpt", 3, 1),
            "455: " + getCheckMessage(MSG_KEY_EXT, "bOpt", 2, 1),
            "471: " + getCheckMessage(MSG_KEY_EXT, "l1", 3, 1),
            "471: " + getCheckMessage(MSG_KEY_EXT, "l2", 2, 1),
            "479: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 1),
            "491: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 1),
            "504: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 1),
            "505: " + getCheckMessage(MSG_KEY_EXT, "files", 2, 1),
            "540: " + getCheckMessage(MSG_KEY_EXT, "id", 2, 1),
            "542: " + getCheckMessage(MSG_KEY_EXT, "parentId", 3, 1),
        };
        verify(checkConfig, getPath("coding/InputVariableDeclarationUsageDistanceCheck.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        VariableDeclarationUsageDistanceCheck check = new VariableDeclarationUsageDistanceCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(VariableDeclarationUsageDistanceCheck.class);
        final String[] expected = {
            "71: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 3),
            "219: " + getCheckMessage(MSG_KEY_EXT, "t", 5, 3),
            "479: " + getCheckMessage(MSG_KEY_EXT, "myOption", 7, 3),
            "491: " + getCheckMessage(MSG_KEY_EXT, "myOption", 6, 3),
            "542: " + getCheckMessage(MSG_KEY_EXT, "parentId", 4, 3),
        };

        try {
            createChecker(checkConfig);
            verify(checkConfig, getPath("coding/InputVariableDeclarationUsageDistanceCheck.java"), expected);
        }
        catch (Exception ex) {
            //Exception is not expected
            fail();
        }
    }
}
