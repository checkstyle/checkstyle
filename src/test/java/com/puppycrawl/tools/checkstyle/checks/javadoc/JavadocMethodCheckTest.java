////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
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
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
        };

        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testLogLoadErrors() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("logLoadErrors", "true");
        config.addAttribute("allowUndeclaredRTE", "true");
        verify(config, getPath("InputJavadocMethodLoadErrors.java"), CommonUtil.EMPTY_STRING_ARRAY);
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
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "24: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "33: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "40:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "49:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "53:9: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "WrongException"),
            "55:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "55:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "NullPointerException"),
            "60:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "68:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "72:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "73:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "73:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aTwo"),
            "78:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "79: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "87:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "109:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "109:55: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aFour"),
            "109:66: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aFive"),
            "178:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ThreadDeath"),
            "179:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ArrayStoreException"),
            "236:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "254:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "256:28: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "262:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "305: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "305:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aParam"),
            "344:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "383:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "389:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "algorithm"),
        };

        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testTagsWithResolver() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "24: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "33: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "40:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "49:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "55:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "55:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "NullPointerException"),
            "60:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "68:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "72:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "73:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "73:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aTwo"),
            "78:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "79: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "87:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "109:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "109:55: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aFour"),
            "109:66: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aFive"),
            "236:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "254:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "256:28: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "262:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "305: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "305:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aParam"),
            "344:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "383:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "389:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "algorithm"),
        };
        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "94:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.NOTHING.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerAnonInner() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.ANONINNER.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerWithResolver() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testTagsWithSubclassesAllowed() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "24: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "33: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "40:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "49:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "55:16: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "Exception"),
            "55:27: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "NullPointerException"),
            "60:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "68:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "72:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "73:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "73:33: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aTwo"),
            "78:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "79: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "87:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "109:23: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aOne"),
            "109:55: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aFour"),
            "109:66: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aFive"),
            "178:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ThreadDeath"),
            "179:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ArrayStoreException"),
            "256:28: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "IOException"),
            "262:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "305: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "305:22: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aParam"),
            "344:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "383:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "389:37: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "algorithm"),
        };
        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "15: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "17: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "19: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "21: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "15: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "17: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "15: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "19: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "21: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testAllowMissingJavadocTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        checkConfig.addAttribute("allowMissingThrowsTags", "true");
        checkConfig.addAttribute("allowMissingReturnTag", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodMissingJavadocTags.java"), expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "10: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "20:26: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "number"),
            "30:42: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "ThreadDeath"),
            "51: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "61: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "72: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verify(checkConfig, getPath("InputJavadocMethodMissingJavadocTags.java"), expected);
    }

    @Test
    public void testSetterGetterOff() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testSetterGetterOn() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testTypeParamsTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "26:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BB>"),
            "28:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
            "53:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<Z"),
            "55:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<Z>"),
        };
        verify(checkConfig, getPath("InputJavadocMethodTypeParamsTags.java"), expected);
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
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_03.java"), expected);
    }

    @Test
    public void testGenerics1() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "17:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RE"),
            "33:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<NPE>"),
            "40:12: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "E"),
            "43:38: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RuntimeException"),
            "44:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verify(checkConfig, getPath("InputJavadocMethodGenerics.java"), expected);
    }

    @Test
    public void testGenerics2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "17:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RE"),
            "33:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<NPE>"),
            "40:12: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "E"),
            "43:38: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RuntimeException"),
            "44:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verify(checkConfig, getPath("InputJavadocMethodGenerics.java"), expected);
    }

    @Test
    public void testGenerics3() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "8:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "RE"),
            "17:34: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RE"),
            "33:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "<NPE>"),
            "40:12: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "E"),
            "43:38: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "RuntimeException"),
            "44:13: " + getCheckMessage(MSG_EXPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verify(checkConfig, getPath("InputJavadocMethodGenerics.java"), expected);
    }

    @Test
    public void test1379666() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_1379666.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = {
            "6:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "11:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "31:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "36:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "41:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "46:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
        };
        verify(checkConfig, getPath("InputJavadocMethodInheritDoc.java"), expected);
    }

    @Test
    public void testMethodsNotSkipWrittenJavadocs() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "MyAnnotation");
        final String[] expected = {
            "7:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "17:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
        };
        verify(checkConfig, getPath("InputJavadocMethodsNotSkipWritten.java"), expected);
    }

    @Test
    public void testAllowToSkipOverridden() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "MyAnnotation");
        final String[] expected = {
            "7:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "17:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
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
            "8:49: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
            "10:50: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "p1"),
        };
        verify(checkConfig, getPath("InputJavadocMethodConstructor.java"), expected);
    }

    @Test
    public void testIsSubclassWithNulls() throws Exception {
        final JavadocMethodCheck check = new JavadocMethodCheck();
        final Method method = check.getClass()
                .getDeclaredMethod("isSubclass", Class.class, Class.class);
        method.setAccessible(true);
        assertFalse("Should return false if at least one of the params is null",
            (boolean) method.invoke(check, null, null));
    }

    @Test
    public void testGetRequiredTokens() {
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
        };
        final JavadocMethodCheck check = new JavadocMethodCheck();
        final int[] actual = check.getRequiredTokens();
        assertArrayEquals("Required tokens differ from expected", expected, actual);
    }

    @Test
    public void testTokenToString() throws Exception {
        final Class<?> tokenType = Class.forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                + "JavadocMethodCheck$Token");
        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        final Object token = tokenConstructor.newInstance("blablabla", 1, 1);
        final Method toString = token.getClass().getDeclaredMethod("toString");
        final String result = (String) toString.invoke(token);
        assertEquals("Invalid toString result", "Token[blablabla(1x1)]", result);
    }

    @Test
    public void testClassRegularClass() throws Exception {
        final Class<?> tokenType = Class.forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                + "JavadocMethodCheck$Token");

        final Class<?> regularClassType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                        + "JavadocMethodCheck$RegularClass");
        final Constructor<?> regularClassConstructor = regularClassType.getDeclaredConstructor(
                tokenType, String.class, JavadocMethodCheck.class);
        regularClassConstructor.setAccessible(true);

        try {
            regularClassConstructor.newInstance(null, "", new JavadocMethodCheck());
            fail("Exception is expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Invalid exception class, expected: IllegalArgumentException.class",
                ex.getCause() instanceof IllegalArgumentException);
            assertEquals("Invalid exception message",
                "ClassInfo's name should be non-null", ex.getCause().getMessage());
        }

        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        final Object token = tokenConstructor.newInstance("blablabla", 1, 1);

        final JavadocMethodCheck methodCheck = new JavadocMethodCheck();
        final Object regularClass = regularClassConstructor.newInstance(token, "sur",
                methodCheck);

        final Method toString = regularClass.getClass().getDeclaredMethod("toString");
        toString.setAccessible(true);
        final String result = (String) toString.invoke(regularClass);
        final String expected = "RegularClass[name=Token[blablabla(1x1)], in class='sur', check="
                + methodCheck.hashCode() + "," + " loadable=true, class=null]";

        assertEquals("Invalid toString result", expected, result);

        final Method setClazz = regularClass.getClass().getDeclaredMethod("setClazz", Class.class);
        setClazz.setAccessible(true);
        final Class<?> arg = null;
        setClazz.invoke(regularClass, arg);

        final Method getClazz = regularClass.getClass().getDeclaredMethod("getClazz");
        getClazz.setAccessible(true);
        assertNull("Expected null", getClazz.invoke(regularClass));
    }

    @Test
    public void testClassAliasToString() throws Exception {
        final Class<?> tokenType = Class.forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                + "JavadocMethodCheck$Token");

        final Class<?> regularClassType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                        + "JavadocMethodCheck$RegularClass");
        final Constructor<?> regularClassConstructor = regularClassType.getDeclaredConstructor(
                tokenType, String.class, JavadocMethodCheck.class);
        regularClassConstructor.setAccessible(true);

        final Constructor<?> tokenConstructor = tokenType.getDeclaredConstructor(String.class,
                int.class, int.class);
        final Object token = tokenConstructor.newInstance("blablabla", 1, 1);

        final Object regularClass = regularClassConstructor.newInstance(token, "sur",
                new JavadocMethodCheck());

        final Class<?> classAliasType = Class.forName(
                "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck$ClassAlias");
        final Class<?> abstractTypeInfoType = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.javadoc."
                        + "JavadocMethodCheck$AbstractClassInfo");

        final Constructor<?> classAliasConstructor = classAliasType
                .getDeclaredConstructor(tokenType, abstractTypeInfoType);
        classAliasConstructor.setAccessible(true);

        final Object classAlias = classAliasConstructor.newInstance(token, regularClass);
        final Method toString = classAlias.getClass().getDeclaredMethod("toString");
        toString.setAccessible(true);
        final String result = (String) toString.invoke(classAlias);
        assertEquals("Invalid toString result",
            "ClassAlias[alias Token[blablabla(1x1)] for Token[blablabla(1x1)]]", result);
    }

    @Test
    public void testWithoutLogErrors() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("allowUndeclaredRTE", "true");
        verify(config, getPath("InputJavadocMethodLoadErrors.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testWithSuppressLoadErrors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("suppressLoadErrors", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocMethodLoadErrors.java"), expected);
    }

}
