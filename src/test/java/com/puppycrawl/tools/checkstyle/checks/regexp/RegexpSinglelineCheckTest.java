////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_MINIMUM;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class RegexpSinglelineCheckTest extends BaseFileSetCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(RegexpSinglelineCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "regexp" + File.separator
                + "regexpsingleline" + File.separator
                + filename);
    }

    @Test
    public void testIt() throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        checkConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
            throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        checkConfig.addAttribute("format", illegal);
        final String message = "Bad line :(";
        checkConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "true");
        checkConfig.addAttribute("maximum", "0");

        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testMinimum() throws Exception {
        final String illegal = "\\r";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("minimum", "500");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REGEXP_MINIMUM, "500", illegal),
        };

        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testSetMessage() throws Exception {
        final String illegal = "\\r";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("minimum", "500");
        checkConfig.addAttribute("message", "someMessage");
        final String[] expected = {
            "0: someMessage",
        };

        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }
}
