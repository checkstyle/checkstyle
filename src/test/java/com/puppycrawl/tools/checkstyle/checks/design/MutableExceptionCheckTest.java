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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import antlr.CommonHiddenStreamToken;
import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MutableExceptionCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/mutableexception";
    }

    @Test
    public void testClassExtendsGenericClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MutableExceptionCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMutableExceptionClassExtendsGenericClass.java"),
            expected);
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MutableExceptionCheck.class);

        final String[] expected = {
            "6:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "23:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "46:9: " + getCheckMessage(MSG_KEY, "errorCode"),
        };

        verify(checkConfig, getPath("InputMutableException.java"), expected);
    }

    @Test
    public void testMultipleInputs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MutableExceptionCheck.class);
        final String filePath1 = getPath("InputMutableException.java");
        final String filePath2 = getPath("InputMutableExceptionMultipleInputs.java");

        final List<String> expected1 = Arrays.asList(
            "6:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "23:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "46:9: " + getCheckMessage(MSG_KEY, "errorCode"));
        final List<String> expected2 = Arrays.asList(
            "6:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "10:9: " + getCheckMessage(MSG_KEY, "errorCode"));

        final File[] inputs = {new File(filePath1), new File(filePath2)};

        verify(createChecker(checkConfig), inputs,
                ImmutableMap.of(filePath1, expected1, filePath2, expected2));
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MutableExceptionCheck.class);
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
        assertArrayEquals(expected, obj.getAcceptableTokens(),
                "Default acceptable tokens are invalid");
    }

    @Test
    public void testGetRequiredTokens() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
        assertArrayEquals(expected, obj.getRequiredTokens(), "Default required tokens are invalid");
    }

    @Test
    public void testWrongTokenType() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.INTERFACE_DEF, "interface"));
        try {
            obj.visitToken(ast);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            // exception is expected
            assertEquals("interface[0x-1]", ex.getMessage(),
                    "Invalid exception message");
        }
    }

}
