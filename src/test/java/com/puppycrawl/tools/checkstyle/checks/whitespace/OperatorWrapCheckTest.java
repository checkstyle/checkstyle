////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_PREVIOUS;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class OperatorWrapCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(OperatorWrapCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testDefault()
        throws Exception {
        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_LINE_NEW, "+"),
            "16:15: " + getCheckMessage(MSG_LINE_NEW, "-"),
            "24:18: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "39:30: " + getCheckMessage(MSG_LINE_NEW, "&"),
            "52:30: " + getCheckMessage(MSG_LINE_NEW, "&"),
        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }

    @Test
    public void testOpWrapEol()
        throws Exception {
        checkConfig.addAttribute("option", WrapOption.EOL.toString());
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "-"),
            "22:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&&"),
            "27:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&&"),
        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }

    @Test
    public void testAssignEol()
        throws Exception {
        checkConfig.addAttribute("tokens", "ASSIGN");
        checkConfig.addAttribute("option", WrapOption.EOL.toString());
        final String[] expected = {
            "33:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
        };
        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }

    @Test(expected = CheckstyleException.class)
    public void testInvalidOption() throws Exception {
        checkConfig.addAttribute("option", "invalid_option");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputOpWrap.java"), expected);
    }
}
