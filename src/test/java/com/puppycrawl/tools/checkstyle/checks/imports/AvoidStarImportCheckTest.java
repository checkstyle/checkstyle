///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidStarImportCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/avoidstarimport";
    }

    @Test
    public void testDefaultOperation()
            throws Exception {
        final String[] expected = {
            "12:15: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "13:17: " + getCheckMessage(MSG_KEY, "java.lang.*"),
            "28:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "29:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "31:27: " + getCheckMessage(MSG_KEY, "java.io.File.*"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAvoidStarImportDefault.java"),
                expected);
    }

    @Test
    public void testExcludes()
            throws Exception {
        // allow the java.io/java.lang,javax.swing.WindowConstants star imports
        final String[] expected2 = {
            "31:27: " + getCheckMessage(MSG_KEY, "java.io.File.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStarImportExcludes.java"),
                expected2);
    }

    @Test
    public void testAllowClassImports() throws Exception {
        // allow all class star imports
        final String[] expected2 = {
            "28:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "29:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "31:27: " + getCheckMessage(MSG_KEY, "java.io.File.*"), };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStarImportAllowClass.java"), expected2);
    }

    @Test
    public void testAllowStaticMemberImports() throws Exception {
        // allow all static star imports
        final String[] expected2 = {
            "12:15: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "13:17: " + getCheckMessage(MSG_KEY, "java.lang.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStarImportAllowStaticMember.java"), expected2);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidStarImportCheck testCheckObject =
                new AvoidStarImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidStarImportCheck testCheckObject =
                new AvoidStarImportCheck();
        final int[] actual = testCheckObject.getRequiredTokens();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};

        assertWithMessage("Default required tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

}
