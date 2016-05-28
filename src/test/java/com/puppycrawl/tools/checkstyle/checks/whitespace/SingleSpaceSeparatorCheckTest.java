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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.SingleSpaceSeparatorCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class SingleSpaceSeparatorCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testNoSpaceErrors() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        verify(checkConfig, getPath("InputSingleSpaceNoErrors.java"),
                CommonUtils.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testSpaceErrors() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        checkConfig.addAttribute("validateComments", String.valueOf(true));
        final String[] expected = {
            "1:9: " + getCheckMessage(MSG_KEY),
            "1:27: " + getCheckMessage(MSG_KEY),
            "4:8: " + getCheckMessage(MSG_KEY),
            "6:18: " + getCheckMessage(MSG_KEY),
            "6:42: " + getCheckMessage(MSG_KEY),
            "7:20: " + getCheckMessage(MSG_KEY),
            "8:11: " + getCheckMessage(MSG_KEY),
            "8:15: " + getCheckMessage(MSG_KEY),
            "11:3: " + getCheckMessage(MSG_KEY),
            "12:5: " + getCheckMessage(MSG_KEY),
            "13:7: " + getCheckMessage(MSG_KEY),
            "14:8: " + getCheckMessage(MSG_KEY),
            "17:13: " + getCheckMessage(MSG_KEY),
            "17:23: " + getCheckMessage(MSG_KEY),
            "17:32: " + getCheckMessage(MSG_KEY),
            "18:15: " + getCheckMessage(MSG_KEY),
            "18:22: " + getCheckMessage(MSG_KEY),
            "19:16: " + getCheckMessage(MSG_KEY),
            "19:23: " + getCheckMessage(MSG_KEY),
            "20:19: " + getCheckMessage(MSG_KEY),
            "21:21: " + getCheckMessage(MSG_KEY),
            "26:21: " + getCheckMessage(MSG_KEY),
            "26:27: " + getCheckMessage(MSG_KEY),
            "27:14: " + getCheckMessage(MSG_KEY),
            "27:23: " + getCheckMessage(MSG_KEY),
            "27:31: " + getCheckMessage(MSG_KEY),
            "27:46: " + getCheckMessage(MSG_KEY),
            "28:14: " + getCheckMessage(MSG_KEY),
            "28:22: " + getCheckMessage(MSG_KEY),
            "30:16: " + getCheckMessage(MSG_KEY),
            "30:33: " + getCheckMessage(MSG_KEY),
            "31:7: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceErrors.java"), expected);
    }

    @Test
    public void testSpaceErrorsAroundComments() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        checkConfig.addAttribute("validateComments", String.valueOf(true));
        final String[] expected = {
            "5:10: " + getCheckMessage(MSG_KEY),
            "5:42: " + getCheckMessage(MSG_KEY),
            "6:13: " + getCheckMessage(MSG_KEY),
            "13:13: " + getCheckMessage(MSG_KEY),
            "13:20: " + getCheckMessage(MSG_KEY),
            "14:7: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceComments.java"), expected);
    }

    @Test
    public void testSpaceErrorsIfCommentsIgnored() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(SingleSpaceSeparatorCheck.class);
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputSingleSpaceComments.java"), expected);
    }
}
