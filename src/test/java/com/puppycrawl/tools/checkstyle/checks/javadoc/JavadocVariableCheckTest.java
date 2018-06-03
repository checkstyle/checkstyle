////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck.MSG_JAVADOC_MISSING;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocVariableCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocVariableCheck javadocVariableCheck = new JavadocVariableCheck();
        final int[] actual = javadocVariableCheck.getRequiredTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertArrayEquals("Default required tokens are invalid", expected, actual);
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocVariableCheck javadocVariableCheck = new JavadocVariableCheck();

        final int[] actual = javadocVariableCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };

        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "304:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "311:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "330:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocVariableTags.java"), expected);
    }

    @Test
    public void testAnother()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocVariableInner.java"), expected);
    }

    @Test
    public void testAnother2()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocVariableInner.java"), expected);
    }

    @Test
    public void testAnother3()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocVariablePublicOnly.java"), expected);
    }

    @Test
    public void testAnother4()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "46:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocVariablePublicOnly.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "6:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "7:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "8:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "42:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "54:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "78:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "90:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "91:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "102:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "103:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputJavadocVariableNoJavadoc.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "6:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputJavadocVariableNoJavadoc.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "7:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "8:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "42:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "54:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "78:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "90:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "91:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "102:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "103:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputJavadocVariableNoJavadoc.java"),
               expected);
    }

    @Test
    public void testIgnoredVariableNames()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("ignoreNamePattern", "log|logger");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "6:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "7:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "8:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "42:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "54:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "78:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "90:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "91:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "102:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "103:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
                getPath("InputJavadocVariableNoJavadoc.java"),
                expected);
    }

    @Test
    public void testDoNotIgnoreAnythingWhenIgnoreNamePatternIsEmpty()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("ignoreNamePattern", "");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "6:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "7:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "8:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "42:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "54:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "78:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "90:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "91:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "102:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "103:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
                getPath("InputJavadocVariableNoJavadoc.java"),
                expected);
    }

    @Test
    public void testLambdaLocalVariablesDoNotNeedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "6:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
                getPath("InputJavadocVariableNoJavadocNeededInLambda.java"),
                expected);
    }

}
