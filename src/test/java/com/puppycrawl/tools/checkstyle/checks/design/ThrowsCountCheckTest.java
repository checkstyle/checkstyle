///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck.MSG_KEY;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ThrowsCountCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/throwscount";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "25:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "30:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "35:20: " + getCheckMessage(MSG_KEY, 6, 4),
            "63:43: " + getCheckMessage(MSG_KEY, 5, 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputThrowsCount.java"), expected);
    }

    @Test
    public void testMax() throws Exception {

        final String[] expected = {
            "35:20: " + getCheckMessage(MSG_KEY, 6, 5),
        };

        verifyWithInlineConfigParser(
                getPath("InputThrowsCount2.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ThrowsCountCheck obj = new ThrowsCountCheck();
        final int[] expected = {TokenTypes.LITERAL_THROWS};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(obj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final ThrowsCountCheck obj = new ThrowsCountCheck();
        final int[] expected = {TokenTypes.LITERAL_THROWS};
        assertWithMessage("Default required tokens are invalid")
            .that(obj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testWrongTokenType() {
        final ThrowsCountCheck obj = new ThrowsCountCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.CLASS_DEF, "class"));
        try {
            obj.visitToken(ast);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo(ast.toString());
        }
    }

    @Test
    public void testNotIgnorePrivateMethod() throws Exception {
        final String[] expected = {
            "25:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "30:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "35:20: " + getCheckMessage(MSG_KEY, 6, 4),
            "43:28: " + getCheckMessage(MSG_KEY, 5, 4),
            "63:43: " + getCheckMessage(MSG_KEY, 5, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputThrowsCount3.java"), expected);
    }

    @Test
    public void testMethodWithAnnotation() throws Exception {
        final String[] expected = {
            "26:26: " + getCheckMessage(MSG_KEY, 5, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputThrowsCountMethodWithAnnotation.java"), expected);
    }

}
