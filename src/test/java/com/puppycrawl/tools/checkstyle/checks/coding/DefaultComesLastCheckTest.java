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

import static com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck.MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class DefaultComesLastCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/defaultcomeslast";
    }

    @Test
    public void testSkipIfLastAndSharedWithCase() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DefaultComesLastCheck.class);
        checkConfig.addAttribute("skipIfLastAndSharedWithCase", "true");
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "25:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "33:21: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "37:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "57:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "77:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "89:13: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "98:13: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputDefaultComesLastSkipIfLastAndSharedWithCase.java"),
                expected);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DefaultComesLastCheck.class);
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY),
            "32:24: " + getCheckMessage(MSG_KEY),
            "37:13: " + getCheckMessage(MSG_KEY),
            "45:13: " + getCheckMessage(MSG_KEY),
            "53:13: " + getCheckMessage(MSG_KEY),
            "61:21: " + getCheckMessage(MSG_KEY),
            "65:13: " + getCheckMessage(MSG_KEY),
            "69:21: " + getCheckMessage(MSG_KEY),
            "74:13: " + getCheckMessage(MSG_KEY),
            "85:13: " + getCheckMessage(MSG_KEY),
            "96:13: " + getCheckMessage(MSG_KEY),
            "106:13: " + getCheckMessage(MSG_KEY),
            "114:13: " + getCheckMessage(MSG_KEY),
            "125:13: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig,
               getPath("InputDefaultComesLast.java"),
               expected);
    }

    @Test
    public void testDefaultMethodsInJava8()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DefaultComesLastCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputDefaultComesLastDefaultMethodsInInterface.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final DefaultComesLastCheck check = new DefaultComesLastCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

}
