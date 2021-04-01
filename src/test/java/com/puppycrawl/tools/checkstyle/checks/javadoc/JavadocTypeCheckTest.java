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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNKNOWN_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNUSED_TAG;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocTypeCheck javadocTypeCheck = new JavadocTypeCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, javadocTypeCheck.getRequiredTokens(),
                "JavadocTypeCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocTypeCheck javadocTypeCheck = new JavadocTypeCheck();

        final int[] actual = javadocTypeCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocTypeTags.java"), expected);
    }

    @Test
    public void testInner() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocTypeInner.java"), expected);
    }

    @Test
    public void testStrict() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocTypePublicOnly.java"), expected);
    }

    @Test
    public void testProtected() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocTypePublicOnly1.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
               getPath("InputJavadocTypeScopeInnerInterfaces.java"),
               expected);
    }

    @Test
    public void testProtest() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
               getPath("InputJavadocTypeScopeInnerInterfaces1.java"),
               expected);
    }

    @Test
    public void testPkg() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.PACKAGE.getName());
        final String[] expected = {
            "48:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verify(checkConfig, getPath("InputJavadocTypeScopeInnerClasses.java"), expected);
    }

    @Test
    public void testEclipse() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.PUBLIC.getName());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocTypeScopeInnerClasses1.java"), expected);
    }

    @Test
    public void testAuthorRequired() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "\\S");
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verify(checkConfig, getPath("InputJavadocTypeWhitespace.java"), expected);
    }

    @Test
    public void testAuthorRegularEx()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "0*");
        final String[] expected = {
            "27:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "63:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "99:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verify(checkConfig, getPath("InputJavadocTypeJavadoc.java"), expected);
    }

    @Test
    public void testAuthorRegularExError()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "ABC");
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "27:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "36:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "54:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "63:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "72:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "90:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "99:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "108:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verify(checkConfig, getPath("InputJavadocTypeJavadoc_1.java"), expected);
    }

    @Test
    public void testVersionRequired()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\S");
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verify(checkConfig, getPath("InputJavadocTypeWhitespace_1.java"), expected);
    }

    @Test
    public void testVersionRegularEx()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "^\\p{Digit}+\\.\\p{Digit}+$");
        final String[] expected = {
            "27:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "63:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "99:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verify(checkConfig, getPath("InputJavadocTypeJavadoc_3.java"), expected);
    }

    @Test
    public void testVersionRegularExError()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\$Revision.*\\$");
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "27:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "36:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "45:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "54:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "63:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "72:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "81:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "90:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "99:1: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "108:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "117:1: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
        };
        verify(checkConfig, getPath("InputJavadocTypeJavadoc_2.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "125:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verify(checkConfig,
               getPath("InputJavadocTypeNoJavadoc.java"),
               expected);
    }

    @Test
    public void testLimitViolationsBySpecifyingTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("tokens", "INTERFACE_DEF");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
               getPath("InputJavadocTypeNoJavadocOnInterface.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verify(checkConfig,
               getPath("InputJavadocTypeNoJavadoc_2.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "129:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verify(checkConfig,
               getPath("InputJavadocTypeNoJavadoc_1.java"),
               expected);
    }

    @Test
    public void testTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "9:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "13:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <C456>"),
            "46:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "49:5: " + getCheckMessage(MSG_MISSING_TAG, "@param <B>"),
            "62:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
        };
        verify(checkConfig, getPath("InputJavadocTypeTypeParamsTags_1.java"), expected);
    }

    @Test
    public void testAllowMissingTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        final String[] expected = {
            "12:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "49:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "65:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
        };
        verify(checkConfig, getPath("InputJavadocTypeTypeParamsTags.java"), expected);
    }

    @Test
    public void testDontAllowUnusedParameterTag() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "8:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "BAD"),
            "9:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BAD>"),
        };
        verify(checkConfig,
                getPath("InputJavadocTypeUnusedParamInJavadocForClass.java"),
                expected);
    }

    @Test
    public void testBadTag() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "7:4: " + getCheckMessage(MSG_UNKNOWN_TAG, "mytag"),
        };
        verify(checkConfig,
               getPath("InputJavadocTypeBadTag.java"),
               expected);
    }

    @Test
    public void testBadTagSuppress() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowUnknownTags", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputJavadocTypeBadTag_1.java"),
                expected);
    }

    @Test
    public void testAllowedAnnotationsDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputJavadocTypeAllowedAnnotations.java"),
            expected);
    }

    @Test
    public void testAllowedAnnotationsWithFullyQualifiedName() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "allowedAnnotations",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk_1");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputJavadocTypeAllowedAnnotations_1.java"),
                expected);
    }

    @Test
    public void testAllowedAnnotationsAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "SuppressWarnings, ThisIsOk_2");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputJavadocTypeAllowedAnnotations_2.java"),
            expected);
    }

    @Test
    public void testAllowedAnnotationsNotAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "Override");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputJavadocTypeAllowedAnnotations_3.java"),
            expected);
    }

    @Test
    public void testJavadocTypeRecords() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "ABC");
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "31:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "40:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "53:1: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "63:1: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verify(checkConfig, getNonCompilablePath("InputJavadocTypeRecords.java"), expected);
    }

    @Test
    public void testJavadocTypeRecordComponents() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", "protected");
        checkConfig.addAttribute("allowMissingParamTags", "false");
        checkConfig.addAttribute("allowUnknownTags", "false");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
            getNonCompilablePath("InputJavadocTypeRecordComponents.java"), expected);
    }

    @Test
    public void testJavadocTypeRecordComponents2() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("allowMissingParamTags", "false");
        checkConfig.addAttribute("allowUnknownTags", "false");

        final String[] expected = {
            "37:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <X>"),
            "41:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "52:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "notMyString"),
            "55:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myString"),
            "55:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myInt"),
            "59:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "x"),
            "61:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myList"),
            "68:1: " + getCheckMessage(MSG_MISSING_TAG, "@param X"),
            "71:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "notMyString"),
            "74:1: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "74:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myInt"),
            "74:1: " + getCheckMessage(MSG_MISSING_TAG, "@param myString"),
        };
        verify(checkConfig,
            getNonCompilablePath("InputJavadocTypeRecordComponents2.java"), expected);
    }
}
