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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UncommentedMainCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/uncommentedmain";
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
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertArrayEquals(check.getDefaultTokens(),
                check.getAcceptableTokens(), "Invalid default tokens");
        assertArrayEquals(check.getDefaultTokens(),
                check.getRequiredTokens(), "Invalid acceptable tokens");
    }

    @Test
    public void testDeepDepth() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUncommentedMain2.java"), expected);
    }

    @Test
    public void testVisitPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        checkConfig.addAttribute("excludedClasses", "uncommentedmain\\.InputUncommentedMain5");
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputUncommentedMain5.java"), expected);
    }

    @Test
    public void testWrongName() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUncommentedMain3.java"), expected);
    }

    @Test
    public void testWrongArrayType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUncommentedMain4.java"), expected);
    }

    @Test
    public void testIllegalStateException() {
        final UncommentedMainCheck check = new UncommentedMainCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.CTOR_DEF, "ctor"));
        try {
            check.visitToken(ast);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            assertEquals(ast.toString(), ex.getMessage(), "Error message is unexpected");
        }
    }

}
