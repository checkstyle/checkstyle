///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

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

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMutableExceptionClassExtendsGenericClass.java"),
                expected);
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "31:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "54:9: " + getCheckMessage(MSG_KEY, "errorCode"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMutableException.java"), expected);
    }

    @Test
    public void testMultipleInputs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MutableExceptionCheck.class);
        final String filePath1 = getPath("InputMutableException2.java");
        final String filePath2 = getPath("InputMutableExceptionMultipleInputs.java");

        final List<String> expected1 = Arrays.asList(
            "14:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "31:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "54:9: " + getCheckMessage(MSG_KEY, "errorCode"));
        final List<String> expected2 = Arrays.asList(
            "14:9: " + getCheckMessage(MSG_KEY, "errorCode"),
            "18:9: " + getCheckMessage(MSG_KEY, "errorCode"));

        final File[] inputs = {new File(filePath1), new File(filePath2)};

        verify(createChecker(checkConfig), inputs,
                ImmutableMap.of(filePath1, expected1, filePath2, expected2));
    }

    @Test
    public void testFormat() throws Exception {
        final String[] expected = {
            "42:13: " + getCheckMessage(MSG_KEY, "errorCode"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMutableException3.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
        assertWithMessage("Default acceptable tokens are invalid")
                .that(obj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final int[] expected = {TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF};
        assertWithMessage("Default required tokens are invalid")
                .that(obj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testWrongTokenType() {
        final MutableExceptionCheck obj = new MutableExceptionCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.INTERFACE_DEF, "interface"));
        try {
            obj.visitToken(ast);
            assertWithMessage("IllegalStateException is expected")
                    .fail();
        }
        catch (IllegalStateException ex) {
            // exception is expected
            assertWithMessage("Invalid exception message")
                    .that(ex.getMessage())
                    .isEqualTo("interface[0x-1]");
        }
    }

}
