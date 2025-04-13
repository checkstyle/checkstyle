///
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
///

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SingleLineJavadocCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SingleLineJavadocCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
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
            "38: " + getCheckMessage(MSG_KEY),
            "50: " + getCheckMessage(MSG_KEY),
            "53: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSingleLineJavadoc.java"), expected);
    }

    @Test
    public void testIgnoredTags() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY),
            "44: " + getCheckMessage(MSG_KEY),
            "47: " + getCheckMessage(MSG_KEY),
            "50: " + getCheckMessage(MSG_KEY),
            "56: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSingleLineJavadocIgnoredTags.java"), expected);
    }

}
