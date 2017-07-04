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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassTypeParameterNameCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator
                + "classtypeparametername" + File.separator
                + filename);
    }

    @Test
    public void testGetClassRequiredTokens() {
        final ClassTypeParameterNameCheck checkObj =
            new ClassTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testClassDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);

        final String pattern = "^[A-Z]$";

        final String[] expected = {
            "5:42: " + getCheckMessage(MSG_INVALID_PATTERN, "t", pattern),
            "13:14: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
            "27:24: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
        };
        verify(checkConfig, getPath("InputClassTypeParameterName.java"), expected);
    }

    @Test
    public void testClassFooName()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ClassTypeParameterNameCheck.class);
        checkConfig.addAttribute("format", "^foo$");

        final String pattern = "^foo$";

        final String[] expected = {
            "5:42: " + getCheckMessage(MSG_INVALID_PATTERN, "t", pattern),
            "33:18: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
        };
        verify(checkConfig, getPath("InputClassTypeParameterName.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ClassTypeParameterNameCheck typeParameterNameCheckObj =
            new ClassTypeParameterNameCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.TYPE_PARAMETER,
        };
        assertArrayEquals(expected, actual);
    }
}
