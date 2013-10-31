////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2013  Oliver Burn
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
import org.junit.Test;

public class FinalParametersCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefaultTokens() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        final String[] expected = {
            "23:26: Parameter s should be final.",
            "38:26: Parameter i should be final.",
            "43:26: Parameter s should be final.",
            "53:17: Parameter s should be final.",
            "69:17: Parameter s should be final.",
            "75:17: Parameter s should be final.",
            "82:21: Parameter s should not be final.",
            "91:45: Parameter e should be final.",
            "94:36: Parameter e should be final.",
            "111:18: Parameter aParam should be final.",
            "114:18: Parameter args should be final.",
            "117:18: Parameter args should be final.",
            "143:41: Parameter aParam should not be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testCtorToken() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "CTOR_DEF");
        final String[] expected = {
            "23:26: Parameter s should be final.",
            "38:26: Parameter i should be final.",
            "43:26: Parameter s should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testMethodToken() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_DEF");
        final String[] expected = {
            "53:17: Parameter s should be final.",
            "69:17: Parameter s should be final.",
            "75:17: Parameter s should be final.",
            "82:21: Parameter s should not be final.",
            "91:45: Parameter e should be final.",
            "94:36: Parameter e should be final.",
            "111:18: Parameter aParam should be final.",
            "114:18: Parameter args should be final.",
            "117:18: Parameter args should be final.",
            "143:41: Parameter aParam should not be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testCatchToken() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "LITERAL_CATCH");
        final String[] expected = {
            "126:16: Parameter npe should be final.",
            "132:16: Parameter e should be final.",
            "135:16: Parameter e should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }

    @Test
    public void testForEachClauseToken() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FinalParametersCheck.class);
        checkConfig.addAttribute("tokens", "FOR_EACH_CLAUSE");
        final String[] expected = {
            "152:13: Parameter s should be final.",
            "160:13: Parameter s should be final.",
        };
        verify(checkConfig, getPath("InputFinalParameters.java"), expected);
    }
}
