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
            "11: " + getCheckMessage(MSG_NO_PERIOD),
            "36: " + getCheckMessage(MSG_NO_PERIOD),
            "44:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "47:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag"
                    + " shouldn't be here // violation"),
            "48:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "49:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "54:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "58: " + getCheckMessage(MSG_NO_PERIOD),
            "59:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "63: " + getCheckMessage(MSG_NO_PERIOD),
            "64:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "65: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "80:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img> // violation"),
            "149:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote> // violation"),
            "154: " + getCheckMessage(MSG_NO_PERIOD),
            "187: " + getCheckMessage(MSG_NO_PERIOD),
            "281:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "302:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "313: " + getCheckMessage(MSG_NO_PERIOD),
            "319:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p> // violation"),
            "323: " + getCheckMessage(MSG_NO_PERIOD),
            "327: " + getCheckMessage(MSG_NO_PERIOD),
            "334: " + getCheckMessage(MSG_NO_PERIOD),
            "346: " + getCheckMessage(MSG_NO_PERIOD),
            "359: " + getCheckMessage(MSG_NO_PERIOD),
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
            "13: " + getCheckMessage(MSG_NO_PERIOD),
            "38: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "60: " + getCheckMessage(MSG_NO_PERIOD),
            "65: " + getCheckMessage(MSG_NO_PERIOD),
            "156: " + getCheckMessage(MSG_NO_PERIOD),
            "189: " + getCheckMessage(MSG_NO_PERIOD),
            "315: " + getCheckMessage(MSG_NO_PERIOD),
            "325: " + getCheckMessage(MSG_NO_PERIOD),
            "329: " + getCheckMessage(MSG_NO_PERIOD),
            "336: " + getCheckMessage(MSG_NO_PERIOD),
            "348: " + getCheckMessage(MSG_NO_PERIOD),
            "361: " + getCheckMessage(MSG_NO_PERIOD),
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
            "14: " + getCheckMessage(MSG_NO_PERIOD),
            "24: " + getCheckMessage(MSG_NO_PERIOD),
            "29: " + getCheckMessage(MSG_NO_PERIOD),
            "39: " + getCheckMessage(MSG_NO_PERIOD),
            "56: " + getCheckMessage(MSG_NO_PERIOD),
            "61: " + getCheckMessage(MSG_NO_PERIOD),
            "66: " + getCheckMessage(MSG_NO_PERIOD),
            "157: " + getCheckMessage(MSG_NO_PERIOD),
            "190: " + getCheckMessage(MSG_NO_PERIOD),
            "316: " + getCheckMessage(MSG_NO_PERIOD),
            "326: " + getCheckMessage(MSG_NO_PERIOD),
            "330: " + getCheckMessage(MSG_NO_PERIOD),
            "337: " + getCheckMessage(MSG_NO_PERIOD),
            "349: " + getCheckMessage(MSG_NO_PERIOD),
            "362: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verify(checkConfig, getPath("InputJavadocStyle3.java"), expected);
    }

    @Test
    public void testHtml() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocStyleCheck.class);
        checkConfig.addAttribute("checkFirstSentence", "false");
        checkConfig.addAttribute("checkHtml", "true");
        final String[] expected = {
            "46:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "49:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag "
                    + "shouldn't be here // violation"),
            "50:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "51:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "56:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "61:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "66:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "67: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "82:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img> // violation"),
            "151:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote> // violation"),
            "283:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "304:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "321:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
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
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "68:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "69: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "167: " + getCheckMessage(MSG_EMPTY),
            "185: " + getCheckMessage(MSG_EMPTY),
            "190: " + getCheckMessage(MSG_NO_PERIOD),
            "284:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "326: " + getCheckMessage(MSG_NO_PERIOD),
            "330: " + getCheckMessage(MSG_NO_PERIOD),
            "362: " + getCheckMessage(MSG_NO_PERIOD),
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
            "57: " + getCheckMessage(MSG_NO_PERIOD),
            "58:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "68:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "69: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "168: " + getCheckMessage(MSG_EMPTY),
            "172: " + getCheckMessage(MSG_EMPTY),
            "186: " + getCheckMessage(MSG_EMPTY),
            "191: " + getCheckMessage(MSG_NO_PERIOD),
            "285:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "327: " + getCheckMessage(MSG_NO_PERIOD),
            "331: " + getCheckMessage(MSG_NO_PERIOD),
            "363: " + getCheckMessage(MSG_NO_PERIOD),
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
            "57: " + getCheckMessage(MSG_NO_PERIOD),
            "58:24: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "62: " + getCheckMessage(MSG_NO_PERIOD),
            "63:32: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "68:32: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation "
                    + "and line below, too"),
            "69: " + getCheckMessage(MSG_INCOMPLETE_TAG, "     * should fail <"),
            "167: " + getCheckMessage(MSG_EMPTY),
            "171: " + getCheckMessage(MSG_EMPTY),
            "176: " + getCheckMessage(MSG_EMPTY),
            "185: " + getCheckMessage(MSG_EMPTY),
            "190: " + getCheckMessage(MSG_NO_PERIOD),
            "284:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string> // violation"),
            "326: " + getCheckMessage(MSG_NO_PERIOD),
            "330: " + getCheckMessage(MSG_NO_PERIOD),
            "337: " + getCheckMessage(MSG_NO_PERIOD),
            "349: " + getCheckMessage(MSG_NO_PERIOD),
            "362: " + getCheckMessage(MSG_NO_PERIOD),
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
            "166: " + getCheckMessage(MSG_EMPTY),
            "170: " + getCheckMessage(MSG_EMPTY),
            "175: " + getCheckMessage(MSG_EMPTY),
            "180: " + getCheckMessage(MSG_EMPTY),
            "184: " + getCheckMessage(MSG_EMPTY),
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
            "13: " + getCheckMessage(MSG_NO_PERIOD),
            "38: " + getCheckMessage(MSG_NO_PERIOD),
            "46:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "49:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag "
                    + "shouldn't be here // violation"),
            "50:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "51:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "60: " + getCheckMessage(MSG_NO_PERIOD),
            "61:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>should fail // violation"),
            "82:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img> // violation"),
            "151:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote> // violation"),
            "156: " + getCheckMessage(MSG_NO_PERIOD),
            "304:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "315: " + getCheckMessage(MSG_NO_PERIOD),
            "321:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>Note:<b> it's unterminated tag.</p> // violation"),
            "336: " + getCheckMessage(MSG_NO_PERIOD),
            "348: " + getCheckMessage(MSG_NO_PERIOD),
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
            "68: " + getCheckMessage(MSG_NO_PERIOD),
            "331: " + getCheckMessage(MSG_NO_PERIOD),
            "363: " + getCheckMessage(MSG_NO_PERIOD),
        };
        verify(checkConfig, getPath("InputJavadocStyle10.java"), expected);
    }

    @Test
    public void testJavadocStyleRecordsAndCompactCtors() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocStyleCheck.class);
        final String[] expected = {

            "11: " + getCheckMessage(MSG_NO_PERIOD),
            "31: " + getCheckMessage(MSG_NO_PERIOD),
            "40:16: " + getCheckMessage(MSG_UNCLOSED_HTML,
                "<b>This guy is missing end of bold tag // violation"),
            "43:12: " + getCheckMessage(MSG_EXTRA_HTML, "</td>Extra tag "
                + "shouldn't be here // violation"),
            "44:54: " + getCheckMessage(MSG_EXTRA_HTML, "</style> // violation"),
            "46:24: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy. // violation"),
            "58: " + getCheckMessage(MSG_NO_PERIOD),
            "59:36: " + getCheckMessage(MSG_EXTRA_HTML, "</code> // violation and"
                + " line below, too"),
            "60: " + getCheckMessage(MSG_INCOMPLETE_TAG, "         * should fail <"),
            "75:37: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code> // violation"),
            "90: " + getCheckMessage(MSG_NO_PERIOD),
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
