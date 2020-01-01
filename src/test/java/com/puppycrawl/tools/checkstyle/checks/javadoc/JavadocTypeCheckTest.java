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
        verify(checkConfig, getPath("InputJavadocTypePublicOnly.java"), expected);
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
               getPath("InputJavadocTypeScopeInnerInterfaces.java"),
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
            "43: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
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
        verify(checkConfig, getPath("InputJavadocTypeScopeInnerClasses.java"), expected);
    }

    @Test
    public void testAuthorRequired() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "\\S");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
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
            "22: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "58: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "94: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
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
            "13: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "22: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "31: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "49: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "58: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "67: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "85: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
            "94: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "103: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verify(checkConfig, getPath("InputJavadocTypeJavadoc.java"), expected);
    }

    @Test
    public void testVersionRequired()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\S");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verify(checkConfig, getPath("InputJavadocTypeWhitespace.java"), expected);
    }

    @Test
    public void testVersionRegularEx()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "^\\p{Digit}+\\.\\p{Digit}+$");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "58: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "94: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verify(checkConfig, getPath("InputJavadocTypeJavadoc.java"), expected);
    }

    @Test
    public void testVersionRegularExError()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\$Revision.*\\$");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "22: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "31: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "40: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "49: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "58: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "67: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "76: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "85: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "94: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "103: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "112: " + getCheckMessage(MSG_TAG_FORMAT, "@version", "\\$Revision.*\\$"),
        };
        verify(checkConfig, getPath("InputJavadocTypeJavadoc.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "4: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
            "123: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
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
            "4: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verify(checkConfig,
               getPath("InputJavadocTypeNoJavadoc.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "123: " + getCheckMessage(MSG_MISSING_TAG, "@param <T>"),
        };
        verify(checkConfig,
               getPath("InputJavadocTypeNoJavadoc.java"),
               expected);
    }

    @Test
    public void testTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "7:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "11: " + getCheckMessage(MSG_MISSING_TAG, "@param <C456>"),
            "44:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "47: " + getCheckMessage(MSG_MISSING_TAG, "@param <B>"),
            "60:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<x>"),
        };
        verify(checkConfig, getPath("InputJavadocTypeTypeParamsTags.java"), expected);
    }

    @Test
    public void testAllowMissingTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        final String[] expected = {
            "7:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "44:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "60:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<x>"),
        };
        verify(checkConfig, getPath("InputJavadocTypeTypeParamsTags.java"), expected);
    }

    @Test
    public void testDontAllowUnusedParameterTag() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "6:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BAD>"),
            "7:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BAD>"),
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
            "5:4: " + getCheckMessage(MSG_UNKNOWN_TAG, "mytag"),
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
                getPath("InputJavadocTypeBadTag.java"),
                expected);
    }

    @Test
    public void testAllowedAnnotationsDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getNonCompilablePath("InputJavadocTypeAllowedAnnotations.java"),
            expected);
    }

    @Test
    public void testAllowedAnnotationsWithFullyQualifiedName() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "allowedAnnotations",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getNonCompilablePath("InputJavadocTypeAllowedAnnotations.java"),
                expected);
    }

    @Test
    public void testAllowedAnnotationsAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "Generated, ThisIsOk");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getNonCompilablePath("InputJavadocTypeAllowedAnnotations.java"),
            expected);
    }

    @Test
    public void testAllowedAnnotationsNotAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowedAnnotations", "Override");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getNonCompilablePath("InputJavadocTypeAllowedAnnotations.java"),
            expected);
    }
}
