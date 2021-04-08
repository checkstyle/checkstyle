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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_EXTRA_HTML;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_INCOMPLETE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_NO_PERIOD;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_UNCLOSED_HTML;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocStyleCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle";
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testDefaultSettings()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "30: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "63:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "66:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag"
                    + " shouldn't be here // violation"),
            "67:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "68:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "72: " + getCheckMessage(MSG_NO_PERIOD),
            "73:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "77: " + getCheckMessage(MSG_NO_PERIOD),
            "78:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "82: " + getCheckMessage(MSG_NO_PERIOD),
            "83:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "84: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "99:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img> // violation"),
            "168:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote> // violation"),
            "173: " + getCheckMessage(MSG_NO_PERIOD),
            "206: " + getCheckMessage(MSG_NO_PERIOD),
            "300:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "321:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "332: " + getCheckMessage(MSG_NO_PERIOD),
            "338:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p> // violation"),
            "342: " + getCheckMessage(MSG_NO_PERIOD),
            "346: " + getCheckMessage(MSG_NO_PERIOD),
            "353: " + getCheckMessage(MSG_NO_PERIOD),
            "365: " + getCheckMessage(MSG_NO_PERIOD),
            "378: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle1.java"), expected);
    }

    @Test
    public void testFirstSentence() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "false");
        final String[] expected = {
            "17: " + getCheckMessage(MSG_NO_PERIOD),
            "42: " + getCheckMessage(MSG_NO_PERIOD),
            "59: " + getCheckMessage(MSG_NO_PERIOD),
            "64: " + getCheckMessage(MSG_NO_PERIOD),
            "69: " + getCheckMessage(MSG_NO_PERIOD),
            "160: " + getCheckMessage(MSG_NO_PERIOD),
            "193: " + getCheckMessage(MSG_NO_PERIOD),
            "319: " + getCheckMessage(MSG_NO_PERIOD),
            "329: " + getCheckMessage(MSG_NO_PERIOD),
            "333: " + getCheckMessage(MSG_NO_PERIOD),
            "340: " + getCheckMessage(MSG_NO_PERIOD),
            "352: " + getCheckMessage(MSG_NO_PERIOD),
            "365: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle2.java"), expected);
    }

    @Test
    public void testFirstSentenceFormat() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "false");
        checkConfig.addAttribute("endOfSentenceFormat",
                "([.][ \t\n\r\f<])|([.]$)");
        final String[] expected = {
            "17: " + getCheckMessage(MSG_NO_PERIOD),
            "27: " + getCheckMessage(MSG_NO_PERIOD),
            "32: " + getCheckMessage(MSG_NO_PERIOD),
            "42: " + getCheckMessage(MSG_NO_PERIOD),
            "59: " + getCheckMessage(MSG_NO_PERIOD),
            "64: " + getCheckMessage(MSG_NO_PERIOD),
            "69: " + getCheckMessage(MSG_NO_PERIOD),
            "160: " + getCheckMessage(MSG_NO_PERIOD),
            "193: " + getCheckMessage(MSG_NO_PERIOD),
            "319: " + getCheckMessage(MSG_NO_PERIOD),
            "329: " + getCheckMessage(MSG_NO_PERIOD),
            "333: " + getCheckMessage(MSG_NO_PERIOD),
            "340: " + getCheckMessage(MSG_NO_PERIOD),
            "352: " + getCheckMessage(MSG_NO_PERIOD),
            "365: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle3.java"), expected);
    }

    @Test
    public void testHtml() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "true");
        final String[] expected = {
            "50:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "53:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag "
                    + "shouldn't be here // violation"),
            "54:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "55:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "60:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "65:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "70:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "71: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "86:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img> // violation"),
            "155:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote> // violation"),
            "287:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "308:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "325:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p> // violation"),
        };

        verify(checkConfig, getPath("InputJavadocStyle4.java"), expected);
    }

    @Test
    public void testHtmlComment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocStyleHtmlComment.java"), expected);
    }

    @Test
    public void testOnInputWithNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocStyleNoJavadoc.java"), expected);
    }

    @Test
    public void testScopePublic()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "public");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_NO_PERIOD),
            "70:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "71: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "169: " + getCheckMessage(MSG_EMPTY),
            "187: " + getCheckMessage(MSG_EMPTY),
            "192: " + getCheckMessage(MSG_NO_PERIOD),
            "286:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "328: " + getCheckMessage(MSG_NO_PERIOD),
            "332: " + getCheckMessage(MSG_NO_PERIOD),
            "364: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle5.java"), expected);
    }

    @Test
    public void testScopeProtected()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "protected");
        final String[] expected = {
            "59: " + getCheckMessage(MSG_NO_PERIOD),
            "60:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "69: " + getCheckMessage(MSG_NO_PERIOD),
            "70:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "71: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "170: " + getCheckMessage(MSG_EMPTY),
            "174: " + getCheckMessage(MSG_EMPTY),
            "188: " + getCheckMessage(MSG_EMPTY),
            "193: " + getCheckMessage(MSG_NO_PERIOD),
            "287:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "329: " + getCheckMessage(MSG_NO_PERIOD),
            "333: " + getCheckMessage(MSG_NO_PERIOD),
            "365: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle6.java"), expected);
    }

    @Test
    public void testScopePackage()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkHtml", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        checkConfig.addAttribute("scope", "package");
        final String[] expected = {
            "59: " + getCheckMessage(MSG_NO_PERIOD),
            "60:24: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "64: " + getCheckMessage(MSG_NO_PERIOD),
            "65:32: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "69: " + getCheckMessage(MSG_NO_PERIOD),
            "70:32: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "71: " + getCheckMessage(MSG_INCOMPLETE_TAG, "     * should fail <"),
            "169: " + getCheckMessage(MSG_EMPTY),
            "173: " + getCheckMessage(MSG_EMPTY),
            "178: " + getCheckMessage(MSG_EMPTY),
            "187: " + getCheckMessage(MSG_EMPTY),
            "192: " + getCheckMessage(MSG_NO_PERIOD),
            "286:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "328: " + getCheckMessage(MSG_NO_PERIOD),
            "332: " + getCheckMessage(MSG_NO_PERIOD),
            "339: " + getCheckMessage(MSG_NO_PERIOD),
            "351: " + getCheckMessage(MSG_NO_PERIOD),
            "364: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle7.java"), expected);
    }

    @Test
    public void testEmptyJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "false");
        checkConfig.addAttribute("checkEmptyJavadoc", "true");
        final String[] expected = {
            "169: " + getCheckMessage(MSG_EMPTY),
            "173: " + getCheckMessage(MSG_EMPTY),
            "178: " + getCheckMessage(MSG_EMPTY),
            "183: " + getCheckMessage(MSG_EMPTY),
            "187: " + getCheckMessage(MSG_EMPTY),
        };

        verify(checkConfig, getPath("InputJavadocStyle8.java"), expected);
    }

    @Test
    public void testExcludeScope()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("scope", "private");
        checkConfig.addAttribute("excludeScope", "protected");
        final String[] expected = {
            "17: " + getCheckMessage(MSG_NO_PERIOD),
            "42: " + getCheckMessage(MSG_NO_PERIOD),
            "50:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "53:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag "
                    + "shouldn't be here // violation"),
            "54:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "55:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "64: " + getCheckMessage(MSG_NO_PERIOD),
            "65:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "86:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img> // violation"),
            "155:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote> // violation"),
            "160: " + getCheckMessage(MSG_NO_PERIOD),
            "308:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "319: " + getCheckMessage(MSG_NO_PERIOD),
            "325:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p> // violation"),
            "340: " + getCheckMessage(MSG_NO_PERIOD),
            "352: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle9.java"), expected);
    }

    @Test
    public void packageInfoInheritDoc() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig,
               getPath("pkginfo" + File.separator + "invalidinherit" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoInvalid() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig,
               getPath("pkginfo" + File.separator + "invalidformat" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoAnnotation() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
               getPath("pkginfo" + File.separator + "annotation" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoMissing() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };

        verify(checkConfig,
               getPath("bothfiles" + File.separator + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoValid() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
               getPath("pkginfo" + File.separator + "valid" + File.separator + "package-info.java"),
               expected);
    }

    @Test
    public void testRestrictedTokenSet()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_DEF");
        checkConfig.addAttribute("scope", "public");
        checkConfig.addAttribute("checkFirstSentence", "true");
        checkConfig.addAttribute("checkEmptyJavadoc", "false");
        checkConfig.addAttribute("checkHtml", "false");
        final String[] expected = {
            "70: " + getCheckMessage(MSG_NO_PERIOD),
            "333: " + getCheckMessage(MSG_NO_PERIOD),
            "365: " + getCheckMessage(MSG_NO_PERIOD),
        };
        verify(checkConfig, getPath("InputJavadocStyle10.java"), expected);
    }

    @Test
    public void testJavadocStyleRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = {

            "19: " + getCheckMessage(MSG_NO_PERIOD),
            "39: " + getCheckMessage(MSG_NO_PERIOD),
            "49:16: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "52:12: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag "
                + "shouldn't be here // violation"),
            "53:54: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "55:24: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "69:36: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation and"
                + " line below, too"),
            "70: " + getCheckMessage(MSG_INCOMPLETE_TAG, "         * should fail <"),
            "85:37: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "100: " + getCheckMessage(MSG_NO_PERIOD),
            };

        verify(checkConfig,
            getNonCompilablePath("InputJavadocStyleRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testHtmlTagToString() {
        final HtmlTag tag = new HtmlTag("id", 3, 5, true, false, "<a href=\"URL\"/>");
        assertEquals("HtmlTag[id='id', lineNo=3, position=5, text='<a href=\"URL\"/>', "
                + "closedTag=true, incompleteTag=false]", tag.toString(),
                "Invalid toString result");
    }

    @Test
    public void testNeverEndingXmlCommentInsideJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocStyleNeverEndingXmlComment.java"), expected);
    }

}
