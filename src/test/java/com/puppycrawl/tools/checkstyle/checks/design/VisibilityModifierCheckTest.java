///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck.MSG_KEY;

import java.io.File;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
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
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testInner()
            throws Exception {
        final String[] expected = {
            "47:24: " + getCheckMessage(MSG_KEY, "rData"),
            "50:27: " + getCheckMessage(MSG_KEY, "protectedVariable"),
            "53:17: " + getCheckMessage(MSG_KEY, "packageVariable"),
            "58:29: " + getCheckMessage(MSG_KEY, "sWeird"),
            "60:19: " + getCheckMessage(MSG_KEY, "sWeird2"),
            "94:20: " + getCheckMessage(MSG_KEY, "someValue"),
            "97:11: " + getCheckMessage(MSG_KEY, "fSerialVersionUID"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierInner.java"), expected);
    }

    @Test
    public void testIgnoreAccess()
            throws Exception {
        final String[] expected = {
            "34:20: " + getCheckMessage(MSG_KEY, "fData"),
            "94:20: " + getCheckMessage(MSG_KEY, "someValue"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierInner1.java"), expected);
    }

    @Test
    public void testSimple() throws Exception {
        final String[] expected = {
            "49:19: " + getCheckMessage(MSG_KEY, "mNumCreated2"),
            "59:23: " + getCheckMessage(MSG_KEY, "sTest1"),
            "61:26: " + getCheckMessage(MSG_KEY, "sTest3"),
            "63:16: " + getCheckMessage(MSG_KEY, "sTest2"),
            "66:9: " + getCheckMessage(MSG_KEY, "mTest1"),
            "68:16: " + getCheckMessage(MSG_KEY, "mTest2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierSimple.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final String[] expected = {
            "49:9: " + getCheckMessage(MSG_KEY, "mLen"),
            "50:19: " + getCheckMessage(MSG_KEY, "mDeer"),
            "51:16: " + getCheckMessage(MSG_KEY, "aFreddo"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierPublicOnly.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInImmutableClass() throws Exception {
        final String[] expected = {
            "33:39: " + getCheckMessage(MSG_KEY, "includes"),
            "34:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "37:23: " + getCheckMessage(MSG_KEY, "list"),
            "55:20: " + getCheckMessage(MSG_KEY, "value"),
            "57:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "58:31: " + getCheckMessage(MSG_KEY, "longValue"),
            "62:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInImmutableClassWithNonCanonicalClasses()
            throws Exception {
        final String[] expected = {
            "28:39: " + getCheckMessage(MSG_KEY, "includes"),
            "29:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "31:29: " + getCheckMessage(MSG_KEY, "money"),
            "32:23: " + getCheckMessage(MSG_KEY, "list"),
            "48:35: " + getCheckMessage(MSG_KEY, "uri"),
            "49:35: " + getCheckMessage(MSG_KEY, "file"),
            "50:20: " + getCheckMessage(MSG_KEY, "value"),
            "51:35: " + getCheckMessage(MSG_KEY, "url"),
            "52:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "53:21: " + getCheckMessage(MSG_KEY, "longValue"),
            "57:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable2.java"), expected);
    }

    @Test
    public void testDisAllowPublicFinalAndImmutableFieldsInImmutableClass() throws Exception {
        final String[] expected = {
            "32:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "33:39: " + getCheckMessage(MSG_KEY, "includes"),
            "34:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "35:25: " + getCheckMessage(MSG_KEY, "notes"),
            "36:29: " + getCheckMessage(MSG_KEY, "money"),
            "37:23: " + getCheckMessage(MSG_KEY, "list"),
            "51:28: " + getCheckMessage(MSG_KEY, "f"),
            "52:30: " + getCheckMessage(MSG_KEY, "bool"),
            "53:35: " + getCheckMessage(MSG_KEY, "uri"),
            "54:35: " + getCheckMessage(MSG_KEY, "file"),
            "55:20: " + getCheckMessage(MSG_KEY, "value"),
            "56:35: " + getCheckMessage(MSG_KEY, "url"),
            "57:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "58:21: " + getCheckMessage(MSG_KEY, "longValue"),
            "62:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable3.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInNonFinalClass() throws Exception {
        final String[] expected = {
            "55:20: " + getCheckMessage(MSG_KEY, "value"),
            "57:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "58:21: " + getCheckMessage(MSG_KEY, "longValue"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable4.java"), expected);
    }

    @Test
    public void testUserSpecifiedImmutableClassesList() throws Exception {
        final String[] expected = {
            "30:29: " + getCheckMessage(MSG_KEY, "money"),
            "47:35: " + getCheckMessage(MSG_KEY, "uri"),
            "48:35: " + getCheckMessage(MSG_KEY, "file"),
            "49:20: " + getCheckMessage(MSG_KEY, "value"),
            "50:35: " + getCheckMessage(MSG_KEY, "url"),
            "51:24: " + getCheckMessage(MSG_KEY, "bValue"),
            "52:21: " + getCheckMessage(MSG_KEY, "longValue"),
            "56:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable5.java"), expected);
    }

    @Test
    public void testImmutableSpecifiedSameTypeName() throws Exception {
        final String[] expected = {
            "23:46: " + getCheckMessage(MSG_KEY, "calendar"),
            "26:36: " + getCheckMessage(MSG_KEY, "address"),
            "27:36: " + getCheckMessage(MSG_KEY, "adr"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutableSameTypeName.java"),
                expected);
    }

    @Test
    public void testImmutableValueSameTypeName() throws Exception {
        final String[] expected = {
            "28:46: " + getCheckMessage(MSG_KEY, "calendar"),
            "29:59: " + getCheckMessage(MSG_KEY, "calendar2"),
            "30:59: " + getCheckMessage(MSG_KEY, "calendar3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutableSameTypeName2.java"),
                expected);
    }

    @Test
    public void testImmutableStarImportFalseNegative() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutableStarImport.java"), expected);
    }

    @Test
    public void testImmutableStarImportNoWarn() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutableStarImport2.java"),
                expected);
    }

    @Test
    public void testDefaultAnnotationPatterns() throws Exception {
        final String[] expected = {
            "61:19: " + getCheckMessage(MSG_KEY, "customAnnotatedPublic"),
            "64:12: " + getCheckMessage(MSG_KEY, "customAnnotatedPackage"),
            "67:22: " + getCheckMessage(MSG_KEY, "customAnnotatedProtected"),
            "69:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "70:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "71:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierAnnotated.java"), expected);
    }

    @Test
    public void testCustomAnnotationPatterns() throws Exception {
        final String[] expected = {
            "37:28: " + getCheckMessage(MSG_KEY, "publicJUnitRule"),
            "40:28: " + getCheckMessage(MSG_KEY, "fqPublicJUnitRule"),
            "43:19: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPublic"),
            "46:12: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPackage"),
            "49:22: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedProtected"),
            "52:19: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPublic"),
            "55:12: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPackage"),
            "58:22: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedProtected"),
            "69:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "70:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "71:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
            "80:35: " + getCheckMessage(MSG_KEY, "publicJUnitClassRule"),
            "83:35: " + getCheckMessage(MSG_KEY, "fqPublicJUnitClassRule"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierAnnotated2.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationNoPattern() throws Exception {
        final String[] expected = {
            "36:28: " + getCheckMessage(MSG_KEY, "publicJUnitRule"),
            "39:28: " + getCheckMessage(MSG_KEY, "fqPublicJUnitRule"),
            "42:19: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPublic"),
            "45:12: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedPackage"),
            "48:22: " + getCheckMessage(MSG_KEY, "googleCommonsAnnotatedProtected"),
            "51:19: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPublic"),
            "54:12: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedPackage"),
            "57:22: " + getCheckMessage(MSG_KEY, "fqGoogleCommonsAnnotatedProtected"),
            "60:19: " + getCheckMessage(MSG_KEY, "customAnnotatedPublic"),
            "63:12: " + getCheckMessage(MSG_KEY, "customAnnotatedPackage"),
            "66:22: " + getCheckMessage(MSG_KEY, "customAnnotatedProtected"),
            "68:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "69:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "70:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
            "79:35: " + getCheckMessage(MSG_KEY, "publicJUnitClassRule"),
            "82:35: " + getCheckMessage(MSG_KEY, "fqPublicJUnitClassRule"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierAnnotated3.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationSameName() throws Exception {
        final String[] expected = {
            "33:28: " + getCheckMessage(MSG_KEY, "publicJUnitRule"),
            "36:28: " + getCheckMessage(MSG_KEY, "publicJUnitClassRule"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierAnnotatedSameTypeName.java"),
                expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final VisibilityModifierCheck obj = new VisibilityModifierCheck();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IMPORT,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(obj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testPublicImmutableFieldsNotAllowed() throws Exception {
        final String[] expected = {
            "31:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "32:39: " + getCheckMessage(MSG_KEY, "includes"),
            "33:35: " + getCheckMessage(MSG_KEY, "notes"),
            "34:29: " + getCheckMessage(MSG_KEY, "value"),
            "35:23: " + getCheckMessage(MSG_KEY, "list"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifiersPublicImmutable.java"), expected);
    }

    @Test
    public void testPublicFinalFieldsNotAllowed() throws Exception {
        final String[] expected = {
            "31:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "32:39: " + getCheckMessage(MSG_KEY, "includes"),
            "33:25: " + getCheckMessage(MSG_KEY, "notes"),
            "34:29: " + getCheckMessage(MSG_KEY, "value"),
            "35:23: " + getCheckMessage(MSG_KEY, "list"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifiersPublicImmutable2.java"), expected);
    }

    @Test
    public void testPublicFinalFieldsAllowed() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifiersPublicImmutable3.java"), expected);
    }

    @Test
    public void testPublicFinalFieldInEnum() throws Exception {
        final String[] expected = {
            "35:23: " + getCheckMessage(MSG_KEY, "hole"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifiersEnumIsSealed.java"), expected);
    }

    @Test
    public void testWrongTokenType() {
        final VisibilityModifierCheck obj = new VisibilityModifierCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.CLASS_DEF, "class"));
        try {
            obj.visitToken(ast);
            assertWithMessage("exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Unexpected token type: class");
        }
    }

    @Test
    public void testNullModifiers() throws Exception {
        final String[] expected = {
            "32:50: " + getCheckMessage(MSG_KEY, "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifiersNullModifiers.java"), expected);
    }

    @Test
    public void testVisibilityModifiersOfGenericFields() throws Exception {
        final String[] expected = {
            "31:56: " + getCheckMessage(MSG_KEY, "perfSeries"),
            "32:66: " + getCheckMessage(MSG_KEY, "peopleMap"),
            "33:66: " + getCheckMessage(MSG_KEY, "someMap"),
            "34:76: " + getCheckMessage(MSG_KEY, "newMap"),
            "36:45: " + getCheckMessage(MSG_KEY, "optionalOfObject"),
            "37:35: " + getCheckMessage(MSG_KEY, "obj"),
            "39:19: " + getCheckMessage(MSG_KEY, "rqUID"),
            "40:29: " + getCheckMessage(MSG_KEY, "rqTime"),
            "41:45: " + getCheckMessage(MSG_KEY, "rates"),
            "42:50: " + getCheckMessage(MSG_KEY, "loans"),
            "43:60: " + getCheckMessage(MSG_KEY, "cards"),
            "44:60: " + getCheckMessage(MSG_KEY, "values"),
            "45:70: " + getCheckMessage(MSG_KEY, "permissions"),
            "47:38: " + getCheckMessage(MSG_KEY, "mapOfStrings"),
            "48:48: " + getCheckMessage(MSG_KEY, "names"),
            "49:48: " + getCheckMessage(MSG_KEY, "links"),
            "50:38: " + getCheckMessage(MSG_KEY, "presentations"),
            "51:48: " + getCheckMessage(MSG_KEY, "collection"),
            "54:73: " + getCheckMessage(MSG_KEY, "exceptions"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierGenerics.java"), expected);
    }

    /**
     * We can not cover this mutation because it forces all imports to be non static,
     * but static imports are ignored, so we will not see any effect on validation.
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
            JavaParser.Options.WITHOUT_COMMENTS).getFirstChild().getNextSibling();
        final VisibilityModifierCheck check = new VisibilityModifierCheck();
        final boolean actual = TestUtil.invokeMethod(check, "isStarImport", importAst);

        assertWithMessage("Should return true when star import is passed")
                .that(actual)
                .isTrue();
    }

    @Test
    public void testPackageClassName() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputVisibilityModifierPackageClassName.java"),
                expected);
    }

}
