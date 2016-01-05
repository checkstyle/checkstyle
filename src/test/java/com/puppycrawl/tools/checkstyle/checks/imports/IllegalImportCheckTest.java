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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.IllegalImportCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class IllegalImportCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "imports" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final IllegalImportCheck checkObj = new  IllegalImportCheck();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testWithSupplied()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalPkgs", "java.io");
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "23:1: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "27:1: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
        };
        verify(checkConfig, getPath("InputIllegalImport.java"), expected);
    }

    @Test
    public void testWithDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalImportCheck.class);
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_KEY, "sun.applet.*"),
            "28:1: " + getCheckMessage(MSG_KEY, "sun.*"),
        };
        verify(checkConfig, getPath("InputIllegalImport.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final IllegalImportCheck testCheckObject =
                new IllegalImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};

        assertArrayEquals(expected, actual);
    }
}
