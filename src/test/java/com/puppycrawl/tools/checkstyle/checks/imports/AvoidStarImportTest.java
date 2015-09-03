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

import static com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidStarImportTest
    extends BaseCheckTestSupport {
    @Test
    public void testDefaultOperation()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.imports.*"),
            "9: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "10: " + getCheckMessage(MSG_KEY, "java.lang.*"),
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.*"),
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputAvoidStarImportCheck.java"),
                expected);
    }

    @Test
    public void testExcludes()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("excludes",
            "java.io,java.lang,javax.swing.WindowConstants.*, javax.swing.WindowConstants");
        // allow the java.io/java.lang,javax.swing.WindowConstants star imports
        final String[] expected2 = {
            "7: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.imports.*"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.*"),
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputAvoidStarImportCheck.java"),
                expected2);
    }

    @Test
    public void testAllowClassImports() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("allowClassImports", "true");
        // allow all class star imports
        final String[] expected2 = {
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.*"), };
        verify(checkConfig, getPath("imports" + File.separator
            + "InputAvoidStarImportCheck.java"), expected2);
    }

    @Test
    public void testAllowStaticMemberImports() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("allowStaticMemberImports", "true");
        // allow all static star imports
        final String[] expected2 = {
            "7: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.imports.*"),
            "9: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "10: " + getCheckMessage(MSG_KEY, "java.lang.*"),
        };
        verify(checkConfig, getPath("imports" + File.separator
            + "InputAvoidStarImportCheck.java"), expected2);
    }

    @Test
    public void testGetAcceptableTokens() {
        AvoidStarImportCheck testCheckObject =
                new AvoidStarImportCheck();
        int[] actual = testCheckObject.getAcceptableTokens();
        int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetRequiredTokens() {
        AvoidStarImportCheck testCheckObject =
                new AvoidStarImportCheck();
        int[] actual = testCheckObject.getRequiredTokens();
        int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};

        assertArrayEquals(expected, actual);
    }
}
