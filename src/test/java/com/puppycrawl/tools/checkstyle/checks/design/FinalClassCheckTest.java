////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class FinalClassCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/finalclass";
    }

    @Test
    public void testGetRequiredTokens() {
        final FinalClassCheck checkObj = new FinalClassCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF, TokenTypes.PACKAGE_DEF};
        assertArrayEquals("Default required tokens are invalid",
            expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testFinalClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(FinalClassCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY, "InputFinalClass"),
            "15: " + getCheckMessage(MSG_KEY, "test4"),
            "113: " + getCheckMessage(MSG_KEY, "someinnerClass"),
        };
        verify(checkConfig, getPath("InputFinalClass.java"), expected);
    }

    @Test
    public void testClassWithPrivateCtorAndNestedExtendingSubclass() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(FinalClassCheck.class);
        final String[] expected = {
            "16: " + getCheckMessage(MSG_KEY, "C"),
        };
        verify(checkConfig,
                getNonCompilablePath(
                        "InputFinalClassClassWithPrivateCtorWithNestedExtendingClass.java"),
                expected);
    }

    @Test
    public void testClassWithPrivateCtorAndNestedExtendingSubclassWithoutPackage()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(FinalClassCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY, "C"),
        };
        verify(checkConfig,
                getNonCompilablePath(
                "InputFinalClassClassWithPrivateCtorWithNestedExtendingClassWithoutPackage.java"),
                expected);
    }

    @Test
    public void testImproperToken() {
        final FinalClassCheck finalClassCheck = new FinalClassCheck();
        final DetailAST badAst = new DetailAST();
        final int unsupportedTokenByCheck = TokenTypes.EOF;
        badAst.setType(unsupportedTokenByCheck);
        try {
            finalClassCheck.visitToken(badAst);
            Assert.fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

    @Test
    public void testGetAcceptableTokens() {
        final FinalClassCheck obj = new FinalClassCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.CTOR_DEF, TokenTypes.PACKAGE_DEF};
        assertArrayEquals("Default acceptable tokens are invalid",
            expected, obj.getAcceptableTokens());
    }

}
