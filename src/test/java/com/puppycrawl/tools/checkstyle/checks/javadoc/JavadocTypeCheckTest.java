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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.TAG_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.UNKNOWN_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.UNUSED_TAG;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * @author Oliver.Burn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JavadocTypeCheckTest extends BaseCheckTestSupport {

    @Test
    public void testGetAcceptableTokens() {
        JavadocTypeCheck javadocTypeCheck = new JavadocTypeCheck();

        int[] actual = javadocTypeCheck.getAcceptableTokens();
        int[] expected = new int[]{
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
            "8: " + getCheckMessage(JAVADOC_MISSING),
            "302: " + getCheckMessage(JAVADOC_MISSING),
            "327: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("checks/javadoc/InputTags.java"), expected);
    }

    @Test
    public void testInner() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "14: " + getCheckMessage(JAVADOC_MISSING),
            "21: " + getCheckMessage(JAVADOC_MISSING),
            "27: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testStrict() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(JAVADOC_MISSING),
            "9: " + getCheckMessage(JAVADOC_MISSING),
            "14: " + getCheckMessage(JAVADOC_MISSING),
            "34: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testProtected() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "7: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputPublicOnly.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "7: " + getCheckMessage(JAVADOC_MISSING),
            "38: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputScopeInnerInterfaces.java"), expected);
    }

    @Test
    public void testProtest() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "7: " + getCheckMessage(JAVADOC_MISSING),
            "29: " + getCheckMessage(JAVADOC_MISSING),
            "38: " + getCheckMessage(JAVADOC_MISSING),
            "65: " + getCheckMessage(JAVADOC_MISSING),
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
            "18: " + getCheckMessage(JAVADOC_MISSING),
            "20: " + getCheckMessage(JAVADOC_MISSING),
            "22: " + getCheckMessage(JAVADOC_MISSING),
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
            "18: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputScopeInnerClasses.java"), expected);
    }

    @Test
    public void testAuthorRequired() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("authorFormat", "\\S");
        final String[] expected = {
            "13: " + getCheckMessage(MISSING_TAG, "@author"),
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
            "22: " + getCheckMessage(MISSING_TAG, "@author"),
            "58: " + getCheckMessage(MISSING_TAG, "@author"),
            "94: " + getCheckMessage(MISSING_TAG, "@author"),
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
            "13: " + getCheckMessage(TAG_FORMAT, "@author", "ABC"),
            "22: " + getCheckMessage(MISSING_TAG, "@author"),
            "31: " + getCheckMessage(TAG_FORMAT, "@author", "ABC"),
            "49: " + getCheckMessage(TAG_FORMAT, "@author", "ABC"),
            "58: " + getCheckMessage(MISSING_TAG, "@author"),
            "67: " + getCheckMessage(TAG_FORMAT, "@author", "ABC"),
            "85: " + getCheckMessage(TAG_FORMAT, "@author", "ABC"),
            "94: " + getCheckMessage(MISSING_TAG, "@author"),
            "103: " + getCheckMessage(TAG_FORMAT, "@author", "ABC"),
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
            "13: " + getCheckMessage(MISSING_TAG, "@version"),
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
            "22: " + getCheckMessage(MISSING_TAG, "@version"),
            "58: " + getCheckMessage(MISSING_TAG, "@version"),
            "94: " + getCheckMessage(MISSING_TAG, "@version"),
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
            "13: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "22: " + getCheckMessage(MISSING_TAG, "@version"),
            "31: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "40: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "49: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "58: " + getCheckMessage(MISSING_TAG, "@version"),
            "67: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "76: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "85: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "94: " + getCheckMessage(MISSING_TAG, "@version"),
            "103: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
            "112: " + getCheckMessage(TAG_FORMAT, "@version", "\\$Revision.*\\$"),
        };
        verify(checkConfig, getPath("InputJavadoc.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(JAVADOC_MISSING),
            "15: " + getCheckMessage(JAVADOC_MISSING),
            "27: " + getCheckMessage(JAVADOC_MISSING),
            "39: " + getCheckMessage(JAVADOC_MISSING),
            "52: " + getCheckMessage(JAVADOC_MISSING),
            "63: " + getCheckMessage(JAVADOC_MISSING),
            "75: " + getCheckMessage(JAVADOC_MISSING),
            "87: " + getCheckMessage(JAVADOC_MISSING),
            "99: " + getCheckMessage(JAVADOC_MISSING),
            "111: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "3: " + getCheckMessage(JAVADOC_MISSING),
            "15: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("scope", Scope.PRIVATE.getName());
        checkConfig.addAttribute("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "27: " + getCheckMessage(JAVADOC_MISSING),
            "39: " + getCheckMessage(JAVADOC_MISSING),
            "52: " + getCheckMessage(JAVADOC_MISSING),
            "63: " + getCheckMessage(JAVADOC_MISSING),
            "75: " + getCheckMessage(JAVADOC_MISSING),
            "87: " + getCheckMessage(JAVADOC_MISSING),
            "99: " + getCheckMessage(JAVADOC_MISSING),
            "111: " + getCheckMessage(JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputNoJavadoc.java"),
               expected);
    }

    @Test
    public void testTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "7:4: " + getCheckMessage(UNUSED_TAG, "@param", "<D123>"),
            "11: " + getCheckMessage(MISSING_TAG, "@param <C456>"),
            "44:8: " + getCheckMessage(UNUSED_TAG, "@param", "<C>"),
            "47: " + getCheckMessage(MISSING_TAG, "@param <B>"),
        };
        verify(checkConfig, getPath("InputTypeParamsTags.java"), expected);
    }

    @Test
    public void testAllowMissingTypeParameters() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowMissingParamTags", "true");
        final String[] expected = {
            "7:4: " + getCheckMessage(UNUSED_TAG, "@param", "<D123>"),
            "44:8: " + getCheckMessage(UNUSED_TAG, "@param", "<C>"),
        };
        verify(checkConfig, getPath("InputTypeParamsTags.java"), expected);
    }

    @Test
    public void testBadTag() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        final String[] expected = {
            "5:4: " + getCheckMessage(UNKNOWN_TAG, "mytag"),
        };
        verify(checkConfig,
               getPath("javadoc" + File.separator + "InputBadTag.java"),
               expected);
    }

    @Test
    public void testBadTagSuppress() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocTypeCheck.class);
        checkConfig.addAttribute("allowUnknownTags", "true");
        final String[] expected = {
        };
        verify(checkConfig,
                getPath("javadoc" + File.separator + "InputBadTag.java"),
                expected);
    }
}
