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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SingleLineJavadocCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SingleLineJavadocCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/singlelinejavadoc";
    }

    @Test
    public void testAcceptableTokens() {
        final SingleLineJavadocCheck checkObj = new SingleLineJavadocCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final SingleLineJavadocCheck checkObj = new SingleLineJavadocCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void simpleTest() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_KEY),
            "39: " + getCheckMessage(MSG_KEY),
            "51: " + getCheckMessage(MSG_KEY),
            "54: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSingleLineJavadoc.java"), expected);
    }

    @Test
    public void testIgnoredTags() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY),
            "45: " + getCheckMessage(MSG_KEY),
            "49: " + getCheckMessage(MSG_KEY),
            "53: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
            "63: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSingleLineJavadocIgnoredTags.java"), expected);
    }

    /**
     * Demonstrate that we can show lexer errors as violation messages, and keep parsing.
     */
    @Test
    public void testLexerError() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(AbstractJavadocCheck.MSG_JAVADOC_PARSE_RULE_ERROR,
                        4, "token recognition error at: '@'", "@"),
            "19: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSingleLineJavadocLexerError.java"), expected);
    }

}
