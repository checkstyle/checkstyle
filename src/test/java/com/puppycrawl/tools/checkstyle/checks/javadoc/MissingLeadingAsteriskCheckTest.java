////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingLeadingAsteriskCheck.MSG_MISSING_ASTERISK;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MissingLeadingAsteriskCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingleadingasterisk";
    }

    @Test
    public void testGetAcceptableTokens() {
        final MissingLeadingAsteriskCheck check = new MissingLeadingAsteriskCheck();

        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testGetRequiredTokens() {
        final MissingLeadingAsteriskCheck check = new MissingLeadingAsteriskCheck();

        final int[] actual = check.getRequiredTokens();
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };

        assertArrayEquals(expected, actual, "Required tokens are invalid");
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MissingLeadingAsteriskCheck.class);
        final String[] expected = {
            "5:3: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "20:3: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "27:3: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "34:3: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "48:3: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "56:3: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "62:3: " + getCheckMessage(MSG_MISSING_ASTERISK),
        };
        verify(checkConfig, getPath("InputMissingLeadingAsterisk.java"),
                expected);
    }

}
