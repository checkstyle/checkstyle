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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck.MSG_DUPLICATE;
import static com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck.MSG_LANG;
import static com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck.MSG_SAME;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class RedundantImportCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testWithChecker()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantImportCheck.class);
        final String[] expected = {
            "7:1: " + getCheckMessage(MSG_SAME, "com.puppycrawl.tools.checkstyle.imports.*"),
            "8:1: " + getCheckMessage(MSG_SAME, "com.puppycrawl.tools.checkstyle.imports.InputImportBug"),
            "10:1: " + getCheckMessage(MSG_LANG, "java.lang.*"),
            "11:1: " + getCheckMessage(MSG_LANG, "java.lang.String"),
            "14:1: " + getCheckMessage(MSG_DUPLICATE, 13, "java.util.List"),
            "26:1: " + getCheckMessage(MSG_DUPLICATE, 25, "javax.swing.WindowConstants.*"),
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputRedundantImportCheck.java"), expected);
    }

    @Test
    public void testUnnamedPackage()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantImportCheck.class);
        final String[] expected = {
            "2:1: " + getCheckMessage(MSG_DUPLICATE, 1, "java.util.List"),
            "4:1: " + getCheckMessage(MSG_LANG, "java.lang.String"),
        };
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputRedundantImportCheck_UnnamedPackage.java").getCanonicalPath(), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        RedundantImportCheck testCheckObject =
                new RedundantImportCheck();
        int[] actual = testCheckObject.getAcceptableTokens();
        int[] expected = new int[]{
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };

        assertArrayEquals(expected, actual);
    }
}
