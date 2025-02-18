///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
    public void testThrowsCountDefault() throws Exception {

        final String[] expected = {
            "26:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "32:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "38:20: " + getCheckMessage(MSG_KEY, 6, 4),
            "66:43: " + getCheckMessage(MSG_KEY, 5, 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputThrowsCountDefault.java"), expected);
    }

    @Test
    public void testThrowsCountCustomMaxCount() throws Exception {

        final String[] expected = {
            "36:20: " + getCheckMessage(MSG_KEY, 6, 5),
        };

        verifyWithInlineConfigParser(
                getPath("InputThrowsCountCustomMaxCount.java"), expected);
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
    public void testThrowsCountNotIgnorePrivateMethods() throws Exception {
        final String[] expected = {
            "26:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "32:20: " + getCheckMessage(MSG_KEY, 5, 4),
            "38:20: " + getCheckMessage(MSG_KEY, 6, 4),
            "47:28: " + getCheckMessage(MSG_KEY, 5, 4),
            "67:43: " + getCheckMessage(MSG_KEY, 5, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputThrowsCountNotIgnorePrivateMethods.java"), expected);
    }

    @Test
    public void testThrowsCountMethodWithAnnotation() throws Exception {
        final String[] expected = {
            "27:26: " + getCheckMessage(MSG_KEY, 5, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputThrowsCountMethodWithAnnotation.java"), expected);
    }

    @Test
    public void testThrowsCountMaxAllowZero() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY, 1, 0),
            "21:20: " + getCheckMessage(MSG_KEY, 1, 0),
            "25:20: " + getCheckMessage(MSG_KEY, 5, 0),
            "31:20: " + getCheckMessage(MSG_KEY, 5, 0),
            "37:20: " + getCheckMessage(MSG_KEY, 6, 0),
            "46:28: " + getCheckMessage(MSG_KEY, 5, 0),
            "67:43: " + getCheckMessage(MSG_KEY, 5, 0),
        };
        verifyWithInlineConfigParser(
                getPath("InputThrowsCountMaxAllowZero.java"), expected);
    }

}
