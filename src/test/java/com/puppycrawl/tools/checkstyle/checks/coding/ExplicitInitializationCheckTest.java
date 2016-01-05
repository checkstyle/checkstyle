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

import static com.puppycrawl.tools.checkstyle.checks.coding.ExplicitInitializationCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ExplicitInitializationCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExplicitInitializationCheck.class);
        final String[] expected = {
            "4:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "5:20: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "9:18: " + getCheckMessage(MSG_KEY, "y4", 0),
            "10:21: " + getCheckMessage(MSG_KEY, "b1", "false"),
            "14:22: " + getCheckMessage(MSG_KEY, "str1", "null"),
            "14:35: " + getCheckMessage(MSG_KEY, "str3", "null"),
            "15:9: " + getCheckMessage(MSG_KEY, "ar1", "null"),
            "18:11: " + getCheckMessage(MSG_KEY, "f1", 0),
            "19:12: " + getCheckMessage(MSG_KEY, "d1", 0),
            "22:17: " + getCheckMessage(MSG_KEY, "ch1", "\\0"),
            "23:17: " + getCheckMessage(MSG_KEY, "ch2", "\\0"),
            "39:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "40:27: " + getCheckMessage(MSG_KEY, "barArray", "null"),
            "47:21: " + getCheckMessage(MSG_KEY, "x", 0),
            "48:29: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "49:31: " + getCheckMessage(MSG_KEY, "barArray", "null"),
            "52:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "53:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "54:27: " + getCheckMessage(MSG_KEY, "barArray", "null"),
        };
        verify(checkConfig,
               getPath("InputExplicitInit.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final ExplicitInitializationCheck check = new ExplicitInitializationCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
