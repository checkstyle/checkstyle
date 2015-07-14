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

import static com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck.MSG_KEY;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class DefaultComesLastCheckTest extends BaseCheckTestSupport {
    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DefaultComesLastCheck.class);
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_KEY),
            "31:24: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputDefaultComesLast.java"),
               expected);
    }

    @Test
    public void testDefaultMethodsInJava8()
        throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(DefaultComesLastCheck.class);
        final String[] expected = {
        };
        verify(checkConfig,
                  new File(
                        "src/test/resources-noncompilable/com/puppycrawl/tools/"
                        + "checkstyle/coding/InputDefaultComesLast2.java").getCanonicalPath(),
                  expected);
    }

    @Test
    public void testTokensNotNull() {
        DefaultComesLastCheck check = new DefaultComesLastCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
