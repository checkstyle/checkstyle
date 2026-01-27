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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.NoGetMessageInCatchThrowCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class NoGetMessageInCatchThrowCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nogetmessageincatchthrow";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "ex"),
            "29:13: " + getCheckMessage(MSG_KEY, "exception"),
            "40:13: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInCatchThrowDefault.java"), expected);
    }

    @Test
    public void testNoViolations() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInCatchThrowNoViolations.java"), expected);
    }

    @Test
    public void testCorrectUsage() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInCatchThrowCorrect.java"), expected);
    }

    @Test
    public void testDotIsNull() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInCatchThrowDotIsNull.java"),
                expected);
    }

    @Test
    public void testNested() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_KEY, "ex"),
            "34:17: " + getCheckMessage(MSG_KEY, "inner"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInCatchThrowNested.java"), expected);
    }

    @Test
    public void testMultipleCatches() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_KEY, "ex"),
            "35:13: " + getCheckMessage(MSG_KEY, "ex"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInCatchThrowMultipleCatches.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final NoGetMessageInCatchThrowCheck check = new NoGetMessageInCatchThrowCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testAcceptableTokensEqualsRequiredTokens() {
        final NoGetMessageInCatchThrowCheck check = new NoGetMessageInCatchThrowCheck();
        assertWithMessage("Acceptable tokens should equal required tokens")
            .that(check.getAcceptableTokens())
            .isEqualTo(check.getRequiredTokens());
    }

    @Test
    public void testDefaultTokensEqualsRequiredTokens() {
        final NoGetMessageInCatchThrowCheck check = new NoGetMessageInCatchThrowCheck();
        assertWithMessage("Default tokens should equal required tokens")
            .that(check.getDefaultTokens())
            .isEqualTo(check.getRequiredTokens());
    }
}
