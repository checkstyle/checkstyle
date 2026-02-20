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

import static com.puppycrawl.tools.checkstyle.checks.coding.NoGetMessageInThrowCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class NoGetMessageInThrowCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nogetmessageinthrow";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_KEY, "ex"),
            "28:13: " + getCheckMessage(MSG_KEY, "exception"),
            "38:13: " + getCheckMessage(MSG_KEY, "e"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowDefault.java"), expected);
    }

    @Test
    public void testNoViolations() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowNoViolations.java"), expected);
    }

    @Test
    public void testCorrectUsage() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowCorrect.java"), expected);
    }

    @Test
    public void testDotIsNull() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowDotIsNull.java"),
                expected);
    }

    @Test
    public void testNested() throws Exception {
        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_KEY, "ex"),
            "33:17: " + getCheckMessage(MSG_KEY, "inner"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowNested.java"), expected);
    }

    @Test
    public void testMultipleCatches() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_KEY, "ex"),
            "37:13: " + getCheckMessage(MSG_KEY, "ex"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowMultipleCatches.java"), expected);
    }

    @Test
    public void testOnlyOneViolationReported() throws Exception {
        final String[] expected = {
            "18:17: " + getCheckMessage(MSG_KEY, "ex"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowMultipleThrows.java"),
                expected);
    }

    @Test
    public void testNonMethodCalls() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowNonMethodCalls.java"),
                expected);
    }

    @Test
    public void testSiblingTraversal() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_KEY, "ex"),
            "28:13: " + getCheckMessage(MSG_KEY, "ex"),
            "37:13: " + getCheckMessage(MSG_KEY, "ex"),
            "46:13: " + getCheckMessage(MSG_KEY, "ex"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowSiblingTraversal.java"),
                expected);
    }

    @Test
    public void testCoverage() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputNoGetMessageInThrowCoverage.java"), expected);
    }
}
