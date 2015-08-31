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
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
            "182:9: " + getCheckMessage(MSG_ACCESS),
        };
        verify(checkConfig, getPath("coding/InputDeclarationOrder.java"), expected);
    }

    @Test
    public void testOnlyConstructors() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(DeclarationOrderCheck.class);
        checkConfig.addAttribute("ignoreConstructors", "false");
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
            "182:9: " + getCheckMessage(MSG_ACCESS),
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

    @Test
    public void testParents() {
        DetailAST parent = new DetailAST();
        parent.setType(TokenTypes.STATIC_INIT);
        DetailAST method = new DetailAST();
        method.setType(TokenTypes.METHOD_DEF);
        parent.setFirstChild(method);
        DetailAST ctor = new DetailAST();
        ctor.setType(TokenTypes.CTOR_DEF);
        method.setNextSibling(ctor);

        DeclarationOrderCheck check = new DeclarationOrderCheck();
        check.visitToken(method);
        check.visitToken(ctor);
    }

    @Test
    public void testImproperToken() {
        DetailAST parent = new DetailAST();
        parent.setType(TokenTypes.STATIC_INIT);
        DetailAST array = new DetailAST();
        array.setType(TokenTypes.ARRAY_INIT);
        parent.setFirstChild(array);

        DeclarationOrderCheck check = new DeclarationOrderCheck();
        check.visitToken(array);
    }

}
