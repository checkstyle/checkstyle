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
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        final String[] expected = {
            "10:38: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "27:5: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "29:37: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "33:12: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeAbstractClassNames.java"), expected);
    }

    @Test
    public void testValidateAbstractClassNamesSetToFalse() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("validateAbstractClassNames", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputIllegalTypeAbstractClassNames.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "42:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "44:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testIgnoreMethodNames() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("ignoredMethodNames", "table2");
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        final String[] expected = {
            "6:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "9:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalType.AbstractClass"),
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "25:36: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "42:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "44:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalAbstractClassNameFormat", "^$");

        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "42:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "44:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testLegalAbstractClassNames() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        checkConfig.addAttribute("legalAbstractClassNames", "AbstractClass");

        final String[] expected = {
            "9:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalType.AbstractClass"),
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "42:14: " + getCheckMessage(MSG_KEY, "HashMap"),
            "44:5: " + getCheckMessage(MSG_KEY, "HashMap"),
        };

        verify(checkConfig, getPath("InputIllegalType.java"), expected);
    }

    @Test
    public void testSameFileNameFalsePositive() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                "java.util.GregorianCalendar, SubCalendar, java.util.List");

        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY, "SubCalendar"),
            "27:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };

        verify(checkConfig, getPath("InputIllegalTypeSameFileName.java"), expected);
    }

    @Test
    public void testSameFileNameGeneral() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames",
            "List, InputIllegalTypeGregorianCalendar, java.io.File, ArrayList, Boolean");
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregorianCalendar"),
            "14:43: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregorianCalendar"),
            "16:23: " + getCheckMessage(MSG_KEY, "InputIllegalTypeGregorianCalendar"),
            "24:9: " + getCheckMessage(MSG_KEY, "List"),
            "25:9: " + getCheckMessage(MSG_KEY, "java.io.File"),
            "27:5: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "28:13: " + getCheckMessage(MSG_KEY, "ArrayList"),
            "29:13: " + getCheckMessage(MSG_KEY, "Boolean"),
        };
        verify(checkConfig, getPath("InputIllegalTypeSameFileName.java"), expected);
    }

    @Test
    public void testArrayTypes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames", "Boolean[], Boolean[][]");
        final String[] expected = {
            "6:12: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "8:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "10:12: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "11:9: " + getCheckMessage(MSG_KEY, "Boolean[]"),
            "15:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "16:9: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
        };
        verify(checkConfig, getPath("InputIllegalTypeArrays.java"), expected);
    }

    @Test
    public void testPlainAndArrayTypes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames", "Boolean, Boolean[][]");
        final String[] expected = {
            "6:12: " + getCheckMessage(MSG_KEY, "Boolean"),
            "10:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "12:12: " + getCheckMessage(MSG_KEY, "Boolean"),
            "21:12: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
            "22:9: " + getCheckMessage(MSG_KEY, "Boolean[][]"),
        };
        verify(checkConfig, getPath("InputIllegalTypePlainAndArrays.java"), expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                "Boolean, Foo, Serializable");
        checkConfig.addAttribute("memberModifiers", "LITERAL_PUBLIC, FINAL");
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_KEY, "Boolean"),
            "21:31: " + getCheckMessage(MSG_KEY, "Boolean"),
            "21:40: " + getCheckMessage(MSG_KEY, "Foo"),
            "24:18: " + getCheckMessage(MSG_KEY, "Boolean"),
            "25:24: " + getCheckMessage(MSG_KEY, "Foo"),
            "25:44: " + getCheckMessage(MSG_KEY, "Boolean"),
            "28:23: " + getCheckMessage(MSG_KEY, "Boolean"),
            "28:42: " + getCheckMessage(MSG_KEY, "Serializable"),
            "30:54: " + getCheckMessage(MSG_KEY, "Boolean"),
            "32:25: " + getCheckMessage(MSG_KEY, "Boolean"),
            "32:60: " + getCheckMessage(MSG_KEY, "Boolean"),
            "34:26: " + getCheckMessage(MSG_KEY, "Foo"),
            "34:30: " + getCheckMessage(MSG_KEY, "Boolean"),
            "38:26: " + getCheckMessage(MSG_KEY, "Foo"),
            "38:38: " + getCheckMessage(MSG_KEY, "Boolean"),
            "47:20: " + getCheckMessage(MSG_KEY, "Boolean"),
            "60:28: " + getCheckMessage(MSG_KEY, "Boolean"),
        };
        verify(checkConfig, getPath("InputIllegalTypeGenerics.java"), expected);
    }

    @Test
    public void testExtendsImplements() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames",
                "Boolean, Foo, Hashtable, Serializable");
        checkConfig.addAttribute("memberModifiers", "LITERAL_PUBLIC");
        final String[] expected = {
            "16:17: " + getCheckMessage(MSG_KEY, "Hashtable"),
            "17:14: " + getCheckMessage(MSG_KEY, "Boolean"),
            "22:23: " + getCheckMessage(MSG_KEY, "Boolean"),
            "24:13: " + getCheckMessage(MSG_KEY, "Serializable"),
            "26:24: " + getCheckMessage(MSG_KEY, "Foo"),
            "27:27: " + getCheckMessage(MSG_KEY, "Boolean"),
            "30:32: " + getCheckMessage(MSG_KEY, "Foo"),
            "31:28: " + getCheckMessage(MSG_KEY, "Boolean"),
            "32:13: " + getCheckMessage(MSG_KEY, "Serializable"),
        };
        verify(checkConfig, getPath("InputIllegalTypeExtendsImplements.java"), expected);
    }

    @Test
    public void testStarImports() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames", "List");

        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_KEY, "List"),
        };

        verify(checkConfig, getPath("InputIllegalTypeStarImports.java"), expected);
    }

    @Test
    public void testStaticImports() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames", "SomeStaticClass");
        checkConfig.addAttribute("ignoredMethodNames", "foo1");

        final String[] expected = {
            "13:6: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
            "15:31: " + getCheckMessage(MSG_KEY, "SomeStaticClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeStaticImports.java"), expected);
    }

    @Test
    public void testMemberModifiers() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("validateAbstractClassNames", "true");
        checkConfig.addAttribute("memberModifiers", "LITERAL_PRIVATE, LITERAL_PROTECTED,"
                + " LITERAL_STATIC");
        final String[] expected = {
            "6:13: " + getCheckMessage(MSG_KEY, "AbstractClass"),
            "9:13: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalTypeMemberModifiers.AbstractClass"),
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
            "23:15: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.coding.illegaltype."
                    + "InputIllegalTypeMemberModifiers.AbstractClass"),
            "25:25: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "33:15: " + getCheckMessage(MSG_KEY, "AbstractClass"),
        };

        verify(checkConfig, getPath("InputIllegalTypeMemberModifiers.java"), expected);
    }

    @Test
    public void testPackageClassName() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        checkConfig.addAttribute("illegalClassNames", "com.PackageClass");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("InputIllegalTypePackageClassName.java"),
                expected);
    }

    @Test
    public void testClearDataBetweenFiles() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalTypeCheck.class);
        final String violationFile = getPath("InputIllegalType.java");
        checkConfig.addAttribute("illegalClassNames", "java.util.TreeSet");
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),
            "17:13: " + getCheckMessage(MSG_KEY, "TreeSet"),
        };

        verify(createChecker(checkConfig), new File[] {
            new File(violationFile),
            new File(getPath("InputIllegalTypeSimilarClassName.java")),
        }, violationFile, expected);
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
