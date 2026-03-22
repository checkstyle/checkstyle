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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.SayHelloCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class SayHelloCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/sayhello";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY),
            "18:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSayHello.java"), expected);
    }

    @Test
    public void testTokens() {
        final SayHelloCheck check = new SayHelloCheck();

        assertWithMessage("getDefaultTokens should return CLASS_DEF")
                .that(check.getDefaultTokens())
                .isEqualTo(new int[]{TokenTypes.CLASS_DEF});

        assertWithMessage("getAcceptableTokens should return CLASS_DEF")
                .that(check.getAcceptableTokens())
                .isEqualTo(new int[]{TokenTypes.CLASS_DEF});

        assertWithMessage("getRequiredTokens should return CLASS_DEF")
                .that(check.getRequiredTokens())
                .isEqualTo(new int[]{TokenTypes.CLASS_DEF});
    }
}
