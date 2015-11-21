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

import static com.puppycrawl.tools.checkstyle.checks.coding.ModifiedControlVariableCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ModifiedControlVariableCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testModifiedControlVariable() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifiedControlVariableCheck.class);
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_KEY, "i"),
            "17:15: " + getCheckMessage(MSG_KEY, "i"),
            "20:37: " + getCheckMessage(MSG_KEY, "i"),
            "21:17: " + getCheckMessage(MSG_KEY, "i"),
            "49:15: " + getCheckMessage(MSG_KEY, "s"),
            "56:14: " + getCheckMessage(MSG_KEY, "m"),
            "67:15: " + getCheckMessage(MSG_KEY, "i"),
            "68:15: " + getCheckMessage(MSG_KEY, "k"),
        };
        verify(checkConfig, getPath("InputModifiedControl.java"), expected);
    }

    @Test
    public void testEnhancedForLoopVariableTrue() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifiedControlVariableCheck.class);
        checkConfig.addAttribute("skipEnhancedForLoopVariable", "true");

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputModifiedControlVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testEnhancedForLoopVariableFalse() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifiedControlVariableCheck.class);

        final String[] expected = {
            "9:18: " + getCheckMessage(MSG_KEY, "line"),
        };
        verify(checkConfig, getPath("InputModifiedControlVariableEnhancedForLoopVariable.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final ModifiedControlVariableCheck check = new ModifiedControlVariableCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }

    @Test
    public void testImproperToken() throws Exception {
        final ModifiedControlVariableCheck check = new ModifiedControlVariableCheck();

        final DetailAST classDefAst = new DetailAST();
        classDefAst.setType(TokenTypes.CLASS_DEF);

        try {
            check.visitToken(classDefAst);
            Assert.fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }

        try {
            check.leaveToken(classDefAst);
            Assert.fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }
}
