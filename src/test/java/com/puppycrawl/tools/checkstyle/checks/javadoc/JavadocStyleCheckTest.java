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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_EXTRA_HTML;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_INCOMPLETE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_NO_PERIOD;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_UNCLOSED_HTML;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class JavadocStyleCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "javadoc" + File.separator + filename);
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocStyleCheck javadocStyleCheck = new JavadocStyleCheck();

        final int[] actual = javadocStyleCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.VARIABLE_DEF,
        };

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testDefaultSettings()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "20: " + getCheckMessage(MSG_NO_PERIOD),
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "63:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag"),
            "66:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag shouldn't be here"),
            "68:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy."),
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "75:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "81: " + getCheckMessage(MSG_NO_PERIOD),
            "82:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "89:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "90: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "109:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img>"),
            "186:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote>"),
            "193: " + getCheckMessage(MSG_NO_PERIOD),
            "238: " + getCheckMessage(MSG_NO_PERIOD),
            "335:33: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
            "361:37: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>"),
            "372: " + getCheckMessage(MSG_NO_PERIOD),
            "378:15: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p>"),
            "382: " + getCheckMessage(MSG_NO_PERIOD),
            "386: " + getCheckMessage(MSG_NO_PERIOD),
            "393: " + getCheckMessage(MSG_NO_PERIOD),
            "405: " + getCheckMessage(MSG_NO_PERIOD),
            "418: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testFirstSentence() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "false");
        final String[] expected = {
            "20: " + getCheckMessage(MSG_NO_PERIOD),
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "81: " + getCheckMessage(MSG_NO_PERIOD),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "193: " + getCheckMessage(MSG_NO_PERIOD),
            "238: " + getCheckMessage(MSG_NO_PERIOD),
            "372: " + getCheckMessage(MSG_NO_PERIOD),
            "382: " + getCheckMessage(MSG_NO_PERIOD),
            "386: " + getCheckMessage(MSG_NO_PERIOD),
            "393: " + getCheckMessage(MSG_NO_PERIOD),
            "405: " + getCheckMessage(MSG_NO_PERIOD),
            "418: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testFirstSentenceFormat() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "false");
        checkConfig.addAttribute("endOfSentenceFormat",
                "([.][ \t\n\r\f<])|([.]$)");
        final String[] expected = {
            "20: " + getCheckMessage(MSG_NO_PERIOD),
            "32: " + getCheckMessage(MSG_NO_PERIOD),
            "39: " + getCheckMessage(MSG_NO_PERIOD),
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "81: " + getCheckMessage(MSG_NO_PERIOD),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "193: " + getCheckMessage(MSG_NO_PERIOD),
            "238: " + getCheckMessage(MSG_NO_PERIOD),
            "372: " + getCheckMessage(MSG_NO_PERIOD),
            "382: " + getCheckMessage(MSG_NO_PERIOD),
            "386: " + getCheckMessage(MSG_NO_PERIOD),
            "393: " + getCheckMessage(MSG_NO_PERIOD),
            "405: " + getCheckMessage(MSG_NO_PERIOD),
            "418: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testHtml() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "true");
        final String[] expected = {
            "63:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag"),
            "66:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag shouldn't be here"),
            "68:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy."),
            "75:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "82:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "89:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "90: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "109:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img>"),
            "186:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote>"),
            "335:33: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
            "361:37: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>"),
            "378:15: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p>"),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testHtmlComment() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocStyleHtmlComment.java"), expected);
    }

    @Test
    public void testOnInputWithNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputNoJavadoc.java"), expected);
    }

    @Test
    public void testScopePublic()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "public");
        final String[] expected = {
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "89:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "90: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "205: " + getCheckMessage(MSG_EMPTY),
            "230: " + getCheckMessage(MSG_EMPTY),
            "238: " + getCheckMessage(MSG_NO_PERIOD),
            "335:33: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
            "382: " + getCheckMessage(MSG_NO_PERIOD),
            "386: " + getCheckMessage(MSG_NO_PERIOD),
            "418: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testScopeProtected()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "protected");
        final String[] expected = {
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "75:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "89:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "90: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "205: " + getCheckMessage(MSG_EMPTY),
            "211: " + getCheckMessage(MSG_EMPTY),
            "230: " + getCheckMessage(MSG_EMPTY),
            "238: " + getCheckMessage(MSG_NO_PERIOD),
            "335:33: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
            "382: " + getCheckMessage(MSG_NO_PERIOD),
            "386: " + getCheckMessage(MSG_NO_PERIOD),
            "418: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testScopePackage()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "package");
        final String[] expected = {
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "75:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "81: " + getCheckMessage(MSG_NO_PERIOD),
            "82:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "89:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "90: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "205: " + getCheckMessage(MSG_EMPTY),
            "211: " + getCheckMessage(MSG_EMPTY),
            "218: " + getCheckMessage(MSG_EMPTY),
            "230: " + getCheckMessage(MSG_EMPTY),
            "238: " + getCheckMessage(MSG_NO_PERIOD),
            "335:33: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
            "382: " + getCheckMessage(MSG_NO_PERIOD),
            "386: " + getCheckMessage(MSG_NO_PERIOD),
            "393: " + getCheckMessage(MSG_NO_PERIOD),
            "405: " + getCheckMessage(MSG_NO_PERIOD),
            "418: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "false");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        final String[] expected = {
            "205: " + getCheckMessage(MSG_EMPTY),
            "211: " + getCheckMessage(MSG_EMPTY),
            "218: " + getCheckMessage(MSG_EMPTY),
            "225: " + getCheckMessage(MSG_EMPTY),
            "230: " + getCheckMessage(MSG_EMPTY),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void testExcludeScope()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("excludeScope", "protected");
        final String[] expected = {
            "20: " + getCheckMessage(MSG_NO_PERIOD),
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "63:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag"),
            "66:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag shouldn't be here"),
            "68:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy."),
            "81: " + getCheckMessage(MSG_NO_PERIOD),
            "82:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail"),
            "109:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img>"),
            "186:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote>"),
            "193: " + getCheckMessage(MSG_NO_PERIOD),
            "361:37: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>"),
            "372: " + getCheckMessage(MSG_NO_PERIOD),
            "378:15: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p>"),
            "393: " + getCheckMessage(MSG_NO_PERIOD),
            "405: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }

    @Test
    public void packageInfoInheritDoc() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(createChecker(checkConfig),
               getPath("pkginfo" + File.separator + "invalidinherit" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoInvalid() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(createChecker(checkConfig),
               getPath("pkginfo" + File.separator + "invalidformat" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoAnnotation() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(createChecker(checkConfig),
               getPath("pkginfo" + File.separator + "annotation" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoMissing() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verify(createChecker(checkConfig),
               getPath("bothfiles" + File.separator + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoValid() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(JavadocStyleCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(createChecker(checkConfig),
               getPath("pkginfo" + File.separator + "valid" + File.separator + "package-info.java"),
               expected);
    }

    @Test
    public void testRestrictedTokenSet()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_DEF");
        checkConfig.addAttribute("scope", "public");
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "false");
        checkConfig.addAttribute("checkHtml", "false");
        final String[] expected = {
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "386: " + getCheckMessage(MSG_NO_PERIOD),
            "418: " + getCheckMessage(MSG_NO_PERIOD),
        };
        verify(checkConfig, getPath("InputJavadocStyle.java"), expected);
    }
}
