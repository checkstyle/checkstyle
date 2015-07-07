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

import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_ACCESS;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_CONSTRUCTOR;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_INSTANCE;
import static com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck.MSG_STATIC;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class DeclarationOrderCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);

        final String[] expected = {
            "8:5: " + getCheckMessage(MSG_ACCESS),
            "13:5: " + getCheckMessage(MSG_ACCESS),
            "18:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "27:5: " + getCheckMessage(MSG_STATIC),
            "27:5: " + getCheckMessage(MSG_ACCESS),
            "34:9: " + getCheckMessage(MSG_ACCESS),
            "45:9: " + getCheckMessage(MSG_STATIC),
            "45:9: " + getCheckMessage(MSG_ACCESS),
            "54:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "80:5: " + getCheckMessage(MSG_INSTANCE),

            "92:9: " + getCheckMessage(MSG_ACCESS),
            "100:9: " + getCheckMessage(MSG_STATIC),
            "100:9: " + getCheckMessage(MSG_ACCESS),
            "106:5: " + getCheckMessage(MSG_ACCESS),
            "111:5: " + getCheckMessage(MSG_ACCESS),
            "116:5: " + getCheckMessage(MSG_ACCESS),
            "119:5: " + getCheckMessage(MSG_ACCESS),
            "125:5: " + getCheckMessage(MSG_STATIC),
            "125:5: " + getCheckMessage(MSG_ACCESS),
            "132:9: " + getCheckMessage(MSG_ACCESS),
            "143:9: " + getCheckMessage(MSG_STATIC),
            "143:9: " + getCheckMessage(MSG_ACCESS),
            "152:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "178:5: " + getCheckMessage(MSG_INSTANCE),
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyConstructors() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "false");
        checkConfig.addAttribute("ignoreMethods", "true");
        checkConfig.addAttribute("ignoreModifiers", "true");

        final String[] expected = {
            "45:9: " + getCheckMessage(MSG_STATIC),
            "54:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "80:5: " + getCheckMessage(MSG_INSTANCE),
            "100:9: " + getCheckMessage(MSG_STATIC),
            "143:9: " + getCheckMessage(MSG_STATIC),
            "152:5: " + getCheckMessage(MSG_CONSTRUCTOR),
            "178:5: " + getCheckMessage(MSG_INSTANCE),
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyModifiers() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "true");
        checkConfig.addAttribute("ignoreMethods", "true");
        checkConfig.addAttribute("ignoreModifiers", "false");

        final String[] expected = {
            "8:5: " + getCheckMessage(MSG_ACCESS),
            "13:5: " + getCheckMessage(MSG_ACCESS),
            "18:5: " + getCheckMessage(MSG_ACCESS),
            "21:5: " + getCheckMessage(MSG_ACCESS),
            "27:5: " + getCheckMessage(MSG_STATIC),
            "27:5: " + getCheckMessage(MSG_ACCESS),
            "34:9: " + getCheckMessage(MSG_ACCESS),
            "45:9: " + getCheckMessage(MSG_STATIC),
            "45:9: " + getCheckMessage(MSG_ACCESS),
            "80:5: " + getCheckMessage(MSG_INSTANCE),

            "92:9: " + getCheckMessage(MSG_ACCESS),
            "100:9: " + getCheckMessage(MSG_STATIC),
            "100:9: " + getCheckMessage(MSG_ACCESS),
            "106:5: " + getCheckMessage(MSG_ACCESS),
            "111:5: " + getCheckMessage(MSG_ACCESS),
            "116:5: " + getCheckMessage(MSG_ACCESS),
            "119:5: " + getCheckMessage(MSG_ACCESS),
            "125:5: " + getCheckMessage(MSG_STATIC),
            "125:5: " + getCheckMessage(MSG_ACCESS),
            "132:9: " + getCheckMessage(MSG_ACCESS),
            "143:9: " + getCheckMessage(MSG_STATIC),
            "143:9: " + getCheckMessage(MSG_ACCESS),
            "178:5: " + getCheckMessage(MSG_INSTANCE),
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        DeclarationOrderCheck check = new DeclarationOrderCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
