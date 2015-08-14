////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_CLASS_INFO;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_DUPLICATE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXCPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_INVALID_INHERIT_DOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG_GENERAL;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class JavadocMethodCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(JavadocMethodCheck.class);
    }

    @Test
    public void testGetAcceptableTokens() {
        JavadocMethodCheck javadocMethodCheck = new JavadocMethodCheck();

        int[] actual = javadocMethodCheck.getAcceptableTokens();
        int[] expected = new int[]{
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
        };

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testLogLoadErrors() throws Exception {
        DefaultConfiguration config = createCheckConfig(JavadocMethodCheck.class);
        config.addAttribute("logLoadErrors", "true");
        config.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {
            "7:8: " + getCheckMessage(MSG_CLASS_INFO, "@throws", "InvalidExceptionName"),
        };
        verify(config, getPath("javadoc/InputLoadErrors.java"), expected);
    }

    @Test
    public void extendAnnotationTest() throws Exception {
        DefaultConfiguration config = createCheckConfig(JavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "MyAnnotation, Override");
        config.addAttribute("minLineCount", "2");
        final String[] expected = {
            "46:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("javadoc/ExtendAnnotation.java"), expected);
    }

    @Test
    public void newTest() throws Exception {
        DefaultConfiguration config = createCheckConfig(JavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "MyAnnotation, Override");
        config.addAttribute("minLineCount", "2");
        final String[] expected = {
            "57:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("javadoc/InputJavadocMethodCheck_SmallMethods.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {

        DefaultConfiguration config = createCheckConfig(JavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "Override,ThisIsOk, \t\n\t ThisIsOkToo");
        final String[] expected = {};
        verify(config, getPath("javadoc/AllowedAnnotations.java"), expected);
    }

    @Test
    public void testTags() throws Exception {
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "24: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "33: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "40:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "49:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "53:9: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "WrongException"),
            "55:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "55:27: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "NullPointerException"),
            "60:22: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "68:22: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "72:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "73:23: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "73:33: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aTwo"),
            "78:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "79: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "87:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "109:23: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "109:55: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aFour"),
            "109:66: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aFive"),
            "178:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ThreadDeath"),
            "179:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ArrayStoreException"),
            "236:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "254:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "256:28: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "IOException"),
            "262:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "320:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "329:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "333: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
        };

        verify(checkConfig, getPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testTagsWithResolver() throws Exception {
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "24: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "33: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "40:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "49:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "55:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "55:27: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "NullPointerException"),
            "60:22: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "68:22: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "72:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "73:23: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "73:33: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aTwo"),
            "78:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "79: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "87:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "109:23: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "109:55: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aFour"),
            "109:66: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aFive"),
            "236:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "254:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "java.io.FileNotFoundException"),
            "256:28: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "IOException"),
            "262:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "320:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "329:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "333: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL), };
        verify(checkConfig, getPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "54:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:32: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aA"),
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        checkConfig.addAttribute("scope", Scope.NOTHING.getName());
        final String[] expected = {};
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        final String[] expected = {};
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerAnonInner() throws Exception {
        checkConfig.addAttribute("scope", Scope.ANONINNER.getName());
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:17: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:17: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerWithResolver() throws Exception {
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {};
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);
    }

    @Test
    public void testTagsWithSubclassesAllowed() throws Exception {
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "18:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "unused"),
            "24: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "33: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "40:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "49:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "55:16: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "Exception"),
            "55:27: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "NullPointerException"),
            "60:22: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "68:22: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "72:9: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "WrongParam"),
            "73:23: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "73:33: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aTwo"),
            "78:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "Unneeded"),
            "79: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL),
            "87:8: " + getCheckMessage(MSG_DUPLICATE_TAG, "@return"),
            "109:23: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aOne"),
            "109:55: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aFour"),
            "109:66: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "aFive"),
            "178:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ThreadDeath"),
            "179:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "ArrayStoreException"),
            "256:28: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "IOException"),
            "262:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "aParam"),
            "320:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "329:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "333: " + getCheckMessage(MSG_UNUSED_TAG_GENERAL), };
        verify(checkConfig, getPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "70:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "96:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "106:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "108:9: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "46:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "70:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "82:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "94:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "96:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "106:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "108:9: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testAllowMissingJavadoc() throws Exception {
        checkConfig.addAttribute("allowMissingJavadoc", "true");
        final String[] expected = {};
        verify(checkConfig, getPath("javadoc" + File.separator
                                    + "InputNoJavadoc.java"), expected);
    }

    @Test
    public void testAllowMissingJavadocTags() throws Exception {
        checkConfig.addAttribute("allowMissingParamTags", "true");
        checkConfig.addAttribute("allowMissingThrowsTags", "true");
        checkConfig.addAttribute("allowMissingReturnTag", "true");
        final String[] expected = {};
        verify(checkConfig, getPath("javadoc" + File.separator
                                    + "InputMissingJavadocTags.java"), expected);
    }

    @Test
    public void testDoAllowMissingJavadocTagsByDefault() throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "20:26: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "number"),
            "30:42: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "ThreadDeath"),
            "51: " + getCheckMessage(MSG_RETURN_EXPECTED),
            "61: " + getCheckMessage(MSG_RETURN_EXPECTED),
        };
        verify(checkConfig, getPath("javadoc" + File.separator
                + "InputMissingJavadocTags.java"), expected);
    }

    @Test
    public void testSetterGetterOff() throws Exception {
        final String[] expected = {
            "7:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("javadoc" + File.separator
                                    + "InputSetterGetter.java"), expected);
    }

    @Test
    public void testSetterGetterOn() throws Exception {
        checkConfig.addAttribute("allowMissingPropertyJavadoc", "true");
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "28:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "43:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "55:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "67:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "74:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "76:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("javadoc" + File.separator
                                    + "InputSetterGetter.java"), expected);
    }

    @Test
    public void testTypeParamsTags() throws Exception {
        final String[] expected = {
            "26:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BB>"),
            "28:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "<Z>"),
            "53:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<Z"),
            "55:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "<Z>"),
        };
        verify(checkConfig, getPath("InputTypeParamsTags.java"), expected);
    }

    @Test
    public void test_1168408_1() throws Exception {
        final String[] expected = {};
        verify(checkConfig, getPath("checks/javadoc/Input_01.java"), expected);
    }

    @Test
    public void test_1168408_2() throws Exception {
        final String[] expected = {};
        verify(checkConfig, getPath("checks/javadoc/Input_02.java"), expected);
    }

    @Test
    public void test_1168408_3() throws Exception {
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {};
        verify(checkConfig, getPath("checks/javadoc/Input_03.java"), expected);
    }

    @Test
    public void test_generics_1() throws Exception {
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "17:34: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "RE"),
            "33:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "<NPE>"),
            "40:12: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "E"),
            "43:38: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "RuntimeException"),
            "44:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verify(checkConfig, getPath("javadoc/TestGenerics.java"), expected);
    }

    @Test
    public void test_generics_2() throws Exception {
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "17:34: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "RE"),
            "33:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "<NPE>"),
            "40:12: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "E"),
            "43:38: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "RuntimeException"),
            "44:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verify(checkConfig, getPath("javadoc/TestGenerics.java"), expected);
    }

    @Test
    public void test_generics_3() throws Exception {
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "8:8: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "RE"),
            "17:34: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "RE"),
            "33:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@param", "<NPE>"),
            "40:12: " + getCheckMessage(MSG_UNUSED_TAG, "@throws", "E"),
            "43:38: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "RuntimeException"),
            "44:13: " + getCheckMessage(MSG_EXCPECTED_TAG, "@throws", "java.lang.RuntimeException"),
        };
        verify(checkConfig, getPath("javadoc/TestGenerics.java"), expected);
    }

    @Test
    public void test_1379666() throws Exception {
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = {};
        verify(checkConfig, getSrcPath("checks/javadoc/Input_1379666.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final String[] expected = {
            "6:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "11:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "31:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "36:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "41:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
            "46:5: " + getCheckMessage(MSG_INVALID_INHERIT_DOC),
        };
        verify(checkConfig, getPath("javadoc/InputInheritDoc.java"), expected);
    }

    @Test
    public void testSkipCertainMethods() throws Exception {
        checkConfig.addAttribute("ignoreMethodNamesRegex", "^foo.*$");
        String[] expected = {

        };
        verify(checkConfig, getPath("javadoc/InputJavadocMethodIgnoreNameRegex.java"), expected);
    }

    @Test
    public void testNotSkipAnythingWhenSkipRegexDoesNotMatch() throws Exception {
        checkConfig.addAttribute("ignoreMethodNamesRegex", "regexThatDoesNotMatch");
        String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("javadoc/InputJavadocMethodIgnoreNameRegex.java"), expected);
    }
}
