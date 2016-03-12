////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNKNOWN_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_UNUSED_TAG;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * @author Oliver.Burn
 */
public class JavadocTypeCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "javadoc" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocTypeCheck javadocTypeCheck = new JavadocTypeCheck();
        assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, javadocTypeCheck.getRequiredTokens());
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

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "302: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "327: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputTags.java"), expected);
    }

    @Test
    public void testInner() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "14: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testStrict() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "14: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "34: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testProtected() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "7: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "7: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testProtest() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "7: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "29: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "38: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "65: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testPkg() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.getInstance("package").getName());
        final String[] expected = {
            "18: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "22: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputScopeInnerClasses.java"), expected);
    }

    @Test
    public void testEclipse() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute(
            "scope",
            Scope.getInstance("public").getName());
        final String[] expected = {
            "18: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputScopeInnerClasses.java"), expected);
    }

    @Test
    public void testAuthorRequired() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "\\S");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testAuthorRegularEx()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "0*");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "58: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
            "94: " + getCheckMessage(MSG_MISSING_TAG, "@author"),
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testAuthorRegularExError()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
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
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testVersionRequired()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "\\S");
        final String[] expected = {
            "13: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testVersionRegularEx()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("versionFormat", "^\\p{Digit}+\\.\\p{Digit}+$");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "58: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
            "94: " + getCheckMessage(MSG_MISSING_TAG, "@version"),
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testVersionRegularExError()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
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
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "27: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "99: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "111: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testLimitViolationsBySpecifyingTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("tokens", "INTERFACE_DEF");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputNoJavadocOnInterface.java"), expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "3: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "27: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "39: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "52: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "63: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "75: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "87: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "99: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "111: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "7:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "11: " + getCheckMessage(MSG_MISSING_TAG, "@param <C456>"),
            "44:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "47: " + getCheckMessage(MSG_MISSING_TAG, "@param <B>"),
            "60:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<x>"),
        };
        verify(checkConfig, getPath("InputTypeParamsTags.java"), expected);
    }

    @Test
    public void testAllowMissingTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        final String[] expected = {
            "7:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<D123>"),
            "44:8: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<C>"),
            "60:5: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<x>"),
        };
        verify(checkConfig, getPath("InputTypeParamsTags.java"), expected);
    }

    @Test
    public void testDontAllowUnusedParameterTag() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "6:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BAD>"),
            "7:4: " + getCheckMessage(MSG_UNUSED_TAG, "@param", "<BAD>"),
        };
        verify(checkConfig,
                getPath("InputUnusedParamInJavadocForClass.java"),
                expected);
    }

    @Test
    public void testBadTag() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "5:4: " + getCheckMessage(MSG_UNKNOWN_TAG, "mytag"),
        };
        verify(checkConfig,
               getPath("InputBadTag.java"),
               expected);
    }

    @Test
    public void testBadTagSuppress() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowUnknownTags", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputBadTag.java"),
                expected);
    }
}
