////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
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
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMatchXpath.java"), expected);
    }

    @Test
    public void testNoStackoverflowError()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//STRING_LITERAL[not(@text='')]");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMatchXpathNoStackoverflowError.java"), expected);
    }

    @Test
    public void testCheckWithImplicitEmptyQuery()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMatchXpath.java"), expected);
    }

    @Test
    public void testCheckWithMatchingMethodNames()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//METHOD_DEF[./IDENT[@text='test' or @text='foo']]");
        final String[] expected = {
            "8:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
            "10:5: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verify(checkConfig, getPath("InputMatchXpath.java"), expected);
    }

    @Test
    public void testCheckWithNoMatchingMethodName()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//METHOD_DEF[./IDENT[@text='wrongName' or "
                + "@text='nonExistingMethod']]");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputMatchXpath.java"), expected);
    }

    @Test
    public void testCheckWithDoubleBraceInitialization()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);

        checkConfig.addProperty("query", "//INSTANCE_INIT[not(../*[not(\n"
                + "                    self::LCURLY or\n"
                + "                    self::INSTANCE_INIT or\n"
                + "                    self::RCURLY or\n"
                + "                    self::SINGLE_LINE_COMMENT or\n"
                + "                    self::BLOCK_COMMENT_BEGIN\n"
                + "                )])]");
        checkConfig.addMessage("matchxpath.match", "Do not use double-brace initialization");
        final String[] expected = {
            "12:35: Do not use double-brace initialization",
        };
        verify(checkConfig, getPath("InputMatchXpathDoubleBrace.java"), expected);
    }

    @Test
    public void testImitateIllegalThrowsCheck()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//LITERAL_THROWS[./IDENT[@text='Throwable' or "
                + "@text='RuntimeException' or ends-with(@text, 'Error')]]");
        checkConfig.addMessage("matchxpath.match", "Illegal throws statement");
        final String[] expected = {
            "9:25: Illegal throws statement",
            "11:25: Illegal throws statement",
            "12:25: Illegal throws statement",
        };
        verify(checkConfig, getPath("InputMatchXpathIllegalThrows.java"), expected);
    }

    @Test
    public void testImitateExecutableStatementCountCheck()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//METHOD_DEF[count(./SLIST/*) > 2]");
        checkConfig.addMessage("matchxpath.match", "Executable number of statements "
                + "exceed threshold");
        final String[] expected = {
            "21:5: Executable number of statements exceed threshold",
        };
        verify(checkConfig, getPath("InputMatchXpathExecutableStatementCount.java"), expected);
    }

    @Test
    public void testForbidPrintStackTrace()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//LITERAL_CATCH"
                + "//METHOD_CALL[.//IDENT[@text = 'printStackTrace']]/..");
        checkConfig.addMessage("matchxpath.match", "printStackTrace() method calls are forbidden");
        final String[] expected = {
            "14:27: printStackTrace() method calls are forbidden",
        };
        verify(checkConfig, getPath("InputMatchXpathForbidPrintStackTrace.java"), expected);
    }

    @Test
    public void testForbidParameterizedConstructor()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//CTOR_DEF[count(./PARAMETERS/*) > 0]");
        checkConfig.addMessage("matchxpath.match", "Parameterized constructors are not allowed");
        final String[] expected = {
            "9:5: Parameterized constructors are not allowed",
            "11:5: Parameterized constructors are not allowed",
        };
        verify(checkConfig, getPath("InputMatchXpathForbidParameterizedConstructor.java"),
                expected);
    }

    @Test
    public void testAvoidInstanceCreationWithoutVar()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addProperty("query", "//VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW "
                + "and not(./TYPE/IDENT[@text='var'])]");
        final String[] expected = {
            "10:9: " + getCheckMessage(MatchXpathCheck.MSG_KEY),
        };
        verify(checkConfig,
                getNonCompilablePath("InputMatchXpathAvoidInstanceCreationWithoutVar.java"),
                expected);
    }

    @Test
    public void testInvalidQuery() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();

        try {
            matchXpathCheck.setQuery("!@#%^");
            fail("Exception was expected");
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
            fail("Exception was expected");
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
}
