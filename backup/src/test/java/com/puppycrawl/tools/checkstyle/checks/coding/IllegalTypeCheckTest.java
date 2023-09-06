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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTypeCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegaltype";
    }

    @Test
    public void testValidateAbstractClassNamesSetToTrue() throws Exception {
        final String[] expected = {
            "27:38: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "44:5: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "46:37: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "50:12: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestAbstractClassNamesTrue.java"), expected);
    }

    @Test
    public void testValidateAbstractClassNamesSetToFalse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestAbstractClassNamesFalse.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception {
        final String[] expected = {
            "34:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "35:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "60:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "62:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestDefaults.java"), expected);
    }

    @Test
    public void testDefaultsEmptyStringMemberModifiers() throws Exception {

        final String[] expected = {
            "34:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "35:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "60:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "62:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeEmptyStringMemberModifiers.java"), expected);
    }

    @Test
    public void testIgnoreMethodNames() throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "26:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalType.AbstractClass"),
            "34:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "43:36: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "60:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "62:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestIgnoreMethodNames.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {

        final String[] expected = {
            "34:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "35:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "60:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "62:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestFormat.java"), expected);
    }

    @Test
    public void testLegalAbstractClassNames() throws Exception {

        final String[] expected = {
            "26:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalType.AbstractClass"),
            "34:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "35:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "60:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "62:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestLegalAbstractClassNames.java"), expected);
    }

    @Test
    public void testSameFileNameFalsePositive() throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, "SubCal"),
            "43:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeSameFileNameFalsePositive.java"), expected);
    }

    @Test
    public void testSameFileNameGeneral() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregCal"),
            "29:43: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregCal"),
            "31:23: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregCal"),
            "39:9: " + getCheckMessage(MSG_KEY, "List"),
            "40:9: " + getCheckMessage(MSG_KEY, "java.io.File"),
            "42:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "43:13: " + getCheckMessage(MSG_KEY, "ArrayList"),
            "44:13: " + getCheckMessage(MSG_KEY, "Boolean"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestSameFileNameGeneral.java"), expected);
    }

    @Test
    public void testArrayTypes() throws Exception {
        final String[] expected = {
            "20:12: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "22:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "24:12: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "25:9: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "29:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "30:9: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeArrays.java"), expected);
    }

    @Test
    public void testPlainAndArrayTypes() throws Exception {
        final String[] expected = {
            "20:12: " + getCheckMessage(MSG_KEY, "Boolean"),
            "24:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "26:12: " + getCheckMessage(MSG_KEY, "Boolean"),
            "35:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "36:9: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestPlainAndArraysTypes.java"), expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final String[] expected = {
            "28:16: " + getCheckMessage(MSG_KEY, "Boolean"),
            "29:31: " + getCheckMessage(MSG_KEY, "Boolean"),
            "29:40: " + getCheckMessage(MSG_KEY, "Foo"),
            "32:18: " + getCheckMessage(MSG_KEY, "Boolean"),
            "33:24: " + getCheckMessage(MSG_KEY, "Foo"),
            "33:44: " + getCheckMessage(MSG_KEY, "Boolean"),
            "36:23: " + getCheckMessage(MSG_KEY, "Boolean"),
            "36:42: " + getCheckMessage(MSG_KEY, "Serializable"),
            "38:54: " + getCheckMessage(MSG_KEY, "Boolean"),
            "40:25: " + getCheckMessage(MSG_KEY, "Boolean"),
            "40:60: " + getCheckMessage(MSG_KEY, "Boolean"),
            "42:26: " + getCheckMessage(MSG_KEY, "Foo"),
            "42:30: " + getCheckMessage(MSG_KEY, "Boolean"),
            "46:26: " + getCheckMessage(MSG_KEY, "Foo"),
            "46:38: " + getCheckMessage(MSG_KEY, "Boolean"),
            "55:20: " + getCheckMessage(MSG_KEY, "Boolean"),
            "68:28: " + getCheckMessage(MSG_KEY, "Boolean"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestGenerics.java"), expected);
    }

    @Test
    public void testExtendsImplements() throws Exception {
        final String[] expected = {
            "24:17: " + getCheckMessage(MSG_KEY, "Hashtable"),
            "25:14: " + getCheckMessage(MSG_KEY, "Boolean"),
            "30:23: " + getCheckMessage(MSG_KEY, "Boolean"),
            "32:13: " + getCheckMessage(MSG_KEY, "Serializable"),
            "34:24: " + getCheckMessage(MSG_KEY, "Foo"),
            "35:27: " + getCheckMessage(MSG_KEY, "Boolean"),
            "38:32: " + getCheckMessage(MSG_KEY, "Foo"),
            "39:28: " + getCheckMessage(MSG_KEY, "Boolean"),
            "40:13: " + getCheckMessage(MSG_KEY, "Serializable"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestExtendsImplements.java"), expected);
    }

    @Test
    public void testStarImports() throws Exception {

        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, "List"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestStarImports.java"), expected);
    }

    @Test
    public void testStaticImports() throws Exception {

        final String[] expected = {
            "26:6: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
            "28:31: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestStaticImports.java"), expected);
    }

    @Test
    public void testMemberModifiers() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "25:13: " + getCheckMessage(MSG_KEY, "java.util.AbstractList"),
            "32:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "33:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "39:15: " + getCheckMessage(MSG_KEY, "java.util.AbstractList"),
            "41:25: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "49:15: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeTestMemberModifiers.java"), expected);
    }

    @Test
    public void testPackageClassName() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalTypePackageClassName.java"),
                expected);
    }

    @Test
    public void testClearDataBetweenFiles() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        final String violationFile = getPath("InputIllegalTypeTestClearDataBetweenFiles.java");
        checkConfig.addProperty("illegalClassNames", "java.util.TreeSet");
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "22:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
        };

        verify(createChecker(checkConfig), new File[] {
            new File(violationFile),
            new File(getPath("InputIllegalTypeSimilarClassName.java")),
        }, violationFile, expected);
    }

    @Test
    public void testIllegalTypeEnhancedInstanceof() throws Exception {
        final String[] expected = {
            "28:9: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "31:28: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "35:35: " + getCheckMessage(MSG_KEY, "HashMap"),
            "40:52: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "41:32: " + getCheckMessage(MSG_KEY, "TreeSet"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalTypeTestEnhancedInstanceof.java"),
                expected);
    }

    @Test
    public void testIllegalTypeRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "27:14: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "31:52: " + getCheckMessage(MSG_KEY, "Cloneable"),
            "32:16: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "35:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "39:38: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "40:18: " + getCheckMessage(MSG_KEY, "HashMap"),
            "48:13: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalTypeRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testIllegalTypeNewArrayStructure() throws Exception {

        final String[] expected = {
            "26:13: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeNewArrayStructure.java"),
            expected);
    }

    @Test
    public void testRecordComponentsDefault() throws Exception {
        final String[] expected = {
            "45:9: " + getCheckMessage(MSG_KEY, "HashSet"),
            "53:23: " + getCheckMessage(MSG_KEY, "HashSet"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputIllegalTypeRecordsWithMemberModifiersDefault.java"),
                expected);
    }

    @Test
    public void testRecordComponentsFinal() throws Exception {
        final String[] expected = {
            "45:9: " + getCheckMessage(MSG_KEY, "HashSet"),
            "53:23: " + getCheckMessage(MSG_KEY, "HashSet"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputIllegalTypeRecordsWithMemberModifiersFinal.java"),
                expected);
    }

    @Test
    public void testRecordComponentsPrivateFinal() throws Exception {
        final String[] expected = {
            "45:9: " + getCheckMessage(MSG_KEY, "HashSet"),
            "53:23: " + getCheckMessage(MSG_KEY, "HashSet"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputIllegalTypeRecordsWithMemberModifiersPrivateFinal.java"),
                expected);
    }

    @Test
    public void testRecordComponentsPublicProtectedStatic() throws Exception {
        final String[] expected = {
            "45:9: " + getCheckMessage(MSG_KEY, "HashSet")};

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputIllegalTypeRecordsWithMemberModifiersPublicProtectedStatic.java"),
                expected);
    }

    @Test
    public void testTrailingWhitespaceInConfig() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeWhitespaceInConfig.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalTypeCheck check = new IllegalTypeCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testImproperToken() {
        final IllegalTypeCheck check = new IllegalTypeCheck();

        final DetailAstImpl classDefAst = new DetailAstImpl();
        classDefAst.setType(TokenTypes.DOT);

        try {
            check.visitToken(classDefAst);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

    /**
     * Tries to reproduce system failure to call Check on not acceptable token.
     * It can not be reproduced by Input files. Maintainers thinks that keeping
     * exception on unknown token is beneficial.
     *
     */
    @Test
    public void testImproperLeaveToken() {
        final IllegalTypeCheck check = new IllegalTypeCheck();
        final DetailAstImpl enumAst = new DetailAstImpl();
        enumAst.setType(TokenTypes.ENUM_DEF);
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> check.visitToken(enumAst), "IllegalStateException was expected");

        assertWithMessage("Message doesn't contain ast")
                .that(exception.getMessage())
                .isEqualTo(enumAst.toString());
    }

    @Test
    public void testIllegalTypeAbstractClassNameFormat() throws Exception {
        final String[] expected = {
            "15:20: " + getCheckMessage(MSG_KEY, "Gitter"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalTypeAbstractClassNameFormat.java"),
                expected);
    }
}
