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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_DUPLICATE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_INVALID_INHERIT_DOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG_GENERAL;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMethodCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocMethodCheck javadocMethodCheck = new JavadocMethodCheck();

        final int[] actual = javadocMethodCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void extendAnnotationTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addProperty("allowedAnnotations", "MyAnnotation, Override");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("InputJavadocMethodExtendAnnotation.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addProperty("allowedAnnotations", "Override,ThisIsOk, \t\n\t ThisIsOkToo");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("InputJavadocMethodAllowedAnnotations.java"), expected);
    }

    @Test
    public void testThrowsDetection() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addProperty("validateThrows", "true");
        final String[] expected = {
            "24:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "35:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "38:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "51:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "55:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "66:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "69:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "80:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "83:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "95:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "109:31: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "121:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "136:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
            "149:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
        };
        verify(config, getPath("InputJavadocMethodThrowsDetection.java"), expected);
    }

    @Test
    public void testExtraThrows() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addProperty("validateThrows", "true");
        final String[] expected = {
            "53:56: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "68:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "80:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "92:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "java.lang.IllegalArgumentException"),
            "132:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "FileNotFoundException"),
        };
        verify(config, getPath("InputJavadocMethodExtraThrows.java"), expected);
    }

    @Test
    public void testIgnoreThrows() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addProperty("validateThrows", "true");
        final String[] expected = {
            "39:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "41:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "57:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "137:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "193:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verify(config, getPath("InputJavadocMethodIgnoreThrows.java"), expected);
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("validateThrows", "true");
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "36: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "45: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "52:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "61:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "67:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "67:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "NullPointerException"),
            "72:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "80:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "84:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "85:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "85:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aTwo"),
            "90:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "91: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "99:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "268:28: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "274:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "317: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "317:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aParam"),
            "356:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "395:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
        };

        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "77:29: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "82:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "87:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "92:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "102:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("accessModifiers", "");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly1.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("accessModifiers", "public, protected");
        final String[] expected = {
            "87:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "92:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verify(checkConfig, getPath("InputJavadocMethodProtectedScopeJavadoc.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("accessModifiers", "public");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("accessModifiers", "public, protected, package, private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "27: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "29: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "31: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "33: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadocDefault.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("accessModifiers", "public, protected");
        final String[] expected = {
            "27: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "29: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadocProtectedScope.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("accessModifiers", "private, package, public");
        final String[] expected = {
            "27: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "31: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "33: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadocOnlyPrivateScope.java"), expected);
    }

    @Test
    public void testAllowMissingJavadocTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("allowMissingParamTags", "true");
        checkConfig.addProperty("allowMissingReturnTag", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodMissingJavadocNoMissingTags.java"),
                expected);
    }

    @Test
    public void testSurroundingAccessModifier() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("accessModifiers", "private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodSurroundingAccessModifier.java"), expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "22: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "32:26: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "number"),
            "63: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "73: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "84: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verify(checkConfig, getPath("InputJavadocMethodMissingJavadocTagsDefault.java"), expected);
    }

    @Test
    public void testSetterGetter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testTypeParamsTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "37:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BB>"),
            "39:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
            "64:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<Z"),
            "66:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
        };
        verify(checkConfig, getPath("InputJavadocMethodTypeParamsTags.java"), expected);
    }

    @Test
    public void testAllowUndocumentedParamsTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "29:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam"),
            "30:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam2"),
            "32:13: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam3"),
            "33:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam4"),
            "61:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "t"),
            "63:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "w"),
            "72:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "73:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "y"),
        };
        verify(checkConfig, getPath("InputJavadocMethodParamsTags.java"), expected);
    }

    @Test
    public void test11684081() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_01.java"), expected);
    }

    @Test
    public void test11684082() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_02.java"), expected);
    }

    @Test
    public void test11684083() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_03.java"), expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("validateThrows", "true");
        final String[] expected = {
            "29:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RE"),
            "45:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<NPE>"),
            "55:38: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RuntimeException"),
            "56:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verify(checkConfig, getPath("InputJavadocMethodGenerics.java"), expected);
    }

    @Test
    public void test1379666() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_1379666.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "23:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "43:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "48:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "53:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "58:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
        };
        verify(checkConfig, getPath("InputJavadocMethodInheritDoc.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("allowedAnnotations", "MyAnnotation");
        final String[] expected = {
            "19:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "29:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
        };
        verify(checkConfig, getPath("InputJavadocMethodsNotSkipWritten.java"), expected);
    }

    @Test
    public void testJava8ReceiverParameter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodReceiverParameter.java"), expected);
    }

    @Test
    public void testJavadocInMethod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodJavadocInMethod.java"), expected);
    }

    @Test
    public void testConstructor() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "20:49: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "22:50: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };
        verify(checkConfig, getPath("InputJavadocMethodConstructor.java"), expected);
    }

    @Test
    public void testJavadocMethodRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addProperty("validateThrows", "true");
        checkConfig.addProperty("tokens", "METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF,"
            + " COMPACT_CTOR_DEF, RECORD_DEF, CLASS_DEF");

        final String[] expected = {
            "28:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "41:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "java.lang.IllegalArgumentException"),
            "53: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "59:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "69: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "75:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "85:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "properties"),
            "88:35: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "myInt"),
            "92:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verify(checkConfig,
            getNonCompilablePath("InputJavadocMethodRecordsAndCompactCtors.java"), expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
        };
        final JavadocMethodCheck check = new JavadocMethodCheck();
        final int[] actual = check.getRequiredTokens();
        assertArrayEquals(expected, actual, "Required tokens differ from expected");
    }

    @Test
    public void testTokenToString() throws Exception {
        final Class<?> tokenType = Class.forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                + "JavadocMethodCheck$Token");
        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        final Object token = tokenConstructor.newInstance("tokenName", 1, 1);
        final Method toString = token.getClass().getDeclaredMethod("toString");
        final String result = (String) toString.invoke(token);
        assertEquals("Token[tokenName(1x1)]", result, "Invalid toString result");
    }

    @Test
    public void testWithoutLogErrors() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        verify(config, getPath("InputJavadocMethodLoadErrors.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

}
