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
        config.addAttribute("allowedAnnotations", "MyAnnotation, Override");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("InputJavadocMethodExtendAnnotation.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "Override,ThisIsOk, \t\n\t ThisIsOkToo");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(config, getPath("InputJavadocMethodAllowedAnnotations.java"), expected);
    }

    @Test
    public void testThrowsDetection() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("validateThrows", "true");
        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "27:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "30:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "43:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "47:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "58:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "61:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "72:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "75:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "87:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "101:31: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "IllegalArgumentException"),
            "113:19: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "UnsupportedOperationException"),
            "128:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
            "141:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "RuntimeException"),
        };
        verify(config, getPath("InputJavadocMethodThrowsDetection.java"), expected);
    }

    @Test
    public void testExtraThrows() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("validateThrows", "true");
        final String[] expected = {
            "45:56: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "60:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "72:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "84:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                    "java.lang.IllegalArgumentException"),
            "124:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "FileNotFoundException"),
        };
        verify(config, getPath("InputJavadocMethodExtraThrows.java"), expected);
    }

    @Test
    public void testIgnoreThrows() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("validateThrows", "true");
        final String[] expected = {
            "31:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "33:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalStateException"),
            "49:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "129:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "185:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
        };
        verify(config, getPath("InputJavadocMethodIgnoreThrows.java"), expected);
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "28: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "37: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "44:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "53:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "59:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "59:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "NullPointerException"),
            "64:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "72:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "76:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "77:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "77:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aTwo"),
            "82:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "83: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "91:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "260:28: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "266:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "309: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "309:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aParam"),
            "348:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "387:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
        };

        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "72:29: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "77:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "82:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "87:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "97:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("accessModifiers", "");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("accessModifiers", "public, protected");
        final String[] expected = {
            "79:41: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
            "84:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verify(checkConfig, getPath("InputJavadocMethodProtectedScopeJavadoc.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("accessModifiers", "public");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("accessModifiers", "public, protected, package, private");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "18: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "20: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "22: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "24: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadocDefault.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("accessModifiers", "public, protected");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "21: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadocProtectedScope.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("accessModifiers", "private, package, public");
        final String[] expected = {
            "20: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "24: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "26: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadocOnlyPrivateScope.java"), expected);
    }

    @Test
    public void testAllowMissingJavadocTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        checkConfig.addAttribute("allowMissingReturnTag", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodMissingJavadocNoMissingTags.java"),
                expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "15: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "25:26: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "number"),
            "56: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "66: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "77: " + getCheckMessage(MSG_RETURN_EXPECTED),
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
            "27:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BB>"),
            "29:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
            "54:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<Z"),
            "56:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
        };
        verify(checkConfig, getPath("InputJavadocMethodTypeParamsTags.java"), expected);
    }

    @Test
    public void testAllowUndocumentedParamsTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "19:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam"),
            "20:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam2"),
            "22:13: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam3"),
            "23:6: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unexpectedParam4"),
            "51:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "t"),
            "53:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "w"),
            "62:7: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "63:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "y"),

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
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "21:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RE"),
            "37:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<NPE>"),
            "47:38: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RuntimeException"),
            "48:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "java.lang.RuntimeException"),
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
            "9:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "14:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "34:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "39:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "44:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "49:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
        };
        verify(checkConfig, getPath("InputJavadocMethodInheritDoc.java"), expected);
    }

    @Test
    public void testMethodsNotSkipWrittenJavadocs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "MyAnnotation");
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "21:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
        };
        verify(checkConfig, getPath("InputJavadocMethodsNotSkipWritten.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "MyAnnotation");
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "21:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
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
            "11:49: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "13:50: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };
        verify(checkConfig, getPath("InputJavadocMethodConstructor.java"), expected);
    }

    @Test
    public void testJavadocMethodRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("validateThrows", "true");
        checkConfig.addAttribute("tokens", "METHOD_DEF , CTOR_DEF , ANNOTATION_FIELD_DEF,"
            + " COMPACT_CTOR_DEF, RECORD_DEF, CLASS_DEF");

        final String[] expected = {
            "26:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "39:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws",
                "java.lang.IllegalArgumentException"),
            "51: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "57:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "67: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "73:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),
            "83:12: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "properties"),
            "86:35: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "myInt"),
            "90:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IllegalArgumentException"),

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
