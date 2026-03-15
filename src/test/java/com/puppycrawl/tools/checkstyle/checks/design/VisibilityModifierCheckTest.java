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
    public String getPackageLocation() {
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
            "51:27: " + getCheckMessage(MSG_KEY, "safeField"),
            "55:17: " + getCheckMessage(MSG_KEY, "localField"),
            "61:29: " + getCheckMessage(MSG_KEY, "sWeird"),
            "64:19: " + getCheckMessage(MSG_KEY, "sWeird2"),
            "99:20: " + getCheckMessage(MSG_KEY, "someValue"),
            "103:11: " + getCheckMessage(MSG_KEY, "fSerialVersionUID"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierInner.java"), expected);
    }

    @Test
    public void testIgnoreAccess()
            throws Exception {
        final String[] expected = {
            "34:20: " + getCheckMessage(MSG_KEY, "fData"),
            "95:20: " + getCheckMessage(MSG_KEY, "someValue"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierInner1.java"), expected);
    }

    @Test
    public void testSimple1() throws Exception {
        final String[] expected = {
            "49:19: " + getCheckMessage(MSG_KEY, "mNumCreated2"),
            "60:23: " + getCheckMessage(MSG_KEY, "sTest1"),
            "63:26: " + getCheckMessage(MSG_KEY, "sTest3"),
            "66:16: " + getCheckMessage(MSG_KEY, "sTest2"),
            "70:9: " + getCheckMessage(MSG_KEY, "mTest1"),
            "73:16: " + getCheckMessage(MSG_KEY, "mTest2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierSimple.java"), expected);
    }

    @Test
    public void testSimple2() throws Exception {
        final String[] expected = {
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierSimple2.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final String[] expected = {
            "49:9: " + getCheckMessage(MSG_KEY, "mLen"),
            "51:19: " + getCheckMessage(MSG_KEY, "mDeer"),
            "53:16: " + getCheckMessage(MSG_KEY, "aFreddo"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierPublicOnly.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInImmutableClass() throws Exception {
        final String[] expected = {
            "33:39: " + getCheckMessage(MSG_KEY, "includes"),
            "35:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "39:23: " + getCheckMessage(MSG_KEY, "list"),
            "58:20: " + getCheckMessage(MSG_KEY, "value"),
            "61:24: " + getCheckMessage(MSG_KEY, "rate"),
            "63:31: " + getCheckMessage(MSG_KEY, "id"),
            "68:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInImmutableClassWithNonCanonicalClasses()
            throws Exception {
        final String[] expected = {
            "28:39: " + getCheckMessage(MSG_KEY, "includes"),
            "30:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "33:29: " + getCheckMessage(MSG_KEY, "money"),
            "35:23: " + getCheckMessage(MSG_KEY, "list"),
            "52:35: " + getCheckMessage(MSG_KEY, "uri"),
            "54:35: " + getCheckMessage(MSG_KEY, "file"),
            "56:20: " + getCheckMessage(MSG_KEY, "value"),
            "58:35: " + getCheckMessage(MSG_KEY, "url"),
            "60:24: " + getCheckMessage(MSG_KEY, "rate"),
            "62:21: " + getCheckMessage(MSG_KEY, "id"),
            "67:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable2.java"), expected);
    }

    @Test
    public void testDisAllowPublicFinalAndImmutableFieldsInImmutableClass() throws Exception {
        final String[] expected = {
            "32:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "34:39: " + getCheckMessage(MSG_KEY, "includes"),
            "36:39: " + getCheckMessage(MSG_KEY, "excludes"),
            "38:25: " + getCheckMessage(MSG_KEY, "notes"),
            "40:29: " + getCheckMessage(MSG_KEY, "money"),
            "42:23: " + getCheckMessage(MSG_KEY, "list"),
            "57:28: " + getCheckMessage(MSG_KEY, "f"),
            "59:30: " + getCheckMessage(MSG_KEY, "bool"),
            "61:35: " + getCheckMessage(MSG_KEY, "uri"),
            "63:35: " + getCheckMessage(MSG_KEY, "file"),
            "65:20: " + getCheckMessage(MSG_KEY, "value"),
            "67:35: " + getCheckMessage(MSG_KEY, "url"),
            "69:24: " + getCheckMessage(MSG_KEY, "rate"),
            "71:21: " + getCheckMessage(MSG_KEY, "id"),
            "76:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable3.java"), expected);
    }

    @Test
    public void testAllowPublicFinalFieldsInNonFinalClass() throws Exception {
        final String[] expected = {
            "55:20: " + getCheckMessage(MSG_KEY, "value"),
            "58:24: " + getCheckMessage(MSG_KEY, "rate"),
            "60:21: " + getCheckMessage(MSG_KEY, "id"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable4.java"), expected);
    }

    @Test
    public void testUserSpecifiedImmutableClassesList() throws Exception {
        final String[] expected = {
            "30:29: " + getCheckMessage(MSG_KEY, "money"),
            "48:35: " + getCheckMessage(MSG_KEY, "uri"),
            "50:35: " + getCheckMessage(MSG_KEY, "file"),
            "52:20: " + getCheckMessage(MSG_KEY, "value"),
            "54:35: " + getCheckMessage(MSG_KEY, "url"),
            "56:24: " + getCheckMessage(MSG_KEY, "rate"),
            "58:21: " + getCheckMessage(MSG_KEY, "id"),
            "63:19: " + getCheckMessage(MSG_KEY, "C_D_E"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutable5.java"), expected);
    }

    @Test
    public void testImmutableSpecifiedSameTypeName() throws Exception {
        final String[] expected = {
            "23:46: " + getCheckMessage(MSG_KEY, "calendar"),
            "27:36: " + getCheckMessage(MSG_KEY, "address"),
            "29:36: " + getCheckMessage(MSG_KEY, "adr"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierImmutableSameTypeName.java"),
                expected);
    }

    @Test
    public void testImmutableValueSameTypeName() throws Exception {
        final String[] expected = {
            "28:46: " + getCheckMessage(MSG_KEY, "calendar"),
            "30:59: " + getCheckMessage(MSG_KEY, "calendar2"),
            "32:59: " + getCheckMessage(MSG_KEY, "calendar3"),
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
            "61:19: " + getCheckMessage(MSG_KEY, "publicCustom"),
            "65:12: " + getCheckMessage(MSG_KEY, "packageCustom"),
            "69:22: " + getCheckMessage(MSG_KEY, "protectedCustom"),
            "72:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "74:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "76:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierAnnotated.java"), expected);
    }

    @Test
    public void testCustomAnnotationPatterns() throws Exception {
        final String[] expected = {
            "37:28: " + getCheckMessage(MSG_KEY, "rule"),
            "41:28: " + getCheckMessage(MSG_KEY, "ruleFull"),
            "45:19: " + getCheckMessage(MSG_KEY, "publicGoogle"),
            "49:12: " + getCheckMessage(MSG_KEY, "packageGoogle"),
            "53:22: " + getCheckMessage(MSG_KEY, "protectedGoogle"),
            "57:19: " + getCheckMessage(MSG_KEY, "publicGoogleFull"),
            "61:12: " + getCheckMessage(MSG_KEY, "packageGoogleFull"),
            "65:22: " + getCheckMessage(MSG_KEY, "protectedGoogleFull"),
            "77:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "79:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "81:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
            "91:35: " + getCheckMessage(MSG_KEY, "classRule"),
            "95:35: " + getCheckMessage(MSG_KEY, "classRuleFull"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierAnnotated2.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationNoPattern() throws Exception {
        final String[] expected = {
            "36:28: " + getCheckMessage(MSG_KEY, "rule"),
            "40:28: " + getCheckMessage(MSG_KEY, "ruleFull"),
            "44:19: " + getCheckMessage(MSG_KEY, "publicGoogle"),
            "48:12: " + getCheckMessage(MSG_KEY, "packageGoogle"),
            "52:22: " + getCheckMessage(MSG_KEY, "protectedGoogle"),
            "56:19: " + getCheckMessage(MSG_KEY, "publicGoogleFull"),
            "60:12: " + getCheckMessage(MSG_KEY, "packageGoogleFull"),
            "64:22: " + getCheckMessage(MSG_KEY, "protectedGoogleFull"),
            "68:19: " + getCheckMessage(MSG_KEY, "publicCustom"),
            "72:12: " + getCheckMessage(MSG_KEY, "packageCustom"),
            "76:22: " + getCheckMessage(MSG_KEY, "protectedCustom"),
            "79:19: " + getCheckMessage(MSG_KEY, "unannotatedPublic"),
            "81:12: " + getCheckMessage(MSG_KEY, "unannotatedPackage"),
            "83:22: " + getCheckMessage(MSG_KEY, "unannotatedProtected"),
            "93:35: " + getCheckMessage(MSG_KEY, "classRule"),
            "97:35: " + getCheckMessage(MSG_KEY, "classRuleFull"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifierAnnotated3.java"), expected);
    }

    @Test
    public void testIgnoreAnnotationSameName() throws Exception {
        final String[] expected = {
            "33:28: " + getCheckMessage(MSG_KEY, "rule"),
            "37:28: " + getCheckMessage(MSG_KEY, "classRule"),
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
            "33:39: " + getCheckMessage(MSG_KEY, "includes"),
            "35:35: " + getCheckMessage(MSG_KEY, "notes"),
            "37:29: " + getCheckMessage(MSG_KEY, "value"),
            "39:23: " + getCheckMessage(MSG_KEY, "list"),
        };
        verifyWithInlineConfigParser(
                getPath("InputVisibilityModifiersPublicImmutable.java"), expected);
    }

    @Test
    public void testPublicFinalFieldsNotAllowed() throws Exception {
        final String[] expected = {
            "31:22: " + getCheckMessage(MSG_KEY, "someIntValue"),
            "33:39: " + getCheckMessage(MSG_KEY, "includes"),
            "35:25: " + getCheckMessage(MSG_KEY, "notes"),
            "37:29: " + getCheckMessage(MSG_KEY, "value"),
            "39:23: " + getCheckMessage(MSG_KEY, "list"),
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
        catch (IllegalArgumentException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
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
            "33:66: " + getCheckMessage(MSG_KEY, "peopleMap"),
            "35:66: " + getCheckMessage(MSG_KEY, "someMap"),
            "37:76: " + getCheckMessage(MSG_KEY, "newMap"),
            "40:45: " + getCheckMessage(MSG_KEY, "optionalOfObject"),
            "42:35: " + getCheckMessage(MSG_KEY, "obj"),
            "45:19: " + getCheckMessage(MSG_KEY, "rqUID"),
            "47:29: " + getCheckMessage(MSG_KEY, "rqTime"),
            "49:45: " + getCheckMessage(MSG_KEY, "rates"),
            "51:50: " + getCheckMessage(MSG_KEY, "loans"),
            "53:60: " + getCheckMessage(MSG_KEY, "cards"),
            "55:60: " + getCheckMessage(MSG_KEY, "values"),
            "57:70: " + getCheckMessage(MSG_KEY, "permissions"),
            "60:38: " + getCheckMessage(MSG_KEY, "mapOfStrings"),
            "62:48: " + getCheckMessage(MSG_KEY, "names"),
            "64:48: " + getCheckMessage(MSG_KEY, "links"),
            "66:38: " + getCheckMessage(MSG_KEY, "presentations"),
            "68:48: " + getCheckMessage(MSG_KEY, "collection"),
            "72:73: " + getCheckMessage(MSG_KEY, "exceptions"),
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
        final boolean actual = TestUtil.invokeMethod(check, "isStarImport",
                Boolean.class, importAst);

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
