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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck.MSG_KEY;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

/**
 * The unit-test for the <code>NestedForDepthCheck</code>-checkstyle enhancement.
 * @see com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck
 */
public class NestedForDepthCheckTest extends BaseCheckTestSupport {
    /**
     * Call the check allowing 2 layers of nested for-statements. This
     * means the top-level for can contain up to 2 levels of nested for
     * statements. As the testinput has 4 layers of for-statements below
     * the top-level for statement, this must cause 2 error-messages.
     *
     * @throws Exception necessary to fulfill JUnit's
     * interface-requirements for test-methods
     */
    @Test
    public void testNestedTooDeep() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedForDepthCheck.class);
        checkConfig.addAttribute("max", "2");

        final String[] expected = {
            "43:11: " + getCheckMessage(MSG_KEY, 3, 2),
            "44:13: " + getCheckMessage(MSG_KEY, 4, 2),
        };

        verify(checkConfig, getPath("coding/InputNestedForDepth.java"),
               expected);
    }

    /**
     * Call the check allowing 4 layers of nested for-statements. This
     * means the top-level for can contain up to 4 levels of nested for
     * statements. As the testinput has 4 layers of for-statements below
     * the top-level for statement, this must not cause an
     * error-message.
     *
     * @throws Exception necessary to fulfill JUnit's
     * interface-requirements for test-methods
     */
    @Test
    public void testNestedOk() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedForDepthCheck.class);
        checkConfig.addAttribute("max", "4");

        final String[] expected = {
        };

        verify(checkConfig, getPath("coding/InputNestedForDepth.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        NestedForDepthCheck check = new NestedForDepthCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
