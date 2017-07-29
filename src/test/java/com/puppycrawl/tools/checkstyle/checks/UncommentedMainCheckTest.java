////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck.MSG_KEY;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class UncommentedMainCheckTest
    extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/misc/uncommentedmain";
    }

    @Test
    public void testDefaults()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UncommentedMainCheck.class);
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY),
            "23: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "96: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputUncommentedMain.java"), expected);
    }

    @Test
    public void testExcludedClasses()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UncommentedMainCheck.class);
        checkConfig.addAttribute("excludedClasses", "\\.Main.*$");
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "96: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputUncommentedMain.java"), expected);
    }

    @Test
    public void testTokens() {
        final UncommentedMainCheck check = new UncommentedMainCheck();
        Assert.assertNotNull("Required tokens should not be null", check.getRequiredTokens());
        Assert.assertNotNull("Acceptable tokens should not be null", check.getAcceptableTokens());
        Assert.assertArrayEquals("Invalid default tokens", check.getDefaultTokens(),
                check.getAcceptableTokens());
        Assert.assertArrayEquals("Invalid acceptable tokens", check.getDefaultTokens(),
                check.getRequiredTokens());
    }

    @Test
    public void testDeepDepth() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUncommentedMain2.java"), expected);
    }

    @Test
    public void testWrongName() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUncommentedMain3.java"), expected);
    }

    @Test
    public void testWrongArrayType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUncommentedMain4.java"), expected);
    }

    @Test
    public void testIllegalStateException() {
        final UncommentedMainCheck check = new UncommentedMainCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.CTOR_DEF, "ctor"));
        try {
            check.visitToken(ast);
            Assert.fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Error message is unexpected",
                    ast.toString(), ex.getMessage());
        }

    }
}
