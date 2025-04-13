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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InvalidJavadocPositionCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/invalidjavadocposition";
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        final InvalidJavadocPositionCheck check = new InvalidJavadocPositionCheck();
        final int[] actual = check.getAcceptableTokens();

        assertWithMessage("Acceptable tokens differs from expected")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        final InvalidJavadocPositionCheck check = new InvalidJavadocPositionCheck();
        final int[] actual = check.getRequiredTokens();

        assertWithMessage("Required tokens differ from expected")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "7:9: " + getCheckMessage(MSG_KEY),
            "10:1: " + getCheckMessage(MSG_KEY),
            "13:1: " + getCheckMessage(MSG_KEY),
            "16:5: " + getCheckMessage(MSG_KEY),
            "21:5: " + getCheckMessage(MSG_KEY),
            "24:5: " + getCheckMessage(MSG_KEY),
            "34:9: " + getCheckMessage(MSG_KEY),
            "35:17: " + getCheckMessage(MSG_KEY),
            "36:17: " + getCheckMessage(MSG_KEY),
            "46:10: " + getCheckMessage(MSG_KEY),
            "47:19: " + getCheckMessage(MSG_KEY),
            "48:19: " + getCheckMessage(MSG_KEY),
            "49:21: " + getCheckMessage(MSG_KEY),
            "50:23: " + getCheckMessage(MSG_KEY),
            "51:23: " + getCheckMessage(MSG_KEY),
            "54:1: " + getCheckMessage(MSG_KEY),
            "59:7: " + getCheckMessage(MSG_KEY),
            "60:36: " + getCheckMessage(MSG_KEY),
            "63:9: " + getCheckMessage(MSG_KEY),
            "64:9: " + getCheckMessage(MSG_KEY),
            "65:9: " + getCheckMessage(MSG_KEY),
            "73:6: " + getCheckMessage(MSG_KEY),
            "76:24: " + getCheckMessage(MSG_KEY),
            "79:43: " + getCheckMessage(MSG_KEY),
            "82:69: " + getCheckMessage(MSG_KEY),
            "94:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInvalidJavadocPosition.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final String[] expected = {
            "7:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageInfoComment() throws Exception {
        final String[] expected = {
            "7:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("comment/package-info.java"), expected);
    }

}
