////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import org.junit.Test;

public class EmptyBlockCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        final String[] expected = {
            "52:65: Must have at least one statement.",
            "54:41: Must have at least one statement.",
            "71:38: Must have at least one statement.",
            "72:52: Must have at least one statement.",
            "73:45: Must have at least one statement.",
            "75:13: Must have at least one statement.",
            "77:17: Must have at least one statement.",
            "79:13: Must have at least one statement.",
            "82:17: Must have at least one statement.",
            "178:5: Must have at least one statement.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testText()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.TEXT.toString());
        final String[] expected = {
            "52:65: Empty catch block.",
            "72:52: Empty catch block.",
            "73:45: Empty catch block.",
            "75:13: Empty try block.",
            "77:17: Empty finally block.",
            "178:5: Empty INSTANCE_INIT block.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testStatement()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.STMT.toString());
        final String[] expected = {
            "52:65: Must have at least one statement.",
            "54:41: Must have at least one statement.",
            "71:38: Must have at least one statement.",
            "72:52: Must have at least one statement.",
            "73:45: Must have at least one statement.",
            "75:13: Must have at least one statement.",
            "77:17: Must have at least one statement.",
            "79:13: Must have at least one statement.",
            "82:17: Must have at least one statement.",
            "178:5: Must have at least one statement.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void allowEmptyLoops() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.STMT.toString());
        checkConfig.addAttribute("tokens", "LITERAL_TRY, LITERAL_CATCH,"
                + "LITERAL_FINALLY, LITERAL_DO, LITERAL_IF,"
                + "LITERAL_ELSE, INSTANCE_INIT, STATIC_INIT, LITERAL_SWITCH");
        final String[] expected = {
            "16:29: Must have at least one statement.",
            "19:42: Must have at least one statement.",
            "22:29: Must have at least one statement.",
            "23:28: Must have at least one statement.",
        };
        verify(checkConfig, getPath("InputSemantic2.java"), expected);
    }

    @Test
    public void allowEmptyLoopsText() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(EmptyBlockCheck.class);
        checkConfig.addAttribute("option", BlockOption.TEXT.toString());
        checkConfig.addAttribute("tokens", "LITERAL_TRY, LITERAL_CATCH,"
                + "LITERAL_FINALLY, LITERAL_DO, LITERAL_IF,"
                + "LITERAL_ELSE, INSTANCE_INIT, STATIC_INIT, LITERAL_SWITCH");
        final String[] expected = {
            "16:29: Empty if block.",
            "19:42: Empty if block.",
            "22:29: Empty if block.",
            "23:28: Empty switch block.",
        };
        verify(checkConfig, getPath("InputSemantic2.java"), expected);
    }
}
