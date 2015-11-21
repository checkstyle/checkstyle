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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import antlr.CommonHiddenStreamToken;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ThrowsCountCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "design" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);

        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "22:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "27:20: " + getCheckMessage(MSG_KEY, 6, 4),
            "55:43: " + getCheckMessage(MSG_KEY, 5, 4),
        };

        verify(checkConfig, getPath("InputThrowsCount.java"), expected);
    }

    @Test
    public void testMax() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);
        checkConfig.addAttribute("max", "5");

        final String[] expected = {
            "27:20: " + getCheckMessage(MSG_KEY, 6, 5),
        };

        verify(checkConfig, getPath("InputThrowsCount.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ThrowsCountCheck obj = new ThrowsCountCheck();
        final int[] expected = {TokenTypes.LITERAL_THROWS};
        assertArrayEquals(expected, obj.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        final ThrowsCountCheck obj = new ThrowsCountCheck();
        final int[] expected = {TokenTypes.LITERAL_THROWS};
        assertArrayEquals(expected, obj.getRequiredTokens());
    }

    @Test
    public void testWrongTokenType() {
        final ThrowsCountCheck obj = new ThrowsCountCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.CLASS_DEF, "class"));
        try {
            obj.visitToken(ast);
            fail();
        }
        catch (IllegalStateException ex) {
            assertEquals(ast.toString(), ex.getMessage());
        }
    }

    @Test
    public void testNotIgnorePrivateMethod() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);
        checkConfig.addAttribute("ignorePrivateMethods", "false");
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "22:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "27:20: " + getCheckMessage(MSG_KEY, 6, 4),
            "35:28: " + getCheckMessage(MSG_KEY, 5, 4),
            "55:43: " + getCheckMessage(MSG_KEY, 5, 4),
        };
        verify(checkConfig, getPath("InputThrowsCount.java"), expected);
    }
}
