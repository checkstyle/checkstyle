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

import static com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ReturnCountCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ReturnCountCheck.class);
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY, 7, 2),
            "35:17: " + getCheckMessage(MSG_KEY, 6, 2),
        };
        verify(checkConfig, getPath("InputReturnCount.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("format", "^$");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_KEY, 7, 2),
            "18:5: " + getCheckMessage(MSG_KEY, 7, 2),
            "35:17: " + getCheckMessage(MSG_KEY, 6, 2),
        };
        verify(checkConfig, getPath("InputReturnCount.java"), expected);
    }

    @Test
    public void testMethodsAndLambdas() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("max", "1");
        final String[] expected = {
            "14:55: " + getCheckMessage(MSG_KEY, 2, 1),
            "26:49: " + getCheckMessage(MSG_KEY, 2, 1),
            "33:42: " + getCheckMessage(MSG_KEY, 3, 1),
            "40:5: " + getCheckMessage(MSG_KEY, 2, 1),
            "48:57: " + getCheckMessage(MSG_KEY, 2, 1),
        };
        verify(checkConfig, getNonCompilablePath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testLambdasOnly() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "33:42: " + getCheckMessage(MSG_KEY, 3, 2),
        };
        verify(checkConfig, getNonCompilablePath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testMethodsOnly() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_DEF");
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, 3, 2),
            "32:5: " + getCheckMessage(MSG_KEY, 4, 2),
            "40:5: " + getCheckMessage(MSG_KEY, 4, 2),
            "55:5: " + getCheckMessage(MSG_KEY, 3, 2),
        };
        verify(checkConfig, getNonCompilablePath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testWithReturnOnlyAsTokens() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(ReturnCountCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_RETURN");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputReturnCountLambda.java"), expected);
    }

    @Test
    public void testImproperToken() throws Exception {
        final ReturnCountCheck check = new ReturnCountCheck();

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
