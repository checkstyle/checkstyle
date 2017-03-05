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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_PREVIOUS;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
                + "whitespace" + File.separator
                + "operatorwrap" + File.separator
                + filename);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "17:19: " + getCheckMessage(MSG_LINE_NEW, "+"),
            "18:15: " + getCheckMessage(MSG_LINE_NEW, "-"),
            "26:18: " + getCheckMessage(MSG_LINE_NEW, "&&"),
            "48:30: " + getCheckMessage(MSG_LINE_NEW, "&"),
            "61:30: " + getCheckMessage(MSG_LINE_NEW, "&"),
        };
        verify(checkConfig, getPath("InputOperatorWrap.java"), expected);
    }

    @Test
    public void testOpWrapEol()
            throws Exception {
        checkConfig.addAttribute("option", WrapOption.EOL.toString());
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "-"),
            "24:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&&"),
            "29:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&&"),
        };
        verify(checkConfig, getPath("InputOperatorWrap.java"), expected);
    }

    @Test
    public void testNonDefOpsDefault()
            throws Exception {
        checkConfig.addAttribute("tokens", "METHOD_REF");
        final String[] expected = {
            "33:33: " + getCheckMessage(MSG_LINE_NEW, "::"),
        };
        verify(checkConfig, getPath("InputOperatorWrap.java"), expected);
    }

    @Test
    public void testNonDefOpsWrapEol()
            throws Exception {
        checkConfig.addAttribute("tokens", "METHOD_REF");
        checkConfig.addAttribute("option", WrapOption.EOL.toString());
        final String[] expected = {
            "31:21: " + getCheckMessage(MSG_LINE_PREVIOUS, "::"),
            "36:21: " + getCheckMessage(MSG_LINE_PREVIOUS, "::"),
        };
        verify(checkConfig, getPath("InputOperatorWrap.java"), expected);
    }

    @Test
    public void testAssignEol()
            throws Exception {
        checkConfig.addAttribute("tokens", "ASSIGN");
        checkConfig.addAttribute("option", WrapOption.EOL.toString());
        final String[] expected = {
            "42:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
        };
        verify(checkConfig, getPath("InputOperatorWrap.java"), expected);
    }

    @Test
    public void testInvalidOption() throws Exception {
        checkConfig.addAttribute("option", "invalid_option");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputOperatorWrap.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                            + "Cannot set property 'option' to 'invalid_option' in module"));
        }
    }
}
