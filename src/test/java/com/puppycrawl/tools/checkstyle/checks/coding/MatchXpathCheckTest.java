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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MatchXpathCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/matchxpath";
    }

    @Test
    public void testCheckWithEmptyQuery()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath.java"), expected);
    }

    @Test
    public void testNoStackoverflowError()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathNoStackoverflowError.java"), expected);
    }

    @Test
    public void testCheckWithImplicitEmptyQuery()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath2.java"), expected);
    }

    @Test
    public void testCheckWithMatchingMethodNames()
            throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "13:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath3.java"), expected);
    }

    @Test
    public void testCheckWithNoMatchingMethodName()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath4.java"), expected);
    }

    @Test
    public void testCheckWithSingleLineCommentsStartsWithSpace() throws Exception {
        final String[] expected = {
            "13:25: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "14:27: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathSingleLineComments.java"), expected);
    }

    @Test
    public void testCheckWithBlockComments() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "14:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathBlockComments.java"), expected);
    }

    @Test
    public void testCheckWithMultilineComments() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "20:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathMultilineComments.java"), expected);
    }

    @Test
    public void testCheckWithDoubleBraceInitialization()
            throws Exception {
        final String[] expected = {
            "18:35: Do not use double-brace initialization",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathDoubleBrace.java"), expected);
    }

    @Test
    public void testImitateIllegalThrowsCheck()
            throws Exception {
        final String[] expected = {
            "13:25: Illegal throws statement",
            "15:25: Illegal throws statement",
            "16:25: Illegal throws statement",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathIllegalThrows.java"), expected);
    }

    @Test
    public void testImitateExecutableStatementCountCheck()
            throws Exception {
        final String[] expected = {
            "25:5: Executable number of statements exceed threshold",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathExecutableStatementCount.java"), expected);
    }

    @Test
    public void testForbidPrintStackTrace()
            throws Exception {
        final String[] expected = {
            "18:27: printStackTrace() method calls are forbidden",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathForbidPrintStackTrace.java"), expected);
    }

    @Test
    public void testForbidParameterizedConstructor()
            throws Exception {
        final String[] expected = {
            "13:5: Parameterized constructors are not allowed",
            "15:5: Parameterized constructors are not allowed",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathForbidParameterizedConstructor.java"),
                expected);
    }

    @Test
    public void testAvoidInstanceCreationWithoutVar()
            throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathAvoidInstanceCreationWithoutVar.java"),
                expected);
    }

    @Test
    public void testInvalidQuery() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();

        try {
            matchXpathCheck.setQuery("!@#%^");
            assertWithMessage("Exception was expected").fail();
        }
        catch (IllegalStateException ignored) {
            // it is OK
        }
    }

    @Test
    public void testEvaluationException() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        matchXpathCheck.setQuery("count(*) div 0");

        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.CLASS_DEF);
        detailAST.setText("Class Def");
        detailAST.setLineNo(0);
        detailAST.setColumnNo(0);

        try {
            matchXpathCheck.beginTree(detailAST);
            assertWithMessage("Exception was expected").fail();
        }
        catch (IllegalStateException ignored) {
            // it is OK
        }
    }

    @Test
    public void testGetDefaultTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertWithMessage("Expected empty array")
                .that(matchXpathCheck.getDefaultTokens())
                .isEmpty();
    }

    @Test
    public void testGetAcceptableTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertWithMessage("Expected empty array")
                .that(matchXpathCheck.getAcceptableTokens())
                .isEmpty();
    }

    @Test
    public void testGetRequiredTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertWithMessage("Expected empty array")
                .that(matchXpathCheck.getRequiredTokens())
                .isEmpty();
    }

    @Test
    public void testMatchXpathWithFailedEvaluation() {
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> verifyWithInlineConfigParser(getPath("InputMatchXpath5.java")));
        assertThat(ex.getCause().getMessage())
                .isEqualTo("Evaluation of Xpath query failed: count(*) div 0");
    }
}
