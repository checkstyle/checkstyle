////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class EmptyLineSeparatorCheckTest
    extends BaseCheckTestSupport
{

    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);

        final String[] expected = {
            "21: 'import' should be separated from previous statement.",
            "35: 'CLASS_DEF' should be separated from previous statement.",
            "38: 'VARIABLE_DEF' should be separated from previous statement.",
            "39: 'STATIC_INIT' should be separated from previous statement.",
            "77: 'INTERFACE_DEF' should be separated from previous statement.",
        };
        verify(checkConfig, getPath("whitespace/InputEmptyLineSeparatorCheck.java"), expected);
    }

    @Test
    public void testAllowNoEmptyLineBetweenFields() throws Exception
    {

        DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowNoEmptyLineBetweenFields", "true");

        final String[] expected = {
            "21: 'import' should be separated from previous statement.",
            "35: 'CLASS_DEF' should be separated from previous statement.",
            "39: 'STATIC_INIT' should be separated from previous statement.",
            "77: 'INTERFACE_DEF' should be separated from previous statement.",
        };
        verify(checkConfig, getPath("whitespace/InputEmptyLineSeparatorCheck.java"), expected);
    }

    @Test
    public void testHeader() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = {
            "19: 'package' should be separated from previous statement.",
        };
        verify(checkConfig, getPath("whitespace/InputEmptyLineSeparatorCheckHeader.java"), expected);
    }

    @Test
    public void testMultipleEmptyLinesBetweenClassMembers() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = {
            "21: 'package' has more than 1 empty lines before.",
            "24: 'import' has more than 1 empty lines before.",
            "33: 'VARIABLE_DEF' has more than 1 empty lines before.",
            "38: 'VARIABLE_DEF' has more than 1 empty lines before.",
            "43: 'METHOD_DEF' has more than 1 empty lines before.",
        };
        verify(checkConfig, getPath("whitespace/InputEmptyLineSeparatorCheckMultipleEmptyLines.java"), expected);
    }

    @Test
    public void testFormerArrayIndexOutOfBounds() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = {

        };
        verify(checkConfig, getPath("whitespace/InputEmptyLineSeparatorFormerException.java"), expected);
    }
}
