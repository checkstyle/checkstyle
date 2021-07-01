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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalTypeCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("validateAbstractClassNames", "true");
        final String[] expected = {
            "14:38: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "31:5: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "33:37: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "37:12: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestAbstractClassNamesTrue.java"), expected);
    }

    @Test
    public void testValidateAbstractClassNamesSetToFalse() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("validateAbstractClassNames", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputIllegalTypeTestAbstractClassNamesFalse.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "21:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "46:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "48:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestDefaults.java"), expected);
    }

    @Test
    public void testDefaultsEmptyStringMemberModifiers() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("memberModifiers", "");

        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "22:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "47:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "49:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalTypeEmptyStringMemberModifiers.java"), expected);
    }

    @Test
    public void testIgnoreMethodNames() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("ignoredMethodNames", "table2");
        checkConfig.addProperty("validateAbstractClassNames", "true");
        final String[] expected = {
            "11:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "14:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalType.AbstractClass"),
            "22:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "31:36: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "48:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "50:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestIgnoreMethodNames.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalAbstractClassNameFormat", "^$");

        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "22:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "47:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "49:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestFormat.java"), expected);
    }

    @Test
    public void testLegalAbstractClassNames() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("validateAbstractClassNames", "true");
        checkConfig.addProperty("legalAbstractClassNames", "AbstractClass");

        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalType.AbstractClass"),
            "22:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "23:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "48:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "50:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestLegalAbstractClassNames.java"), expected);
    }

    @Test
    public void testSameFileNameFalsePositive() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames",
                "java.util.GregorianCalendar, SubCal, java.util.List");

        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY, "SubCal"),
            "32:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };

        verify(checkConfig, getPath("InputIllegalTypeSameFileNameFalsePositive.java"), expected);
    }

    @Test
    public void testSameFileNameGeneral() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames",
            "List, InputIllegalTypeGregCal, java.io.File, ArrayList, Boolean");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregCal"),
            "18:43: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregCal"),
            "20:23: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregCal"),
            "28:9: " + getCheckMessage(MSG_KEY, "List"),
            "29:9: " + getCheckMessage(MSG_KEY, "java.io.File"),
            "31:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "32:13: " + getCheckMessage(MSG_KEY, "ArrayList"),
            "33:13: " + getCheckMessage(MSG_KEY, "Boolean"),
        };
        verify(checkConfig, getPath("InputIllegalTypeTestSameFileNameGeneral.java"), expected);
    }

    @Test
    public void testArrayTypes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames", "Boolean[], Boolean[][]");
        final String[] expected = {
            "9:12: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "11:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "13:12: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "14:9: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "18:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "19:9: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
        };
        verify(checkConfig, getPath("InputIllegalTypeArrays.java"), expected);
    }

    @Test
    public void testPlainAndArrayTypes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames", "Boolean, Boolean[][]");
        final String[] expected = {
            "9:12: " + getCheckMessage(MSG_KEY, "Boolean"),
            "13:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "15:12: " + getCheckMessage(MSG_KEY, "Boolean"),
            "24:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "25:9: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
        };
        verify(checkConfig, getPath("InputIllegalTypeTestPlainAndArraysTypes.java"), expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames",
                "Boolean, Foo, Serializable");
        checkConfig.addProperty("memberModifiers", "LITERAL_PUBLIC, FINAL");
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_KEY, "Boolean"),
            "19:31: " + getCheckMessage(MSG_KEY, "Boolean"),
            "19:40: " + getCheckMessage(MSG_KEY, "Foo"),
            "22:18: " + getCheckMessage(MSG_KEY, "Boolean"),
            "23:24: " + getCheckMessage(MSG_KEY, "Foo"),
            "23:44: " + getCheckMessage(MSG_KEY, "Boolean"),
            "26:23: " + getCheckMessage(MSG_KEY, "Boolean"),
            "26:42: " + getCheckMessage(MSG_KEY, "Serializable"),
            "28:54: " + getCheckMessage(MSG_KEY, "Boolean"),
            "30:25: " + getCheckMessage(MSG_KEY, "Boolean"),
            "30:60: " + getCheckMessage(MSG_KEY, "Boolean"),
            "32:26: " + getCheckMessage(MSG_KEY, "Foo"),
            "32:30: " + getCheckMessage(MSG_KEY, "Boolean"),
            "36:26: " + getCheckMessage(MSG_KEY, "Foo"),
            "36:38: " + getCheckMessage(MSG_KEY, "Boolean"),
            "45:20: " + getCheckMessage(MSG_KEY, "Boolean"),
            "58:28: " + getCheckMessage(MSG_KEY, "Boolean"),
        };
        verify(checkConfig, getPath("InputIllegalTypeTestGenerics.java"), expected);
    }

    @Test
    public void testExtendsImplements() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames",
                "Boolean, Foo, Hashtable, Serializable");
        checkConfig.addProperty("memberModifiers", "LITERAL_PUBLIC");
        final String[] expected = {
            "14:17: " + getCheckMessage(MSG_KEY, "Hashtable"),
            "15:14: " + getCheckMessage(MSG_KEY, "Boolean"),
            "20:23: " + getCheckMessage(MSG_KEY, "Boolean"),
            "22:13: " + getCheckMessage(MSG_KEY, "Serializable"),
            "24:24: " + getCheckMessage(MSG_KEY, "Foo"),
            "25:27: " + getCheckMessage(MSG_KEY, "Boolean"),
            "28:32: " + getCheckMessage(MSG_KEY, "Foo"),
            "29:28: " + getCheckMessage(MSG_KEY, "Boolean"),
            "30:13: " + getCheckMessage(MSG_KEY, "Serializable"),
        };
        verify(checkConfig, getPath("InputIllegalTypeTestExtendsImplements.java"), expected);
    }

    @Test
    public void testStarImports() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames", "List");

        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, "List"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestStarImports.java"), expected);
    }

    @Test
    public void testStaticImports() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames", "SomeStaticClass");
        checkConfig.addProperty("ignoredMethodNames", "foo1");

        final String[] expected = {
            "18:6: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
            "20:31: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestStaticImports.java"), expected);
    }

    @Test
    public void testMemberModifiers() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("validateAbstractClassNames", "true");
        checkConfig.addProperty("memberModifiers", "LITERAL_PRIVATE, LITERAL_PROTECTED,"
                + " LITERAL_STATIC");
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "13:13: " + getCheckMessage(MSG_KEY, "java.util.AbstractList"),
            "20:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "21:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "27:15: " + getCheckMessage(MSG_KEY, "java.util.AbstractList"),
            "29:25: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "37:15: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeTestMemberModifiers.java"), expected);
    }

    @Test
    public void testPackageClassName() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames", "com.PackageClass");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("InputIllegalTypePackageClassName.java"),
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
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "17:28: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "21:35: " + getCheckMessage(MSG_KEY, "HashMap"),
            "26:52: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "27:32: " + getCheckMessage(MSG_KEY, "TreeSet"),
        };

        verify(checkConfig,
                getNonCompilablePath("InputIllegalTypeTestEnhancedInstanceof.java"),
                expected);
    }

    @Test
    public void testIllegalTypeRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames", "HashMap, HashSet, LinkedHashMap,"
            + " LinkedHashSet, TreeMap, TreeSet, java.util.HashMap, java.util.HashSet,"
            + " java.util.LinkedHashMap, java.util.LinkedHashSet, java.util.TreeMap,"
            + " java.util.TreeSet, Cloneable");

        final String[] expected = {
            "16:14: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "20:52: " + getCheckMessage(MSG_KEY, "Cloneable"),
            "21:16: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            "24:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "28:38: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "29:18: " + getCheckMessage(MSG_KEY, "HashMap"),
            "37:13: " + getCheckMessage(MSG_KEY, "LinkedHashMap"),
            };

        verify(checkConfig,
            getNonCompilablePath("InputIllegalTypeRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testIllegalTypeNewArrayStructure() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addProperty("illegalClassNames", "HashMap");

        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig,
            getPath("InputIllegalTypeNewArrayStructure.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalTypeCheck check = new IllegalTypeCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    @Test
    public void testImproperToken() {
        final IllegalTypeCheck check = new IllegalTypeCheck();

        final DetailAstImpl classDefAst = new DetailAstImpl();
        classDefAst.setType(TokenTypes.DOT);

        try {
            check.visitToken(classDefAst);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException ex) {
            // it is OK
        }
    }

}
