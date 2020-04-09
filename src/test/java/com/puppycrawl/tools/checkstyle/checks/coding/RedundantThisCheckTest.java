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

import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_VARIABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.SortedSet;

import org.junit.jupiter.api.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Test fixture for the RedundantThisCheck.
 *
 */
public class RedundantThisCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/redundantthis";
    }

    @Test
    public void testSimple() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_VARIABLE, "x"),
            "18:9: " + getCheckMessage(MSG_METHOD, "methodTwo"),
            "20:16: " + getCheckMessage(MSG_VARIABLE, "x"),
            "24:9: " + getCheckMessage(MSG_METHOD, "methodThree"),
            "38:9: " + getCheckMessage(MSG_VARIABLE, "y"),
        };

        verify(checkConfig, getPath("InputRedundantThisSimple.java"), expected);
    }

    @Test
    public void testMethodsOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RedundantThisCheck.class);
        checkConfig.addAttribute("checkFields", "false");

        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_METHOD, "methodTwo"),
            "20:9: " + getCheckMessage(MSG_METHOD, "methodThree"),
        };

        verify(checkConfig, getPath("InputRedundantThisMethodsOnly.java"), expected);
    }

    @Test
    public void testFieldsOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RedundantThisCheck.class);
        checkConfig.addAttribute("checkMethods", "false");

        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_VARIABLE, "x"),
            "23:16: " + getCheckMessage(MSG_VARIABLE, "y"),
        };

        verify(checkConfig, getPath("InputRedundantThisFieldsOnly.java"), expected);
    }

    @Test
    public void testAnonymousObjects() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_METHOD, "doSideEffect"),
            "24:9: " + getCheckMessage(MSG_VARIABLE, "bar"),
            "36:25: " + getCheckMessage(MSG_VARIABLE, "name"),
            "45:9: " + getCheckMessage(MSG_METHOD, "methodFour"),
            "49:17: " + getCheckMessage(MSG_VARIABLE, "member"),
        };
        verify(checkConfig, getPath("InputRedundantThisAnonymousObjects.java"), expected);
    }

    @Test
    public void testBraceAlone() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_VARIABLE, "var1"),
        };
        verify(checkConfig, getPath("InputRedundantThisBraceAlone.java"), expected);
    }

    @Test
    public void testCatchVariables() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "16:23: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "24:28: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "25:21: " + getCheckMessage(MSG_METHOD, "debug"),
        };
        verify(checkConfig, getPath("InputRedundantThisCatchVariables.java"), expected);
    }

    @Test
    public void testFor() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_VARIABLE, "bottom"),
            "23:37: " + getCheckMessage(MSG_VARIABLE, "name"),
        };
        verify(checkConfig, getPath("InputRedundantThisFor.java"), expected);
    }

    @Test
    public void testCaseGroup() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "29:21: " + getCheckMessage(MSG_VARIABLE, "aVariable"),
            "30:29: " + getCheckMessage(MSG_VARIABLE, "aVariable"),
            "55:24: " + getCheckMessage(MSG_METHOD, "method3"),
        };
        verify(checkConfig, getPath("InputRedundantThisCaseGroup.java"), expected);
    }

    @Test
    public void testEnumInnerClasses() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_METHOD, "method1"),
            "23:9: " + getCheckMessage(MSG_VARIABLE, "i"),
            "33:9: " + getCheckMessage(MSG_METHOD, "method3"),
            "43:13: " + getCheckMessage(MSG_VARIABLE, "z"),
        };
        verify(checkConfig, getPath("InputRedundantThisEnumInnerClasses.java"), expected);
    }

    @Test
    public void testLambdaParameters() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRedundantThisLambdaParameters.java"), expected);
    }

    @Test
    public void testInnerClassAndLimitation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "19:17: " + getCheckMessage(MSG_VARIABLE, "x"),
            "24:13: " + getCheckMessage(MSG_VARIABLE, "x"),
        };
        verify(checkConfig, getPath("InputRedundantThisInnerClassAndLimitation.java"), expected);
    }

    @Test
    public void testReceiver() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRedundantThisReceiver.java"), expected);
    }

    @Test
    public void testTypeArguments() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "10:9: " + getCheckMessage(MSG_VARIABLE, "i"),
        };
        verify(checkConfig, getPath("InputRedundantThisTypeArguments.java"), expected);
    }

    @Test
    public void testTryWithResourcesVariables() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantThisCheck.class);

        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "18:9: " + getCheckMessage(MSG_VARIABLE, "scanner"),
        };
        verify(checkConfig, getPath("InputRedundantThisTryWithResourcesVariables.java"), expected);
    }

    @Test
    public void testDefaultSwitch() {
        final RedundantThisCheck check = new RedundantThisCheck();

        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.ENUM, "ENUM"));

        check.visitToken(ast);
        final SortedSet<LocalizedMessage> messages = check.getMessages();

        assertEquals(0, messages.size(), "No exception messages expected");
    }

    @Test
    public void testTokensNotNull() {
        final RedundantThisCheck check = new RedundantThisCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Acceptable tokens should not be null");
    }
}
