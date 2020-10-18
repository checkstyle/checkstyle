////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
        final String[] expected = {};
        verify(checkConfig, getPath("InputMatchXpath.java"), expected);
    }

    @Test
    public void testCheckWithMatchingMethodNames()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MatchXpathCheck.class);
        checkConfig.addAttribute("query", "//METHOD_DEF[./IDENT[@text='test' or @text='foo']]");
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
        checkConfig.addAttribute("query", "//METHOD_DEF[./IDENT[@text='wrongName' or "
                + "@text='nonExistingMethod']]");
        final String[] expected = {};
        verify(checkConfig, getPath("InputMatchXpath.java"), expected);
    }

    @Test
    public void testCheckWithDoubleBraceInitialization()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);

        checkConfig.addAttribute("query", "//INSTANCE_INIT[not(../*[not(\n"
                + "                    self::LCURLY or\n"
                + "                    self::INSTANCE_INIT or\n"
                + "                    self::RCURLY or\n"
                + "                    self::SINGLE_LINE_COMMENT or\n"
                + "                    self::BLOCK_COMMENT_BEGIN\n"
                + "                )])]");
        checkConfig.addMessage("matchxpath.match", "Do not use double-brace initialization");
        final String[] expected = {
            "11:35: Do not use double-brace initialization",
        };
        verify(checkConfig, getPath("InputMatchXpathDoubleBrace.java"), expected);
    }

    @Test
    public void testImitateIllegalThrowsCheck()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MatchXpathCheck.class);
        checkConfig.addAttribute("query", "//LITERAL_THROWS[./IDENT[@text='Throwable' or "
                + "@text='RuntimeException' or ends-with(@text, 'Error')]]");
        checkConfig.addMessage("matchxpath.match", "Illegal throws statement");
        final String[] expected = {
            "8:25: Illegal throws statement",
            "10:25: Illegal throws statement",
            "11:25: Illegal throws statement",
        };
        verify(checkConfig, getPath("InputMatchXpathIllegalThrows.java"), expected);
    }

    @Test
    public void testInvalidQuery() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        matchXpathCheck.setQuery("!@#%^");

        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.CLASS_DEF);
        detailAST.setText("Class Def");
        detailAST.setLineNo(0);
        detailAST.setColumnNo(0);

        try {
            matchXpathCheck.beginTree(detailAST);
            fail("Exception was expected");
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

    @Test
    public void testGetDefaultTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertThat("Expected empty array",
                matchXpathCheck.getDefaultTokens(), is(CommonUtil.EMPTY_INT_ARRAY));
    }

    @Test
    public void testGetAcceptableTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertThat("Expected empty array",
                matchXpathCheck.getAcceptableTokens(), is(CommonUtil.EMPTY_INT_ARRAY));
    }

    @Test
    public void testGetRequiredTokens() {
        final MatchXpathCheck matchXpathCheck = new MatchXpathCheck();
        assertThat("Expected empty array",
                matchXpathCheck.getRequiredTokens(), is(CommonUtil.EMPTY_INT_ARRAY));
    }
}
