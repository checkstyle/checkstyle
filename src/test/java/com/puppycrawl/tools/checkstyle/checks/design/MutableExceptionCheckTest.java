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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import antlr.CommonHiddenStreamToken;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MutableExceptionCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "design" + File.separator + filename);
    }

    @Test
    public void testClassExtendsGenericClass() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MutableExceptionCheck.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMutableExceptionClassExtendsGenericClass.java"),
            expected);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MutableExceptionCheck.class);

        final String[] expected = {
            "6:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "23:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "46:9: " + getCheckMessage(MSG_KEY, "errorCode"),
        };

        verify(checkConfig, getPath("InputMutableException.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MutableExceptionCheck.class);
        checkConfig.addAttribute("format", "^.*Failure$");
        checkConfig.addAttribute("extendedClassNameFormat", "^.*ThreadDeath$");
        final String[] expected = {
            "34:13: " + getCheckMessage(MSG_KEY, "errorCode"),
        };

        verify(checkConfig, getPath("InputMutableException.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
        assertArrayEquals(expected, obj.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
        assertArrayEquals(expected, obj.getRequiredTokens());
    }

    @Test
    public void testWrongTokenType() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.INTERFACE_DEF, "interface"));
        try {
            obj.visitToken(ast);
            fail();
        }
        catch (IllegalStateException ex) {
            //expected
        }
    }
}
