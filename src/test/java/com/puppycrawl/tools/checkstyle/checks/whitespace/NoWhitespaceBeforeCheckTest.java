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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NoWhitespaceBeforeCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespacebefore";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        final String[] expected = {
            "30:15: " + getCheckMessage(MSG_KEY, "++"),
            "30:22: " + getCheckMessage(MSG_KEY, "--"),
            "176:19: " + getCheckMessage(MSG_KEY, ";"),
            "178:24: " + getCheckMessage(MSG_KEY, ";"),
            "185:19: " + getCheckMessage(MSG_KEY, ";"),
            "187:28: " + getCheckMessage(MSG_KEY, ";"),
            "195:27: " + getCheckMessage(MSG_KEY, ";"),
            "211:16: " + getCheckMessage(MSG_KEY, ";"),
            "266:1: " + getCheckMessage(MSG_KEY, ";"),
            "270:16: " + getCheckMessage(MSG_KEY, ";"),
            "284:1: " + getCheckMessage(MSG_KEY, ";"),
            "287:62: " + getCheckMessage(MSG_KEY, "..."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeDefault.java"), expected);
    }

    @Test
    public void testDot() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "5:13: " + getCheckMessage(MSG_KEY, "."),
            "6:5: " + getCheckMessage(MSG_KEY, "."),
            "129:18: " + getCheckMessage(MSG_KEY, "."),
            "135:13: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
            "264:1: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeDot.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addAttribute("tokens", "DOT");
        checkConfig.addAttribute("allowLineBreaks", "yes");
        final String[] expected = {
            "5:13: " + getCheckMessage(MSG_KEY, "."),
            "129:18: " + getCheckMessage(MSG_KEY, "."),
            "136:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeDotAllowLineBreaks.java"), expected);
    }

    @Test
    public void testMethodReference() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_REF");
        final String[] expected = {
            "17:32: " + getCheckMessage(MSG_KEY, "::"),
            "18:61: " + getCheckMessage(MSG_KEY, "::"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeMethodRef.java"), expected);
    }

    @Test
    public void testDotAtTheStartOfTheLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "2:1: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeAtStartOfTheLine.java"), expected);
    }

    @Test
    public void testMethodRefAtTheStartOfTheLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_REF");
        checkConfig.addAttribute("allowLineBreaks", "yes");
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_KEY, "::"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeAtStartOfTheLine.java"), expected);
    }

    @Test
    public void testEmptyForLoop() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addAttribute("tokens", "SEMI");
        checkConfig.addAttribute("allowLineBreaks", "yes");
        final String[] expected = {
            "12:24: " + getCheckMessage(MSG_KEY, ";"),
            "18:32: " + getCheckMessage(MSG_KEY, ";"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeEmptyForLoop.java"), expected);
    }

}
