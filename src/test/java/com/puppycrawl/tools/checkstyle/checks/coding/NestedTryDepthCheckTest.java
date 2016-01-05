////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class NestedTryDepthCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedTryDepthCheck.class);

        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "33:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "34:21: " + getCheckMessage(MSG_KEY, 3, 1),
        };

        verify(checkConfig, getPath("InputNestedTryDepth.java"), expected);
    }

    @Test
    public void testCustomizedDepth() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NestedTryDepthCheck.class);
        checkConfig.addAttribute("max", "2");

        final String[] expected = {
            "34:21: " + getCheckMessage(MSG_KEY, 3, 2),
        };

        verify(checkConfig, getPath("InputNestedTryDepth.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final NestedTryDepthCheck check = new NestedTryDepthCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
