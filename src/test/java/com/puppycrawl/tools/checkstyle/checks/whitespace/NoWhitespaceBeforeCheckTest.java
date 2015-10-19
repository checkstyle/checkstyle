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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NoWhitespaceBeforeCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(NoWhitespaceBeforeCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "30:14: " + getCheckMessage(MSG_KEY, "++"),
            "30:21: " + getCheckMessage(MSG_KEY, "--"),
            "176:18: " + getCheckMessage(MSG_KEY, ";"),
            "178:23: " + getCheckMessage(MSG_KEY, ";"),
            "185:18: " + getCheckMessage(MSG_KEY, ";"),
            "187:27: " + getCheckMessage(MSG_KEY, ";"),
            "195:26: " + getCheckMessage(MSG_KEY, ";"),
            "211:15: " + getCheckMessage(MSG_KEY, ";"),
            "266:1: " + getCheckMessage(MSG_KEY, ";"),
            "270:15: " + getCheckMessage(MSG_KEY, ";"),
            "284:1: " + getCheckMessage(MSG_KEY, ";"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDot() throws Exception {
        checkConfig.addAttribute("tokens", "DOT");
        final String[] expected = {
            "5:12: " + getCheckMessage(MSG_KEY, "."),
            "6:4: " + getCheckMessage(MSG_KEY, "."),
            "129:17: " + getCheckMessage(MSG_KEY, "."),
            "135:12: " + getCheckMessage(MSG_KEY, "."),
            "136:10: " + getCheckMessage(MSG_KEY, "."),
            "264:1: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDotAllowLineBreaks() throws Exception {
        checkConfig.addAttribute("tokens", "DOT");
        checkConfig.addAttribute("allowLineBreaks", "yes");
        final String[] expected = {
            "5:12: " + getCheckMessage(MSG_KEY, "."),
            "129:17: " + getCheckMessage(MSG_KEY, "."),
            "136:10: " + getCheckMessage(MSG_KEY, "."),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }
}
