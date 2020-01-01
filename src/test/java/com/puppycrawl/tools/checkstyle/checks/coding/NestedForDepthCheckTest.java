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

import static com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NestedForDepthCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nestedfordepth";
    }

    /**
     * Call the check allowing 2 layers of nested for-statements. This
     * means the top-level for can contain up to 2 levels of nested for
     * statements. As the test input has 4 layers of for-statements below
     * the top-level for statement, this must cause 2 error-messages.
     *
     * @throws Exception necessary to fulfill JUnit's
     *     interface-requirements for test-methods.
     */
    @Test
    public void testNestedTooDeep() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NestedForDepthCheck.class);
        checkConfig.addAttribute("max", "2");

        final String[] expected = {
            "43:11: " + getCheckMessage(MSG_KEY, 3, 2),
            "44:13: " + getCheckMessage(MSG_KEY, 4, 2),
            "47:13: " + getCheckMessage(MSG_KEY, 4, 2),
        };

        verify(checkConfig, getPath("InputNestedForDepth.java"),
               expected);
    }

    /**
     * Call the check allowing 4 layers of nested for-statements. This
     * means the top-level for can contain up to 4 levels of nested for
     * statements. As the test input has 4 layers of for-statements below
     * the top-level for statement, this must not cause an
     * error-message.
     *
     * @throws Exception necessary to fulfill JUnit's
     *     interface-requirements for test-methods.
     */
    @Test
    public void testNestedOk() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NestedForDepthCheck.class);
        checkConfig.addAttribute("max", "4");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputNestedForDepth.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final NestedForDepthCheck check = new NestedForDepthCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

}
