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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MultilineCommentLeadingAsteriskPresenceCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/multilinecommentleadingasteriskpresence";
    }

    @Test
    public void testGetRequiredTokens() {
        final MultilineCommentLeadingAsteriskPresenceCheck checkObj =
                new MultilineCommentLeadingAsteriskPresenceCheck();
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        assertWithMessage("Required tokens differs from expected")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testAcceptableTokens() {
        final MultilineCommentLeadingAsteriskPresenceCheck checkObj =
                new MultilineCommentLeadingAsteriskPresenceCheck();
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        assertWithMessage("Accepted tokens differs from expected")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testInputMultilineComment1() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_MISSING_ASTERISK, "2, 3"),
            "12:5: " + getCheckMessage(MSG_MISSING_ASTERISK, "13"),
            "20:5: " + getCheckMessage(MSG_MISSING_ASTERISK, "21, 22, 23"),
            "30:5: " + getCheckMessage(MSG_MISSING_ASTERISK, "31, 32, 33, 34"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultilineCommentLeadingAsteriskPresence1.java"),
                expected);
    }

    @Test
    public void testInputMultilineCommentCorrect() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_MISSING_ASTERISK, "2, 3"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultilineCommentLeadingAsteriskPresenceCorrect.java"),
                expected);
    }

    @Test
    public void testNotMultilineComment() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_MISSING_ASTERISK, "2, 3"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultilineCommentLeadingAsteriskPresenceJavadoc.java"),
                expected);
    }

    @Test
    public void testMissingAsteriskInBetween() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_MISSING_ASTERISK, "2, 3"),
            "12:5: " + getCheckMessage(MSG_MISSING_ASTERISK, "14"),
            "21:5: " + getCheckMessage(MSG_MISSING_ASTERISK, "23, 24"),
            "31:5: " + getCheckMessage(MSG_MISSING_ASTERISK, "32, 34"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultilineCommentLeadingAsteriskPresence2.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_MISSING_ASTERISK, "2, 3"),
        };

        final String filename =
                "compact/InputMultilineCommentLeadingAsteriskPresenceCompactSorceFile.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

}
