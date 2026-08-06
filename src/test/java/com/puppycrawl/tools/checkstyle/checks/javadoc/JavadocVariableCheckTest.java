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
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "mMissingJavadoc"),
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
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT_A"),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT_C"),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "A_CONSTANT"),
            "77:38: " + getCheckMessage(MSG_JAVADOC_MISSING, "INVALID"),
            "77:47: " + getCheckMessage(MSG_JAVADOC_MISSING, "CHECKED"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableTagsEnums.java"), expected);
    }

    @Test
    public void testAnother()
            throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "fData"),
            "28:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "data"),
            "34:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "rData"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInner.java"), expected);
    }

    @Test
    public void testAnother2()
            throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "data"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInner2.java"), expected);
    }

    @Test
    public void testAnother3()
            throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONST"),
            "20:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "mData"),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "mDiff"),
            "47:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "mSize"),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "mLen"),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "mDeer"),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "aFreddo"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariablePublicOnly.java"), expected);
    }

    @Test
    public void testAnother4()
            throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONST"),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "aFreddo"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariablePublicOnly2.java"), expected);
    }

    @Test
    public void testJavadocVariableOnInnerClassFields() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnInnerClassFields.java"),
               expected);
    }

    @Test
    public void testJavadocVariableOnPublicInnerClassFields() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "59:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "60:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "logger"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnPublicInnerClassFields.java"),
               expected);
    }

    @Test
    public void testAccessModifiersPublicProtectedPublic() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc2Public.java"), expected);
    }

    @Test
    public void testAccessModifiersPublicProtectedPackage() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "60:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "CONSTANT"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc2Package.java"), expected);
    }

    @Test
    public void testAccessModifiersPackagePrivatePublic() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc3Public.java"), expected);
    }

    @Test
    public void testAccessModifiersPackagePrivatePackage() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "73:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "logger"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc3Package.java"), expected);
    }

    @Test
    public void testIgnoredVariableNames()
            throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnIgnoredVariableNames.java"),
                expected);
    }

    @Test
    public void testIgnoredVariableNames2()
            throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "15:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "16:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "17:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "25:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "26:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "27:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "28:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "37:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "38:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "39:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "40:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "49:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "50:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "51:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "52:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "61:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "62:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "63:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "64:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableOnIgnoredVariableNames2.java"),
                expected);
    }

    @Test
    public void testDoNotIgnoreAnythingWhenIgnoreNamePatternIsEmptyPublic() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc5Public.java"), expected);
    }

    @Test
    public void testDoNotIgnoreAnythingWhenIgnoreNamePatternIsEmptyPackage() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "60:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i1"),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i2"),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i3"),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "i4"),
            "73:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "logger"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadoc5Package.java"), expected);
    }

    @Test
    public void testLambdaLocalVariablesDoNotNeedJavadoc() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "FUNCTION1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableNoJavadocNeededInLambda.java"),
                expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "field2"),
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "field3"),
            "25:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "B"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocVariableInterfaceMemberScopeIsPublic.java"),
                expected);
    }

    @Test
    public void testMethodInnerClass() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "variablePublic"),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "variableProtected"),
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "variablePackage"),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "variablePrivate"),
        };
        verifyWithInlineConfigParser(
            getPath("InputJavadocVariableMethodInnerClass.java"),
            expected);
    }

    @Test
    public void testJavadocVariableAboveComment() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "x"),
        };
        verifyWithInlineConfigParser(
            getPath("InputJavadocVariableAboveComment.java"),
            expected);
    }

    @Test
    public void testJavadocVariableOddCases() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "field22"),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "INVALID"),
            "21:18: " + getCheckMessage(MSG_JAVADOC_MISSING, "CHECKED"),
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "pinned"),
        };
        verifyWithInlineConfigParser(
            getPath("InputJavadocVariableOddCases.java"),
            expected);
    }

}
