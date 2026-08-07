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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck.MSG_JAVADOC_MISSING;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtilTest;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocMethodCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadocmethod";
    }

    @Test
    public void testGetAcceptableTokens() {
        final MissingJavadocMethodCheck missingJavadocMethodCheck = new MissingJavadocMethodCheck();

        final int[] actual = missingJavadocMethodCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final MissingJavadocMethodCheck missingJavadocMethodCheck = new MissingJavadocMethodCheck();
        final int[] actual = missingJavadocMethodCheck.getRequiredTokens();
        final int[] expected = CommonUtil.EMPTY_INT_ARRAY;
        assertWithMessage("Required tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void extendAnnotationTest() throws Exception {
        final String[] expected = {
            "44:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "testSetCount_zeroToZero_unsupported"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodExtendAnnotation.java"), expected);
    }

    @Test
    public void newTest() throws Exception {
        final String[] expected = {
            "70:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo7"),
            "82:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo9"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodSmallMethods.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {
        final String[] expected = {
            "32:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "allowed3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodAllowedAnnotations.java"), expected);
    }

    @Test
    public void testTags1() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodTags1.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingJavadocMethodCompactSourceFile.java"), expected);
    }

    @Test
    public void testCompactSourceFilePackageScope() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_JAVADOC_MISSING, "main"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMissingJavadocMethodCompactSourceFilePackageScope.java"),
                expected);
    }

    @Test
    public void testTags2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodTags2.java"), expected);
    }

    @Test
    public void testTags3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodTags3.java"), expected);
    }

    @Test
    public void testTags4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodTags4.java"), expected);
    }

    @Test
    public void testTags5() throws Exception {
        final String[] expected = {
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "someOtherMethod"),
            "44:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "someField"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodTags5.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "30:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "InnerInnerClass"),
            "37:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "method2"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodPublicOnly"),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodPublicOnly"),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodPublicOnly"),
            "73:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodPublicOnly"),
            "78:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "82:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "86:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "90:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodPublicOnly2.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final String[] expected = {
            "65:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodPublicOnly3"),
            "70:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodPublicOnly3"),
            "83:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "87:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodPublicOnly3.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final String[] expected = {
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "ma"),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "mb"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodScopeInnerInterfaces.java"),
                expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "30:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodInterfaceMemberScopeIsPublic.java"),
                expected);
    }

    @Test
    public void testEnumCtorScopeIsPrivate() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "packagePrivateMethod"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodEnumCtorScopeIsPrivate.java"),
                expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerAnonInner() throws Exception {
        final String[] expected = {
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "run"),
            "47:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "mouseClicked"),
            "61:13: " + getCheckMessage(MSG_JAVADOC_MISSING, "mouseClicked"), };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodScopeAnonInner2.java"), expected);
    }

    @Test
    public void testScopesA() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodNoJavadocA.java"), expected);
    }

    @Test
    public void testScopesB() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "60:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "73:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "74:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "75:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "86:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "methodWithTwoStarComment"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodNoJavadocB.java"), expected);
    }

    @Test
    public void testScopes2A() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodNoJavadoc2A.java"), expected);
    }

    @Test
    public void testScopes2B() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodNoJavadoc2B.java"), expected);
    }

    @Test
    public void testExcludeScopeA() throws Exception {
        final String[] expected = {
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "50:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "53:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "62:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "65:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodNoJavadoc3A.java"), expected);
    }

    @Test
    public void testExcludeScopeB() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "37:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "40:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "49:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "51:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "52:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "61:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "63:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "64:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "73:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "75:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "76:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "87:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "methodWithTwoStarComment"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodNoJavadoc3B.java"), expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodMissingJavadocTags.java"), expected);
    }

    @Test
    public void testSetterGetterOff() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber"),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getNumber"),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber1"),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber2"),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getNumber2"),
            "45:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getCost1"),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getCost2"),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getCost3"),
            "61:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "isSomething"),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "firePropertyChanged"),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setObject"),
            "72:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getNext"),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setWithoutAssignment"),
            "80:5: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocMethodSetterGetter"),
            "82:5: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocMethodSetterGetter"),
            "88:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setObject"),
            "90:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getObject"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testSetterGetterOnCheck() throws Exception {
        final String[] expected = {
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber1"),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber2"),
            "41:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getNumber2"),
            "45:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getCost1"),
            "50:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getCost2"),
            "56:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getCost3"),
            "66:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "firePropertyChanged"),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setObject"),
            "72:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getNext"),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setWithoutAssignment"),
            "80:5: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocMethodSetterGetter2"),
            "82:5: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocMethodSetterGetter2"),
            "88:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setObject"),
            "90:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "getObject"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodSetterGetter2.java"), expected);
    }

    @Test
    public void test11684081() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethod_01.java"), expected);
    }

    @Test
    public void test11684082() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethod_02.java"), expected);
    }

    @Test
    public void testSkipCertainMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodIgnoreNameRegex.java"), expected);
    }

    @Test
    public void testNotSkipAnythingWhenSkipRegexDoesNotMatch() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo"),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo88"),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodIgnoreNameRegex2.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodsNotSkipWritten.java"), expected);
    }

    @Test
    public void testJava8ReceiverParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodReceiverParameter.java"), expected);
    }

    @Test
    public void testJavadocInMethod() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo1"),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo2"),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo3"),
            "29:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo4"),
            "31:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo5"),
            "34:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "foo6"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodJavadocInMethod.java"), expected);
    }

    @Test
    public void testConstructor() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodConstructor"),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "InputMissingJavadocMethodConstructor"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodConstructor.java"), expected);
    }

    @Test
    public void testNotPublicInterfaceMethods() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodInterfacePrivateMethod.java"), expected);
    }

    @Test
    public void testPublicMethods() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "annotation"),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "annotationInSignature"),
            "30:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "typeInSignature"),
            "33:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "typeInSignature2"),
            "38:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "main"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodPublicMethods.java"), expected);

    }

    @Test
    public void testMissingJavadocMethodRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber"),
            "27:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "getNumber"),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber1"),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "MySecondRecord"),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "MyThirdRecord"),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "setNumber1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodRecordsAndCtors.java"), expected);
    }

    @Test
    public void testMissingJavadocMethodRecordsAndCompactCtorsMinLineCount() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodRecordsAndCtorsMinLineCount.java"),
            expected);
    }

    @Test
    public void testMinLineCount() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "text2Lines"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethod1.java"),
                expected);
    }

    @Test
    public void testAnnotationField() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method"),
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "value"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodAnnotationField.java"),
                expected);
    }

    @Test
    public void testIsGetterMethod() throws Exception {
        final Path testFile = Path.of(getPath("InputMissingJavadocMethodSetterGetter3.java"));
        final DetailAST notGetterMethod = CheckUtilTest.getNode(testFile, TokenTypes.METHOD_DEF);

        final DetailAST getterMethod = notGetterMethod.getNextSibling().getNextSibling();

        assertWithMessage("Invalid result: AST provided is getter method")
                .that(MissingJavadocMethodCheck.isGetterMethod(getterMethod))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is not getter method")
                .that(MissingJavadocMethodCheck.isGetterMethod(notGetterMethod))
                .isFalse();
    }

    @Test
    public void testIsSetterMethod() throws Exception {
        final Path testFile = Path.of(getPath("InputMissingJavadocMethodSetterGetter3.java"));
        final DetailAST firstClassMethod = CheckUtilTest.getNode(testFile, TokenTypes.METHOD_DEF);

        final DetailAST setterMethod =
            firstClassMethod.getNextSibling().getNextSibling().getNextSibling();
        final DetailAST notSetterMethod = setterMethod.getNextSibling();

        assertWithMessage("Invalid result: AST provided is not setter method")
                .that(MissingJavadocMethodCheck.isSetterMethod(setterMethod))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is not setter method")
                .that(MissingJavadocMethodCheck.isSetterMethod(notSetterMethod))
                .isFalse();
    }

    @Test
    public void testSetterGetterOn() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                    MSG_JAVADOC_MISSING, "setNumber"),
            "24:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                    MSG_JAVADOC_MISSING, "Cost1"),
            "29:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                    MSG_JAVADOC_MISSING, "getCost1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodSetterGetter4.java"), expected);
    }

    @Test
    public void missingJavadoc() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MissingJavadocMethodCheck.class,
                    MSG_JAVADOC_MISSING,
                    "validAssign"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodBasic.java"), expected);
    }

    @Test
    public void testMissingJavadocMethodAboveComments() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_JAVADOC_MISSING,
                    "InputMissingJavadocMethodAboveComments"),
            "36:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "method2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodAboveComments.java"),
                expected);
    }

    @Test
    public void testNewCasesAfterAstMigration() throws Exception {
        final String[] expected = {
            "27:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "annotatedSingleLineMethod"),
            "31:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "annotatedBenchmarkMethod"),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "annotatedMethodWithAnnotationValue"),
            "39:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "map"),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING, "Includes"),
            "68:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "oneLineStaticMethod"),
            "71:5: " + getCheckMessage(MSG_JAVADOC_MISSING, "get"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMissingJavadocMethodNewCasesAfterAstMigration.java"), expected);
    }

}
