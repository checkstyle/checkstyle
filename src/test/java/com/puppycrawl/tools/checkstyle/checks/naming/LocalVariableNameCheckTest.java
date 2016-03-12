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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class LocalVariableNameCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator + filename);
    }

    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "119:13: " + getCheckMessage(MSG_INVALID_PATTERN, "ABC", pattern),
            "130:18: " + getCheckMessage(MSG_INVALID_PATTERN, "I", pattern),
            "132:20: " + getCheckMessage(MSG_INVALID_PATTERN, "InnerBlockVariable", pattern),
            "207:21: " + getCheckMessage(MSG_INVALID_PATTERN, "O", pattern),
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testInnerClass()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testLoopVariables()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(LocalVariableNameCheck.class);
        checkConfig.addAttribute("format", "^[a-z]{2,}[a-zA-Z0-9]*$");
        checkConfig.addAttribute("allowOneCharVarInForLoop", "true");

        final String pattern = "^[a-z]{2,}[a-zA-Z0-9]*$";

        final String[] expected = {
            "19:21: " + getCheckMessage(MSG_INVALID_PATTERN, "i", pattern),
            "25:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Index", pattern),
        };
        verify(checkConfig, getPath("InputOneCharInitVarName.java"), expected);
    }
}
