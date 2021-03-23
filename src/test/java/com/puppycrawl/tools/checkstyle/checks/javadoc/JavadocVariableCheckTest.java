////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

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
        assertArrayEquals(expected, actual, "Default required tokens are invalid");
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocVariableCheck javadocVariableCheck = new JavadocVariableCheck();

        final int[] actual = javadocVariableCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "309:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "316:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "335:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocVariableTags.java"), expected);
    }

    @Test
    public void testAnother()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
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
        verify(checkConfig, getPath("InputJavadocVariableInner2.java"), expected);
    }

    @Test
    public void testAnother3()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
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
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocVariablePublicOnly2.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "57:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "68:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "70:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "80:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "92:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "104:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "106:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "117:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
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
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputJavadocVariableNoJavadoc2.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "57:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "68:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "70:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "80:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "92:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "104:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "106:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "117:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputJavadocVariableNoJavadoc3.java"),
               expected);
    }

    @Test
    public void testIgnoredVariableNames()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("ignoreNamePattern", "log|logger");
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "57:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "68:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "70:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "80:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "92:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "104:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "106:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
                getPath("InputJavadocVariableNoJavadoc4.java"),
                expected);
    }

    @Test
    public void testDoNotIgnoreAnythingWhenIgnoreNamePatternIsEmpty()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocVariableCheck.class);
        checkConfig.addAttribute("ignoreNamePattern", "");
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "57:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "68:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "70:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "80:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "92:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "104:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "106:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "117:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
                getPath("InputJavadocVariableNoJavadoc5.java"),
                expected);
    }

    @Test
    public void testLambdaLocalVariablesDoNotNeedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocVariableCheck.class);
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
                getPath("InputJavadocVariableNoJavadocNeededInLambda.java"),
                expected);
    }

}
