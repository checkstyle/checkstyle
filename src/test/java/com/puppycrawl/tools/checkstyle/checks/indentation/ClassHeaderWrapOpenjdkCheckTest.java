///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.indentation.ClassHeaderWrapOpenjdkCheck.MSG_KEY_NOT_ON_NEW_LINE;
import static com.puppycrawl.tools.checkstyle.checks.indentation.ClassHeaderWrapOpenjdkCheck.MSG_KEY_UNNECESSARY_WRAP;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassHeaderWrapOpenjdkCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/"
                + "classheaderwrapopenjdk";
    }

    @Test
    public void testGetAcceptableTokens() {
        final ClassHeaderWrapOpenjdkCheck check = new ClassHeaderWrapOpenjdkCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Acceptable tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final ClassHeaderWrapOpenjdkCheck check = new ClassHeaderWrapOpenjdkCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Required tokens are invalid")
            .that(check.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "33:35: " + getCheckMessage(MSG_KEY_NOT_ON_NEW_LINE, "implements"),
            "38:56: " + getCheckMessage(MSG_KEY_NOT_ON_NEW_LINE, "implements"),
            "49:60: " + getCheckMessage(MSG_KEY_NOT_ON_NEW_LINE, "implements"),
            "55:5: " + getCheckMessage(MSG_KEY_UNNECESSARY_WRAP),
            "61:36: " + getCheckMessage(MSG_KEY_NOT_ON_NEW_LINE, "extends"),
            "67:5: " + getCheckMessage(MSG_KEY_UNNECESSARY_WRAP),
            "90:5: " + getCheckMessage(MSG_KEY_UNNECESSARY_WRAP),
            "105:5: " + getCheckMessage(MSG_KEY_UNNECESSARY_WRAP),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassHeaderWrapOpenjdk.java"), expected);
    }

    @Test
    public void testCompact() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_UNNECESSARY_WRAP),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputClassHeaderWrapOpenjdkCompact.java"),
                    expected);
    }

}
