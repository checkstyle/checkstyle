////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_INVALID_INHERIT_DOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_UNUSED_TAG_GENERAL;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

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
        final String[] expected = {
            "7:8: " + getCheckMessage(MSG_CLASS_INFO, "@throws", "InvalidExceptionName"),
        };
        verify(config, getPath("InputJavadocMethodLoadErrors.java"), expected);
    }

    @Test
    public void extendAnnotationTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "MyAnnotation, Override");
        config.addAttribute("minLineCount", "2");
        final String[] expected = {
            "44:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("InputJavadocMethodExtendAnnotation.java"), expected);
    }

    @Test
    public void newTest() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "MyAnnotation, Override");
        config.addAttribute("minLineCount", "2");
        final String[] expected = {
            "57:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(config, getPath("InputJavadocMethodSmallMethods.java"), expected);
    }

    @Test
    public void allowedAnnotationsTest() throws Exception {

        final DefaultConfiguration config = createModuleConfig(JavadocMethodCheck.class);
        config.addAttribute("allowedAnnotations", "Override,ThisIsOk, \t\n\t ThisIsOkToo");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(config, getPath("InputJavadocMethodAllowedAnnotations.java"), expected);
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
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
            "328:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testTagsWithResolver() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
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
            "328:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testStrictJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
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
            "94:32: " + getCheckMessage(MSG_EXPECTED_TAG, "@param", "aA"),
        };
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.NOTHING.getName());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    // pre 1.4 relaxed mode is roughly equivalent with check=protected
    @Test
    public void testRelaxedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "59:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "64:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "79:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocMethodPublicOnly.java"), expected);
    }

    @Test
    public void testScopeInnerInterfacesPublic() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "43:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocMethodScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testScopeAnonInnerPrivate() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerAnonInner() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.ANONINNER.getName());
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39:17: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "53:17: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testScopeAnonInnerWithResolver() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodScopeAnonInner.java"), expected);
    }

    @Test
    public void testTagsWithSubclassesAllowed() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("validateThrows", "true");
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
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
            "328:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "337:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocMethodTags.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
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
            "108:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "119:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "11:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22:9: " + getCheckMessage(MSG_JAVADOC_MISSING), };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "12:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "33:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "36:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "45:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "47:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "48:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "58:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "60:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "61:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "69:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "72:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "81:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "83:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "84:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "93:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "95:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "96:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "105:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "107:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "108:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "119:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testAllowMissingJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowMissingJavadoc", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodNoJavadoc.java"), expected);
    }

    @Test
    public void testAllowMissingJavadocTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        checkConfig.addAttribute("allowMissingThrowsTags", "true");
        checkConfig.addAttribute("allowMissingReturnTag", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
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
        verify(checkConfig, getPath("InputJavadocMethodSetterGetter.java"), expected);
    }

    @Test
    public void testSetterGetterOn() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
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
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_01.java"), expected);
    }

    @Test
    public void test11684082() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethod_02.java"), expected);
    }

    @Test
    public void test11684083() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("allowThrowsTagsForSubclasses", "true");
        checkConfig.addAttribute("allowUndeclaredRTE", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
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
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
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
    public void testSkipCertainMethods() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("ignoreMethodNamesRegex", "^foo.*$");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodIgnoreNameRegex.java"), expected);
    }

    @Test
    public void testNotSkipAnythingWhenSkipRegexDoesNotMatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocMethodCheck.class);
        checkConfig.addAttribute("ignoreMethodNamesRegex", "regexThatDoesNotMatch");
        final String[] expected = {
            "5:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "9:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "13:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputJavadocMethodIgnoreNameRegex.java"), expected);
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
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMethodReceiverParameter.java"), expected);
    }
}
