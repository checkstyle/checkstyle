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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

/**
 * Test fixture for the UnnecessaryParenthesesCheck.
 *
 * @author  Eric K. Roe
 */
public class UnnecessaryParenthesesCheckTest extends BaseCheckTestSupport
{
    private static final String TEST_FILE = "coding" + File.separator
        + "InputUnnecessaryParentheses.java";

    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(UnnecessaryParenthesesCheck.class);

        final String[] expected = {
            "5:22: Unnecessary parentheses around assignment right-hand side.",
            "5:29: Unnecessary parentheses around expression.",
            "5:31: Unnecessary parentheses around identifier 'i'.",
            "5:46: Unnecessary parentheses around assignment right-hand side.",
            "6:15: Unnecessary parentheses around assignment right-hand side.",
            "7:14: Unnecessary parentheses around identifier 'x'.",
            "7:17: Unnecessary parentheses around assignment right-hand side.",
            "8:15: Unnecessary parentheses around assignment right-hand side.",
            "9:14: Unnecessary parentheses around identifier 'x'.",
            "9:17: Unnecessary parentheses around assignment right-hand side.",
            "12:22: Unnecessary parentheses around assignment right-hand side.",
            "12:30: Unnecessary parentheses around identifier 'i'.",
            "12:46: Unnecessary parentheses around assignment right-hand side.",
            "16:17: Unnecessary parentheses around literal '0'.",
            "26:11: Unnecessary parentheses around assignment right-hand side.",
            "30:11: Unnecessary parentheses around assignment right-hand side.",
            "32:11: Unnecessary parentheses around assignment right-hand side.",
            "34:11: Unnecessary parentheses around assignment right-hand side.",
            "35:16: Unnecessary parentheses around identifier 'a'.",
            "36:14: Unnecessary parentheses around identifier 'a'.",
            "36:20: Unnecessary parentheses around identifier 'b'.",
            "36:26: Unnecessary parentheses around literal '600'.",
            "36:40: Unnecessary parentheses around literal '12.5f'.",
            "36:56: Unnecessary parentheses around identifier 'arg2'.",
            "37:14: Unnecessary parentheses around string \"this\".",
            "37:25: Unnecessary parentheses around string \"that\".",
            "38:11: Unnecessary parentheses around assignment right-hand side.",
            "38:14: Unnecessary parentheses around string \"this is a really, really...\".",
            "40:16: Unnecessary parentheses around return value.",
            "44:21: Unnecessary parentheses around literal '1'.",
            "44:26: Unnecessary parentheses around literal '13.5'.",
            "45:22: Unnecessary parentheses around literal 'true'.",
            "46:17: Unnecessary parentheses around identifier 'b'.",
            "50:17: Unnecessary parentheses around assignment right-hand side.",
            "52:11: Unnecessary parentheses around assignment right-hand side.",
            "54:16: Unnecessary parentheses around return value.",
            "64:13: Unnecessary parentheses around expression.",
            "68:16: Unnecessary parentheses around expression.",
            "73:19: Unnecessary parentheses around expression.",
            "74:23: Unnecessary parentheses around literal '4000'.",
            "79:19: Unnecessary parentheses around assignment right-hand side.",
            "81:11: Unnecessary parentheses around assignment right-hand side.",
            "81:16: Unnecessary parentheses around literal '3'.",
            "82:27: Unnecessary parentheses around assignment right-hand side.",
        };

        verify(checkConfig, getPath(TEST_FILE), expected);
    }

    @Test
    public void test15Extensions() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnnecessaryParenthesesCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }
}
