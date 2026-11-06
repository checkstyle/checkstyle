///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOfExpressionCheck.MSG_KEY_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOfExpressionCheck.MSG_KEY_LINE_PREVIOUS;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class RightCurlyOfExpressionCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/rightcurlyofexpression";
    }

    @Test
    public void testGetRequiredTokens() {
        final RightCurlyOfExpressionCheck checkObj = new RightCurlyOfExpressionCheck();
        final int[] expected = {
            TokenTypes.LAMBDA,
            TokenTypes.ARRAY_INIT,
            TokenTypes.LITERAL_NEW,
        };
        assertWithMessage("Required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final RightCurlyOfExpressionCheck checkObj = new RightCurlyOfExpressionCheck();
        final int[] expected = {
            TokenTypes.LAMBDA,
            TokenTypes.ARRAY_INIT,
            TokenTypes.LITERAL_NEW,
        };
        assertWithMessage("Acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final RightCurlyOfExpressionCheck checkObj = new RightCurlyOfExpressionCheck();
        final int[] expected = {
            TokenTypes.LAMBDA,
            TokenTypes.ARRAY_INIT,
            TokenTypes.LITERAL_NEW,
        };
        assertWithMessage("Default tokens are invalid")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "21:16: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 16),
            "29:16: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 16),
            "43:38: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 38),
            "53:24: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 24),
            "72:21: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 21),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyOfExpressionDefault.java"), expected);
    }

    @Test
    public void testOnSameLine() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "}", 5),
            "23:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "}", 5),
            "29:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "}", 5),
            "39:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "}", 5),
            "51:5: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "}", 5),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyOfExpressionOnSameLine.java"), expected);
    }

    @Test
    public void testOnNewLine() throws Exception {
        final String[] expected = {
            "15:69: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 69),
            "19:16: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 16),
            "22:50: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 50),
            "29:65: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 65),
            "38:65: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 65),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyOfExpressionOnNewLine.java"), expected);
    }

    @Test
    public void testCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "12:12: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 12),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                    "compact/InputRightCurlyOfExpressionCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY_LINE_PREVIOUS, "}", 1),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                    "compact/InputRightCurlyOfExpressionCompactSourceFileProperties.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileOnNewLine() throws Exception {
        final String[] expected = {
            "12:65: " + getCheckMessage(MSG_KEY_LINE_NEW, "}", 65),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                    "compact/InputRightCurlyOfExpressionCompactSourceFileOnNewLine.java"),
                expected);
    }

}
