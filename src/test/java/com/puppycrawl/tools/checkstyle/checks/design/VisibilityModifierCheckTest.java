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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import antlr.CommonHiddenStreamToken;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class VisibilityModifierCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/visibilitymodifier";
    }

    @Test
    public void testGetRequiredTokens() {
        final VisibilityModifierCheck checkObj = new VisibilityModifierCheck();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IMPORT,
        };
        assertArrayEquals(
                expected, checkObj.getRequiredTokens(), "Default required tokens are invalid");
    }

    @Test
    public void testInner()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^f[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "30:24: " + getCheckMessage(MSG_KEY, "rData"),
            "33:27: " + getCheckMessage(MSG_KEY, "protectedVariable"),
            "36:17: " + getCheckMessage(MSG_KEY, "packageVariable"),
            "41:29: " + getCheckMessage(MSG_KEY, "sWeird"),
            "43:19: " + getCheckMessage(MSG_KEY, "sWeird2"),
            "77:20: " + getCheckMessage(MSG_KEY, "someValue"),
            "80:11: " + getCheckMessage(MSG_KEY, "fSerialVersionUID"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierInner.java"), expected);
    }

    @Test
    public void testIgnoreAccess()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^r[A-Z]");
        checkConfig.addAttribute("protectedAllowed", "true");
        checkConfig.addAttribute("packageAllowed", "true");
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY, "fData"),
            "77:20: " + getCheckMessage(MSG_KEY, "someValue"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierInner.java"), expected);
    }

    @Test
    public void testSimple() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^f[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "33:19: " + getCheckMessage(MSG_KEY, "mNumCreated2"),
            "43:23: " + getCheckMessage(MSG_KEY, "sTest1"),
            "45:26: " + getCheckMessage(MSG_KEY, "sTest3"),
            "47:16: " + getCheckMessage(MSG_KEY, "sTest2"),
            "50:9: " + getCheckMessage(MSG_KEY, "mTest1"),
            "52:16: " + getCheckMessage(MSG_KEY, "mTest2"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierSimple.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("publicMemberPattern", "^f[A-Z][a-zA-Z0-9]*$");
        final String[] expected = {
            "32:9: " + getCheckMessage(MSG_KEY, "mLen"),
            "33:19: " + getCheckMessage(MSG_KEY, "mDeer"),
            "34:16: " + getCheckMessage(MSG_KEY, "aFreddo"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierPublicOnly.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInImmutableClass() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        final String[] expected = {
            "12:39: " + getCheckMessage(MSG_KEY, "includes"),
            "13:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "16:23: " + getCheckMessage(MSG_KEY, "list"),
            "34:20: " + getCheckMessage(MSG_KEY, "value"),
            "36:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "37:31: " + getCheckMessage(MSG_KEY, "longValue"),
            "41:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierImmutable.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInImmutableClassWithNonCanonicalClasses()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        checkConfig.addAttribute("immutableClassCanonicalNames", "String, Integer, Byte, "
                + "Character, Short, Boolean, Long, Double, Float, StackTraceElement, BigInteger, "
                + "BigDecimal, File, Locale, UUID, URL, URI, Inet4Address, Inet6Address, "
                + "InetSocketAddress");
        final String[] expected = {
            "12:39: " + getCheckMessage(MSG_KEY, "includes"),
            "13:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "14:35: " + getCheckMessage(MSG_KEY, "notes"),
            "15:29: " + getCheckMessage(MSG_KEY, "money"),
            "16:23: " + getCheckMessage(MSG_KEY, "list"),
            "32:35: " + getCheckMessage(MSG_KEY, "uri"),
            "33:35: " + getCheckMessage(MSG_KEY, "file"),
            "34:20: " + getCheckMessage(MSG_KEY, "value"),
            "35:35: " + getCheckMessage(MSG_KEY, "url"),
            "36:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "37:31: " + getCheckMessage(MSG_KEY, "longValue"),
            "41:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierImmutable.java"), expected);
    }

    @Test
    public void testDisAllowPublicFinalAndImmutableFieldsInImmutableClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        final String[] expected = {
            "11:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "12:39: " + getCheckMessage(MSG_KEY, "includes"),
            "13:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "14:35: " + getCheckMessage(MSG_KEY, "notes"),
            "15:29: " + getCheckMessage(MSG_KEY, "money"),
            "16:23: " + getCheckMessage(MSG_KEY, "list"),
            "30:28: " + getCheckMessage(MSG_KEY, "f"),
            "31:30: " + getCheckMessage(MSG_KEY, "bool"),
            "32:35: " + getCheckMessage(MSG_KEY, "uri"),
            "33:35: " + getCheckMessage(MSG_KEY, "file"),
            "34:20: " + getCheckMessage(MSG_KEY, "value"),
            "35:35: " + getCheckMessage(MSG_KEY, "url"),
            "36:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "37:31: " + getCheckMessage(MSG_KEY, "longValue"),
            "41:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
            };
        verify(checkConfig, getPath("InputVisibilityModifierImmutable.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInNonFinalClass() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicFinalFields", "true");
        final String[] expected = {
            "34:20: " + getCheckMessage(MSG_KEY, "value"),
            "36:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "37:31: " + getCheckMessage(MSG_KEY, "longValue"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierImmutable.java"), expected);
    }

    @Test
    public void testUserSpecifiedImmutableClassesList() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        checkConfig.addAttribute("immutableClassCanonicalNames", "java.util.List,"
                + "com.google.common.collect.ImmutableSet, java.lang.String");
        final String[] expected = {
            "15:29: " + getCheckMessage(MSG_KEY, "money"),
            "32:35: " + getCheckMessage(MSG_KEY, "uri"),
            "33:35: " + getCheckMessage(MSG_KEY, "file"),
            "34:20: " + getCheckMessage(MSG_KEY, "value"),
            "35:35: " + getCheckMessage(MSG_KEY, "url"),
            "36:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "37:31: " + getCheckMessage(MSG_KEY, "longValue"),
            "41:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierImmutable.java"), expected);
    }

    @Test
    public void testImmutableSpecifiedSameTypeName() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        checkConfig.addAttribute("immutableClassCanonicalNames",
                 "com.puppycrawl.tools.checkstyle.checks.design."
                         + "visibilitymodifier.InputVisibilityModifierGregorianCalendar,"
                 + "com.puppycrawl.tools.checkstyle.checks.design."
                         + "visibilitymodifier.inputs.InetSocketAddress");
        final String[] expected = {
            "7:46: " + getCheckMessage(MSG_KEY, "calendar"),
            "12:45: " + getCheckMessage(MSG_KEY, "adr"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierImmutableSameTypeName.java"),
                expected);
    }

    @Test
    public void testImmutableValueSameTypeName() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        final String[] expected = {
            "7:46: " + getCheckMessage(MSG_KEY, "calendar"),
            "8:59: " + getCheckMessage(MSG_KEY, "calendar2"),
            "10:73: " + getCheckMessage(MSG_KEY, "calendar3"),
            "11:36: " + getCheckMessage(MSG_KEY, "address"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierImmutableSameTypeName.java"),
                expected);
    }

    @Test
    public void testImmutableStarImportFalseNegative() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        checkConfig.addAttribute("immutableClassCanonicalNames", "java.util.Arrays");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputVisibilityModifierImmutableStarImport.java"), expected);
    }

    @Test
    public void testImmutableStarImportNoWarn() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        checkConfig.addAttribute("immutableClassCanonicalNames",
            "java.lang.String, com.google.common.collect.ImmutableSet");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputVisibilityModifierImmutableStarImport2.java"),
                expected);
    }

    @Test
    public void testDefaultAnnotationPatterns() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        final String[] expected = {
            "40:19: " + getCheckMessage(MSG_KEY, "customAnnotatedPublic"),
            "43:12: " + getCheckMessage(MSG_KEY, "customAnnotatedPackage"),
            "46:22: " + getCheckMessage(MSG_KEY, "customAnnotatedProtected"),
            "48:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "49:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "50:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierAnnotated.java"), expected);
    }

    @Test
    public void testCustomAnnotationPatterns() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("ignoreAnnotationCanonicalNames",
                "com.puppycrawl.tools.checkstyle.checks.design."
                    + "InputVisibilityModifierAnnotated.CustomAnnotation");
        final String[] expected = {
            "16:28: " + getCheckMessage(MSG_KEY, "publicJUnitRule"),
            "19:28: " + getCheckMessage(MSG_KEY, "fqPublicJUnitRule"),
            "22:19: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPublic"),
            "25:12: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPackage"),
            "28:22: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedProtected"),
            "31:19: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPublic"),
            "34:12: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPackage"),
            "37:22: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedProtected"),
            "48:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "49:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "50:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
            "59:35: " + getCheckMessage(MSG_KEY, "publicJUnitClassRule"),
            "62:35: " + getCheckMessage(MSG_KEY, "fqPublicJUnitClassRule"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierAnnotated.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationNoPattern() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("ignoreAnnotationCanonicalNames", "");
        final String[] expected = {
            "16:28: " + getCheckMessage(MSG_KEY, "publicJUnitRule"),
            "19:28: " + getCheckMessage(MSG_KEY, "fqPublicJUnitRule"),
            "22:19: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPublic"),
            "25:12: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPackage"),
            "28:22: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedProtected"),
            "31:19: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPublic"),
            "34:12: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPackage"),
            "37:22: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedProtected"),
            "40:19: " + getCheckMessage(MSG_KEY, "customAnnotatedPublic"),
            "43:12: " + getCheckMessage(MSG_KEY, "customAnnotatedPackage"),
            "46:22: " + getCheckMessage(MSG_KEY, "customAnnotatedProtected"),
            "48:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "49:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "50:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
            "59:35: " + getCheckMessage(MSG_KEY, "publicJUnitClassRule"),
            "62:35: " + getCheckMessage(MSG_KEY, "fqPublicJUnitClassRule"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierAnnotated.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationSameName() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        final String[] expected = {
            "11:28: " + getCheckMessage(MSG_KEY, "publicJUnitRule"),
            "14:28: " + getCheckMessage(MSG_KEY, "publicJUnitClassRule"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierAnnotatedSameTypeName.java"),
                expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final VisibilityModifierCheck obj = new VisibilityModifierCheck();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IMPORT,
        };
        assertArrayEquals(expected, obj.getAcceptableTokens(),
                "Default acceptable tokens are invalid");
    }

    @Test
    public void testPublicImmutableFieldsNotAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        final String[] expected = {
            "10:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "11:39: " + getCheckMessage(MSG_KEY, "includes"),
            "12:35: " + getCheckMessage(MSG_KEY, "notes"),
            "13:29: " + getCheckMessage(MSG_KEY, "value"),
            "14:23: " + getCheckMessage(MSG_KEY, "list"),
        };
        verify(checkConfig, getPath("InputVisibilityModifiersPublicImmutable.java"), expected);
    }

    @Test
    public void testPublicFinalFieldsNotAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        final String[] expected = {
            "10:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "11:39: " + getCheckMessage(MSG_KEY, "includes"),
            "12:35: " + getCheckMessage(MSG_KEY, "notes"),
            "13:29: " + getCheckMessage(MSG_KEY, "value"),
            "14:23: " + getCheckMessage(MSG_KEY, "list"),
        };
        verify(checkConfig, getPath("InputVisibilityModifiersPublicImmutable.java"), expected);
    }

    @Test
    public void testPublicFinalFieldsAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicFinalFields", "true");
        checkConfig.addAttribute("immutableClassCanonicalNames",
            "com.google.common.collect.ImmutableSet");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputVisibilityModifiersPublicImmutable.java"), expected);
    }

    @Test
    public void testPublicFinalFieldInEnum() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        final String[] expected = {
            "15:23: " + getCheckMessage(MSG_KEY, "hole"),
        };
        verify(checkConfig, getPath("InputVisibilityModifiersEnumIsSealed.java"), expected);
    }

    @Test
    public void testWrongTokenType() {
        final VisibilityModifierCheck obj = new VisibilityModifierCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.CLASS_DEF, "class"));
        try {
            obj.visitToken(ast);
            fail("exception expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unexpected token type: class", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testNullModifiers() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        final String[] expected = {
            "11:50: " + getCheckMessage(MSG_KEY, "i"),
        };
        verify(checkConfig, getPath("InputVisibilityModifiersNullModifiers.java"), expected);
    }

    @Test
    public void testVisibilityModifiersOfGenericFields() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        checkConfig.addAttribute("immutableClassCanonicalNames",
            "com.google.common.collect.ImmutableMap,"
            + "java.lang.String,"
            + "java.util.Optional,"
            + "java.math.BigDecimal");
        final String[] expected = {
            "16:56: " + getCheckMessage(MSG_KEY, "perfSeries"),
            "17:66: " + getCheckMessage(MSG_KEY, "peopleMap"),
            "18:66: " + getCheckMessage(MSG_KEY, "someMap"),
            "19:76: " + getCheckMessage(MSG_KEY, "newMap"),
            "21:45: " + getCheckMessage(MSG_KEY, "optionalOfObject"),
            "22:35: " + getCheckMessage(MSG_KEY, "obj"),
            "24:19: " + getCheckMessage(MSG_KEY, "rqUID"),
            "25:29: " + getCheckMessage(MSG_KEY, "rqTime"),
            "26:45: " + getCheckMessage(MSG_KEY, "rates"),
            "27:50: " + getCheckMessage(MSG_KEY, "loans"),
            "28:60: " + getCheckMessage(MSG_KEY, "cards"),
            "29:60: " + getCheckMessage(MSG_KEY, "values"),
            "30:70: " + getCheckMessage(MSG_KEY, "permissions"),
            "32:38: " + getCheckMessage(MSG_KEY, "mapOfStrings"),
            "33:48: " + getCheckMessage(MSG_KEY, "names"),
            "34:48: " + getCheckMessage(MSG_KEY, "links"),
            "35:38: " + getCheckMessage(MSG_KEY, "presentations"),
            "36:48: " + getCheckMessage(MSG_KEY, "collection"),
            "39:73: " + getCheckMessage(MSG_KEY, "exceptions"),
        };
        verify(checkConfig, getPath("InputVisibilityModifierGenerics.java"), expected);
    }

    /**
     * We can not cover this mutation because it force all imports to be non static,
     * but static imports are ignored, so we will not see any affect on validation.
     * We could remove this method at all, and it will work correctly as we can not use
     * class with name "", but in this case internal collection will have short names
     * as "" that will not make problems, but will be weird in debug.
     *
     * @throws Exception when exception occurred during execution.
     */
    @Test
    public void testIsStarImportNullAst() throws Exception {
        final DetailAST importAst = JavaParser.parseFile(
            new File(getPath("InputVisibilityModifierIsStarImport.java")),
            JavaParser.Options.WITHOUT_COMMENTS).getNextSibling();
        final VisibilityModifierCheck check = new VisibilityModifierCheck();
        final Method method = Whitebox.getMethod(VisibilityModifierCheck.class,
            "isStarImport", DetailAST.class);

        assertTrue((boolean) method.invoke(check, importAst),
                "Should return true when star import is passed");
    }

    @Test
    public void testPackageClassName() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(VisibilityModifierCheck.class);
        checkConfig.addAttribute("immutableClassCanonicalNames", "PackageClass");
        checkConfig.addAttribute("allowPublicImmutableFields", "true");
        final String[] expected = {
        };
        verify(checkConfig, getNonCompilablePath("InputVisibilityModifierPackageClassName.java"),
                expected);
    }

}
