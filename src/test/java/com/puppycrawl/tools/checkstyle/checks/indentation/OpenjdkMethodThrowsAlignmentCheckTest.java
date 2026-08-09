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
import static com.puppycrawl.tools.checkstyle.checks.indentation.OpenjdkMethodThrowsAlignmentCheck.MSG_INDENTATION;
import static com.puppycrawl.tools.checkstyle.checks.indentation.OpenjdkMethodThrowsAlignmentCheck.MSG_LINE_NEW;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OpenjdkMethodThrowsAlignmentCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/"
                + "openjdkmethodthrowsalignment";
    }

    @Test
    public void testGetAcceptableTokens() {
        final OpenjdkMethodThrowsAlignmentCheck check =
                new OpenjdkMethodThrowsAlignmentCheck();
        final int[] expected = {TokenTypes.METHOD_DEF };

        assertWithMessage("Acceptable tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "18:38: " + getCheckMessage(MSG_LINE_NEW),
            "33:13: " + getCheckMessage(MSG_INDENTATION),
            "40:17: " + getCheckMessage(MSG_INDENTATION),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodThrowsAlignmentDefault.java"), expected);
    }

}
