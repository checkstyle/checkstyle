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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.NullTestAroundInstanceOfCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NullTestAroundInstanceOfCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nulltestaroundinstanceof";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NullTestAroundInstanceOfCheck.class);

        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_KEY),
            "16:9: " + getCheckMessage(MSG_KEY),
            "22:9: " + getCheckMessage(MSG_KEY),
            "27:9: " + getCheckMessage(MSG_KEY),
            "31:9: " + getCheckMessage(MSG_KEY),
            "37:9: " + getCheckMessage(MSG_KEY),
            "49:9: " + getCheckMessage(MSG_KEY),
            "51:9: " + getCheckMessage(MSG_KEY),
            "108:9: " + getCheckMessage(MSG_KEY),
            "112:9: " + getCheckMessage(MSG_KEY),
            "145:9: " + getCheckMessage(MSG_KEY),
            "149:9: " + getCheckMessage(MSG_KEY),
            "169:9: " + getCheckMessage(MSG_KEY),
            "173:9: " + getCheckMessage(MSG_KEY),
            "179:9: " + getCheckMessage(MSG_KEY),
            "203:9: " + getCheckMessage(MSG_KEY),
            "209:9: " + getCheckMessage(MSG_KEY),
            "238:9: " + getCheckMessage(MSG_KEY),
            "244:9: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
                getPath("InputNullTestAroundInstanceOf.java"),
                expected);
    }

    @Test
    public void testEnhancedInstanceOf() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NullTestAroundInstanceOfCheck.class);

        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY),
            "17:9: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
                getNonCompilablePath("InputNullTestAroundInstanceOf.java"),
                expected);
    }

    @Test
    public void testTokens() {
        final NullTestAroundInstanceOfCheck check =
                new NullTestAroundInstanceOfCheck();
        final int[] expected = {
            TokenTypes.LITERAL_IF,
        };
        assertArrayEquals(expected, check.getAcceptableTokens(),
                "Acceptable required tokens are invalid");
        assertArrayEquals(expected, check.getDefaultTokens(),
                "Default required tokens are invalid");
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getRequiredTokens(),
                "Required required tokens are invalid");
    }
}
