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
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class PackageNameCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final PackageNameCheck checkObj = new PackageNameCheck();
        final int[] expected = {TokenTypes.PACKAGE_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testSpecified()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(PackageNameCheck.class);
        checkConfig.addAttribute("format", "[A-Z]+");

        final String pattern = "[A-Z]+";

        final String[] expected = {
            "6:9: " + getCheckMessage(MSG_INVALID_PATTERN,
                "com.puppycrawl.tools.checkstyle.checks.naming", pattern),
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(PackageNameCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final PackageNameCheck packageNameCheckObj = new PackageNameCheck();
        final int[] actual = packageNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
        };
        assertArrayEquals(expected, actual);
    }
}
