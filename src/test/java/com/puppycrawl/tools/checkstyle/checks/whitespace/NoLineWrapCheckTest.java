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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NoLineWrapCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testCaseWithoutLineWrapping() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(NoLineWrapCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputNoLineWrapGood.java"), expected);
    }

    @Test
    public void testDefaultTokensLineWrapping() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(NoLineWrapCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY, "package"),
            "6: " + getCheckMessage(MSG_KEY, "import"),
        };
        verify(checkConfig, getPath("InputNoLineWrapBad.java"), expected);
    }

    @Test
    public void testCustomTokensLineWrapping()
        throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(NoLineWrapCheck.class);
        checkConfig.addAttribute("tokens", "IMPORT, CLASS_DEF, METHOD_DEF, ENUM_DEF");
        final String[] expected = {
            "6: " + getCheckMessage(MSG_KEY, "import"),
            "10: " + getCheckMessage(MSG_KEY, "CLASS_DEF"),
            "13: " + getCheckMessage(MSG_KEY, "METHOD_DEF"),
            "20: " + getCheckMessage(MSG_KEY, "ENUM_DEF"),
        };
        verify(checkConfig, getPath("InputNoLineWrapBad.java"), expected);
    }
}
