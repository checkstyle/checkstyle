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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck.MSG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
        assertWithMessage("Default required tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocVariableCheck javadocVariableCheck = new JavadocVariableCheck();

        final int[] actual = javadocVariableCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "311:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "318:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableTags.java"), expected);
    }

    @Test
    public void testAnother()
            throws Exception {
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "30:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInner.java"), expected);
    }

    @Test
    public void testAnother2()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInner2.java"), expected);
    }

    @Test
    public void testAnother3()
            throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "42:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariablePublicOnly.java"), expected);
    }

    @Test
    public void testAnother4()
            throws Exception {
        final String[] expected = {
            "52:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariablePublicOnly2.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "86:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "98:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "99:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "110:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "111:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "112:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "123:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc2.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "86:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "98:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "99:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "110:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "111:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "112:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "123:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc3.java"),
               expected);
    }

    @Test
    public void testIgnoredVariableNames()
            throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "86:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "98:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "99:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "110:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "111:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "112:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc4.java"),
                expected);
    }

    @Test
    public void testDoNotIgnoreAnythingWhenIgnoreNamePatternIsEmpty()
            throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "77:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "86:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "88:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "89:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "98:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "99:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "100:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "101:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "110:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "111:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "112:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "113:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "123:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc5.java"),
                expected);
    }

    @Test
    public void testLambdaLocalVariablesDoNotNeedJavadoc() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadocNeededInLambda.java"),
                expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInterfaceMemberScopeIsPublic.java"),
                expected);
    }

}
