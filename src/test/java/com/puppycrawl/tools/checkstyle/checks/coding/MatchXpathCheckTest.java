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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/matchxpath";
    }

    @Test
    void checkWithEmptyQuery()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath.java"), expected);
    }

    @Test
    void noStackoverflowError()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathNoStackoverflowError.java"), expected);
    }

    @Test
    void checkWithImplicitEmptyQuery()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath2.java"), expected);
    }

    @Test
    void checkWithMatchingMethodNames()
            throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "13:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath3.java"), expected);
    }

    @Test
    void checkWithNoMatchingMethodName()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMatchXpath4.java"), expected);
    }

    @Test
    void checkWithSingleLineCommentsStartsWithSpace() throws Exception {
        final String[] expected = {
            "13:25: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "14:27: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathSingleLineComments.java"), expected);
    }

    @Test
    void checkWithBlockComments() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "14:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathBlockComments.java"), expected);
    }

    @Test
    void checkWithMultilineComments() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "20:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathMultilineComments.java"), expected);
    }

    @Test
    void checkWithDoubleBraceInitialization()
            throws Exception {
        final String[] expected = {
            "18:35: Do not use double-brace initialization",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathDoubleBrace.java"), expected);
    }

    @Test
    void imitateIllegalThrowsCheck()
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
    void imitateExecutableStatementCountCheck()
            throws Exception {
        final String[] expected = {
            "25:5: Executable number of statements exceed threshold",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathExecutableStatementCount.java"), expected);
    }

    @Test
    void forbidPrintStackTrace()
            throws Exception {
        final String[] expected = {
            "18:27: printStackTrace() method calls are forbidden",
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathForbidPrintStackTrace.java"), expected);
    }

    @Test
    void forbidParameterizedConstructor()
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
    void avoidInstanceCreationWithoutVar()
            throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputMatchXpathAvoidInstanceCreationWithoutVar.java"),
                expected);
    }

    @Test
    void invalidQuery() {
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
    void evaluationException() {
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
    void getDefaultTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertWithMessage("Expected empty array")
                .that(matchXpathCheck.getDefaultTokens())
                .isEmpty();
    }

    @Test
    void getAcceptableTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertWithMessage("Expected empty array")
                .that(matchXpathCheck.getAcceptableTokens())
                .isEmpty();
    }

    @Test
    void getRequiredTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertWithMessage("Expected empty array")
                .that(matchXpathCheck.getRequiredTokens())
                .isEmpty();
    }

    @Test
    void matchXpathWithFailedEvaluation() {
        final CheckstyleException ex = getExpectedThrowable(CheckstyleException.class,
                () -> verifyWithInlineConfigParser(getPath("InputMatchXpath5.java")));
        assertThat(ex.getCause().getMessage())
                .isEqualTo("Evaluation of Xpath query failed: count(*) div 0");
    }
}
