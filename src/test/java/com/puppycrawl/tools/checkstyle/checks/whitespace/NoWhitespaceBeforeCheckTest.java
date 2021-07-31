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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

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
            "34:15: " + getCheckMessage(MSG_KEY, "++"),
            "34:22: " + getCheckMessage(MSG_KEY, "--"),
            "180:19: " + getCheckMessage(MSG_KEY, ";"),
            "182:24: " + getCheckMessage(MSG_KEY, ";"),
            "189:19: " + getCheckMessage(MSG_KEY, ";"),
            "191:28: " + getCheckMessage(MSG_KEY, ";"),
            "199:27: " + getCheckMessage(MSG_KEY, ";"),
            "215:16: " + getCheckMessage(MSG_KEY, ";"),
            "270:1: " + getCheckMessage(MSG_KEY, ";"),
            "274:16: " + getCheckMessage(MSG_KEY, ";"),
            "288:1: " + getCheckMessage(MSG_KEY, ";"),
            "291:62: " + getCheckMessage(MSG_KEY, "..."),
            "295:16: " + getCheckMessage(MSG_KEY, ":"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeDefault.java"), expected);
    }

    @Test
    public void testDot() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addProperty("tokens", "DOT");
        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY, "."),
            "10:5: " + getCheckMessage(MSG_KEY, "."),
            "133:18: " + getCheckMessage(MSG_KEY, "."),
            "139:13: " + getCheckMessage(MSG_KEY, "."),
            "140:11: " + getCheckMessage(MSG_KEY, "."),
            "268:1: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeDot.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addProperty("tokens", "DOT");
        checkConfig.addProperty("allowLineBreaks", "yes");
        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY, "."),
            "133:18: " + getCheckMessage(MSG_KEY, "."),
            "140:11: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeDotAllowLineBreaks.java"), expected);
    }

    @Test
    public void testMethodReference() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addProperty("tokens", "METHOD_REF");
        final String[] expected = {
            "25:32: " + getCheckMessage(MSG_KEY, "::"),
            "26:61: " + getCheckMessage(MSG_KEY, "::"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeMethodRef.java"), expected);
    }

    @Test
    public void testDotAtTheStartOfTheLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addProperty("tokens", "DOT");
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeAtStartOfTheLine.java"), expected);
    }

    @Test
    public void testMethodRefAtTheStartOfTheLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addProperty("tokens", "METHOD_REF");
        checkConfig.addProperty("allowLineBreaks", "yes");
        final String[] expected = {
            "22:3: " + getCheckMessage(MSG_KEY, "::"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeAtStartOfTheLine2.java"), expected);
    }

    @Test
    public void testEmptyForLoop() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);
        checkConfig.addProperty("tokens", "SEMI");
        checkConfig.addProperty("allowLineBreaks", "yes");
        final String[] expected = {
            "20:24: " + getCheckMessage(MSG_KEY, ";"),
            "26:32: " + getCheckMessage(MSG_KEY, ";"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeEmptyForLoop.java"), expected);
    }

    @Test
    public void testNoWhitespaceBeforeTextBlocksWithTabIndent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoWhitespaceBeforeCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
            getNonCompilablePath("InputNoWhitespaceBeforeTextBlocksTabIndent.java"), expected);
    }

}
