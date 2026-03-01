///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
    public String getPackageLocation() {
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
    public void testMethods1() throws
            Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableTagsMethods1.java"), expected);
    }

    @Test
    public void testMethods2()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableTagsMethods2.java"), expected);
    }

    @Test
    public void testMethods3()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableTagsMethods3.java"), expected);
    }

    @Test
    public void testEnums()
            throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableTagsEnums.java"), expected);
    }

    @Test
    public void testAnother()
            throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInner.java"), expected);
    }

    @Test
    public void testAnother2()
            throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInner2.java"), expected);
    }

    @Test
    public void testAnother3()
            throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariablePublicOnly.java"), expected);
    }

    @Test
    public void testAnother4()
            throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariablePublicOnly2.java"), expected);
    }

    @Test
    public void testJavadocVariableOnInnerClassFields() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnInnerClassFields.java"),
               expected);
    }

    @Test
    public void testJavadocVariableOnPublicInnerClassFields() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnPublicInnerClassFields.java"),
               expected);
    }

    @Test
    public void testAccessModifiersPublicProtectedPublic() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc2Public.java"), expected);
    }

    @Test
    public void testAccessModifiersPublicProtectedPackage() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc2Package.java"), expected);
    }

    @Test
    public void testAccessModifiersPackagePrivatePublic() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc3Public.java"), expected);
    }

    @Test
    public void testAccessModifiersPackagePrivatePackage() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "73:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc3Package.java"), expected);
    }

    @Test
    public void testIgnoredVariableNames()
            throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnIgnoredVariableNames.java"),
                expected);
    }

    @Test
    public void testIgnoredVariableNames2()
            throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnIgnoredVariableNames2.java"),
                expected);
    }

    @Test
    public void testDoNotIgnoreAnythingWhenIgnoreNamePatternIsEmptyPublic() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc5Public.java"), expected);
    }

    @Test
    public void testDoNotIgnoreAnythingWhenIgnoreNamePatternIsEmptyPackage() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "73:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc5Package.java"), expected);
    }

    @Test
    public void testLambdaLocalVariablesDoNotNeedJavadoc() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadocNeededInLambda.java"),
                expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInterfaceMemberScopeIsPublic.java"),
                expected);
    }

    @Test
    public void testMethodInnerClass() throws Exception {
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputJavadocVariableMethodInnerClass.java"),
            expected);
    }

    @Test
    public void testJavadocVariableAboveComment() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
            getPath("InputJavadocVariableAboveComment.java"),
            expected);
    }
}
