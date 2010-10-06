////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class DescendantTokenCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testMaximumNumber()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        final String[] expected = {
            "20:12: Count of 1 for 'LITERAL_NATIVE' descendant 'LITERAL_NATIVE' exceeds maximum count 0.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testMessage()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumMessage", "Using ''native'' is not allowed.");
        final String[] expected = {
            "20:12: Using 'native' is not allowed.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testMinimumNumber()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("minimumNumber", "2");
        final String[] expected = {
            "11:9: Count of 1 for 'LITERAL_SWITCH' descendant 'LITERAL_DEFAULT' is less than minimum count 2.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testMinimumDepth()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("minimumDepth", "3");
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testMaximumDepth()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "1");
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testEmptyStatements()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "EMPTY_STAT");
        checkConfig.addAttribute("limitedTokens", "EMPTY_STAT");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "0");
        checkConfig.addAttribute("maximumMessage", "Empty statement.");

        final String[] expected = {
            "12:7: Empty statement.",
            "17:7: Empty statement.",
            "22:19: Empty statement.",
            "26:10: Empty statement.",
            "29:16: Empty statement.",
            "33:10: Empty statement.",
            "43:10: Empty statement.",
            "49:13: Empty statement.",
            "51:13: Empty statement.",
            "54:19: Empty statement.",
            "58:10: Empty statement.",
            "61:9: Empty statement.",
            "66:10: Empty statement.",
            "72:10: Empty statement.",
            "76:10: Empty statement.",
            "80:10: Empty statement.",
        };

        verify(checkConfig, getPath("InputEmptyStatement.java"), expected);
    }

    @Test
    public void testMissingSwitchDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addAttribute("minimumNumber", "1");
        checkConfig.addAttribute("maximumDepth", "2");
        checkConfig.addAttribute("minimumMessage", "switch without \"default\" clause.");

        final String[] expected = {
            "15:9: switch without \"default\" clause.",
        };

        verify(checkConfig, getPath("InputMissingSwitchDefault.java"), expected);
    }

    @Test
    public void testStringLiteralEquality() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "EQUAL,NOT_EQUAL");
        checkConfig.addAttribute("limitedTokens", "STRING_LITERAL");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "1");
        checkConfig.addAttribute("maximumMessage", "Literal Strings should be compared using equals(), not ''==''.");

        final String[] expected = {
            "11:18: Literal Strings should be compared using equals(), not '=='.",
            "16:20: Literal Strings should be compared using equals(), not '=='.",
            "21:22: Literal Strings should be compared using equals(), not '=='.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputStringLiteralEquality.java"), expected);
    }

    @Test
    public void testIllegalTokenDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_SWITCH, POST_INC, POST_DEC");
        checkConfig.addAttribute("limitedTokens", "LITERAL_SWITCH, POST_INC, POST_DEC");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "0");
        checkConfig.addAttribute("maximumMessage", "Using ''{2}'' is not allowed.");

        final String[] expected = {
            "11:9: Using 'LITERAL_SWITCH' is not allowed.",
            "14:18: Using 'POST_DEC' is not allowed.",
            "15:18: Using 'POST_INC' is not allowed.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testIllegalTokenNative() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumDepth", "0");
        checkConfig.addAttribute("maximumMessage", "Using ''{2}'' is not allowed.");

        final String[] expected = {
            "20:12: Using 'LITERAL_NATIVE' is not allowed.",
        };
        verify(checkConfig, getPath("InputIllegalTokens.java"), expected);
    }

    @Test
    public void testReturnFromCatch() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(DescendantTokenCheck.class);

        checkConfig.addAttribute("tokens", "LITERAL_CATCH");
        checkConfig.addAttribute("limitedTokens", "LITERAL_RETURN");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumMessage", "Return from catch is not allowed.");

        String[] expected = {
            "7:11: Return from catch is not allowed.",
            "15:11: Return from catch is not allowed.",
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputReturnFromCatchCheck.java"),
               expected);
    }

    @Test
    public void testReturnFromFinally() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(DescendantTokenCheck.class);

        checkConfig.addAttribute("tokens", "LITERAL_FINALLY");
        checkConfig.addAttribute("limitedTokens", "LITERAL_RETURN");
        checkConfig.addAttribute("maximumNumber", "0");
        checkConfig.addAttribute("maximumMessage", "Return from finally is not allowed.");

        String[] expected = {
            "7:11: Return from finally is not allowed.",
            "15:11: Return from finally is not allowed.",
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputReturnFromFinallyCheck.java"),
               expected);
    }

    @Test
    public void testNoSum() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(DescendantTokenCheck.class);

        checkConfig.addAttribute("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addAttribute("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addAttribute("maximumNumber", "1");
        checkConfig.addAttribute("maximumMessage", "What are you doing?");

        String[] expected = {
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputReturnFromFinallyCheck.java"),
               expected);
    }

    @Test
    public void testWithSumCustomMsg() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addAttribute("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addAttribute("maximumNumber", "1");
        checkConfig.addAttribute("maximumDepth", "1");
        checkConfig.addAttribute("maximumMessage", "this cannot be null.");
        checkConfig.addAttribute("sumTokenCounts", "true");

        String[] expected = {
            "22:32: this cannot be null.",
            "22:50: this cannot be null.",
            "23:33: this cannot be null.",
            "23:51: this cannot be null.",
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputReturnFromFinallyCheck.java"),
               expected);
    }

    @Test
    public void testWithSumDefaultMsg() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(DescendantTokenCheck.class);
        checkConfig.addAttribute("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addAttribute("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addAttribute("maximumNumber", "1");
        checkConfig.addAttribute("maximumDepth", "1");
        checkConfig.addAttribute("sumTokenCounts", "true");

        String[] expected = {
            "22:32: Total count of 2 exceeds maximum count 1 under 'EQUAL'.",
            "22:50: Total count of 2 exceeds maximum count 1 under 'EQUAL'.",
            "23:33: Total count of 2 exceeds maximum count 1 under 'NOT_EQUAL'.",
            "23:51: Total count of 2 exceeds maximum count 1 under 'NOT_EQUAL'.",
        };

        verify(checkConfig,
               getPath("coding" + File.separator + "InputReturnFromFinallyCheck.java"),
               expected);
    }
}
