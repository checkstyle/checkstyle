///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_EXTRA_HTML;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_INCOMPLETE_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_NO_PERIOD;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_UNCLOSED_HTML;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsOne()
            throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_NO_PERIOD),
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "62:11: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "65:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>"),
            "66:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style>"),
            "67:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy"),
            "73: " + getCheckMessage(MSG_NO_PERIOD),
            "74:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "80: " + getCheckMessage(MSG_NO_PERIOD),
            "81:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
            "89:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "90: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleDefaultSettingsOne.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsTwo()
            throws Exception {
        final String[] expected = {
            "26:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img>"),
            "72:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote>"),
            "77: " + getCheckMessage(MSG_NO_PERIOD),
            "112:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleDefaultSettingsTwo.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsThree()
            throws Exception {
        final String[] expected = {
            "109: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleDefaultSettingsThree.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsFour()
            throws Exception {
        final String[] expected = {
            "30:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>"),
            "42: " + getCheckMessage(MSG_NO_PERIOD),
            "49:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                    "<b>Note:<b> it's unterminated tag.</p>"),
            "54: " + getCheckMessage(MSG_NO_PERIOD),
            "59: " + getCheckMessage(MSG_NO_PERIOD),
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "80: " + getCheckMessage(MSG_NO_PERIOD),
            "94: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleDefaultSettingsFour.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsFive()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleDefaultSettingsFive.java"), expected);
    }

    @Test
    public void testJavadocStyleTrailingSpace()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleTrailingSpace.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceOne() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_NO_PERIOD),
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "68: " + getCheckMessage(MSG_NO_PERIOD),
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "80: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleFirstSentenceOne.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceTwo() throws Exception {
        final String[] expected = {
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "101: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceTwo.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceThree() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceThree.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceFour() throws Exception {
        final String[] expected = {
            "40: " + getCheckMessage(MSG_NO_PERIOD),
            "51: " + getCheckMessage(MSG_NO_PERIOD),
            "56: " + getCheckMessage(MSG_NO_PERIOD),
            "64: " + getCheckMessage(MSG_NO_PERIOD),
            "77: " + getCheckMessage(MSG_NO_PERIOD),
            "91: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceFour.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceFormatOne() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_NO_PERIOD),
            "35: " + getCheckMessage(MSG_NO_PERIOD),
            "41: " + getCheckMessage(MSG_NO_PERIOD),
            "52: " + getCheckMessage(MSG_NO_PERIOD),
            "70: " + getCheckMessage(MSG_NO_PERIOD),
            "76: " + getCheckMessage(MSG_NO_PERIOD),
            "82: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleFirstSentenceFormatOne.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceFormatTwo() throws Exception {
        final String[] expected = {
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "108: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceFormatTwo.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceFormatThree() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceFormatThree.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceFormatFour() throws Exception {
        final String[] expected = {
            "40: " + getCheckMessage(MSG_NO_PERIOD),
            "51: " + getCheckMessage(MSG_NO_PERIOD),
            "56: " + getCheckMessage(MSG_NO_PERIOD),
            "64: " + getCheckMessage(MSG_NO_PERIOD),
            "77: " + getCheckMessage(MSG_NO_PERIOD),
            "91: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceFormatFour.java"), expected);
    }

    @Test
    public void testHtml1() throws Exception {
        final String[] expected = {
            "59:11: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "62:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>"),
            "63:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style>"),
            "64:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy"),
            "70:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "76:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "83:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "84: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
            "99:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img>"),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleHtml1.java"), expected);
    }

    @Test
    public void testHtml2() throws Exception {
        final String[] expected = {
            "68:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote>"),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleHtml2.java"), expected);
    }

    @Test
    public void testHtml3() throws Exception {
        final String[] expected = {
            "103:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleHtml3.java"), expected);
    }

    @Test
    public void testHtml4() throws Exception {
        final String[] expected = {
            "29:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>"),
            "47:11: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleHtml4.java"), expected);
    }

    @Test
    public void testHtmlComment() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleHtmlComment.java"), expected);
    }

    @Test
    public void testOnInputWithNoJavadoc1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleNoJavadoc1.java"), expected);
    }

    @Test
    public void testOnInputWithNoJavadoc2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleNoJavadoc2.java"), expected);
    }

    @Test
    public void testScopePublic1()
            throws Exception {
        final String[] expected = {
            "78: " + getCheckMessage(MSG_NO_PERIOD),
            "79:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "80: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePublic1.java"), expected);
    }

    @Test
    public void testScopePublic2()
            throws Exception {
        final String[] expected = {
            "83: " + getCheckMessage(MSG_EMPTY),
            "102: " + getCheckMessage(MSG_EMPTY),
            "108: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePublic2.java"), expected);
    }

    @Test
    public void testScopePublic3()
            throws Exception {
        final String[] expected = {
            "104:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePublic3.java"), expected);
    }

    @Test
    public void testScopePublic4()
            throws Exception {
        final String[] expected = {
            "51: " + getCheckMessage(MSG_NO_PERIOD),
            "56: " + getCheckMessage(MSG_NO_PERIOD),
            "89: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePublic4.java"), expected);
    }

    @Test
    public void testScopeProtected1()
            throws Exception {
        final String[] expected = {
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "68:23: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "80: " + getCheckMessage(MSG_NO_PERIOD),
            "81:31: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "82: " + getCheckMessage(MSG_INCOMPLETE_TAG, "    * should fail <"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopeProtected1.java"), expected);
    }

    @Test
    public void testScopeProtected2()
            throws Exception {
        final String[] expected = {
            "83: " + getCheckMessage(MSG_EMPTY),
            "87: " + getCheckMessage(MSG_EMPTY),
            "102: " + getCheckMessage(MSG_EMPTY),
            "108: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopeProtected2.java"), expected);
    }

    @Test
    public void testScopeProtected3()
            throws Exception {
        final String[] expected = {
            "104:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopeProtected3.java"), expected);
    }

    @Test
    public void testScopeProtected4()
            throws Exception {
        final String[] expected = {
            "51: " + getCheckMessage(MSG_NO_PERIOD),
            "56: " + getCheckMessage(MSG_NO_PERIOD),
            "89: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopeProtected4.java"), expected);
    }

    @Test
    public void testScopePackage1()
            throws Exception {
        final String[] expected = {
            "67: " + getCheckMessage(MSG_NO_PERIOD),
            "68:24: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "74: " + getCheckMessage(MSG_NO_PERIOD),
            "75:32: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "82: " + getCheckMessage(MSG_NO_PERIOD),
            "83:32: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "84: " + getCheckMessage(MSG_INCOMPLETE_TAG, "     * should fail <"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePackage1.java"), expected);
    }

    @Test
    public void testScopePackage2()
            throws Exception {
        final String[] expected = {
            "83: " + getCheckMessage(MSG_EMPTY),
            "87: " + getCheckMessage(MSG_EMPTY),
            "92: " + getCheckMessage(MSG_EMPTY),
            "102: " + getCheckMessage(MSG_EMPTY),
            "108: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePackage2.java"), expected);
    }

    @Test
    public void testScopePackage3()
            throws Exception {
        final String[] expected = {
            "104:21: " + getCheckMessage(MSG_EXTRA_HTML, "</string>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePackage3.java"), expected);
    }

    @Test
    public void testScopePackage4()
            throws Exception {
        final String[] expected = {
            "51: " + getCheckMessage(MSG_NO_PERIOD),
            "56: " + getCheckMessage(MSG_NO_PERIOD),
            "64: " + getCheckMessage(MSG_NO_PERIOD),
            "77: " + getCheckMessage(MSG_NO_PERIOD),
            "91: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePackage4.java"), expected);
    }

    @Test
    public void testEmptyJavadoc1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleEmptyJavadoc1.java"), expected);
    }

    @Test
    public void testEmptyJavadoc2() throws Exception {
        final String[] expected = {
            "75: " + getCheckMessage(MSG_EMPTY),
            "79: " + getCheckMessage(MSG_EMPTY),
            "84: " + getCheckMessage(MSG_EMPTY),
            "90: " + getCheckMessage(MSG_EMPTY),
            "95: " + getCheckMessage(MSG_EMPTY),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleEmptyJavadoc2.java"), expected);
    }

    @Test
    public void testEmptyJavadoc3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleEmptyJavadoc3.java"), expected);
    }

    @Test
    public void testEmptyJavadoc4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleEmptyJavadoc4.java"), expected);
    }

    @Test
    public void testExcludeScope1()
            throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_NO_PERIOD),
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "62:11: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "65:7: " + getCheckMessage(MSG_EXTRA_HTML, "</td>"),
            "66:49: " + getCheckMessage(MSG_EXTRA_HTML, "</style>"),
            "67:19: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy"),
            "78: " + getCheckMessage(MSG_NO_PERIOD),
            "79:31: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "100:39: " + getCheckMessage(MSG_EXTRA_HTML, "</img>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleExcludeScope1.java"), expected);
    }

    @Test
    public void testExcludeScope2()
            throws Exception {
        final String[] expected = {
            "69:8: " + getCheckMessage(MSG_UNCLOSED_HTML, "<blockquote>"),
            "75: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleExcludeScope2.java"), expected);
    }

    @Test
    public void testExcludeScope3()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleExcludeScope3.java"), expected);
    }

    @Test
    public void testExcludeScope4()
            throws Exception {
        final String[] expected = {
            "30:33: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>"),
            "42: " + getCheckMessage(MSG_NO_PERIOD),
            "49:11: " + getCheckMessage(MSG_UNCLOSED_HTML,
                    "<b>Note:<b> it's unterminated tag.</p>"),
            "65: " + getCheckMessage(MSG_NO_PERIOD),
            "78: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleExcludeScope4.java"), expected);
    }

    @Test
    public void packageInfoInheritDoc() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("pkginfo" + File.separator + "invalidinherit" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoInvalid() throws Exception {
        final String[] expected = {
            "17: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("pkginfo" + File.separator + "invalidformat" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("pkginfo" + File.separator + "annotation" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoMissing() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("bothfiles" + File.separator + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoMissingPeriod() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("missingperiod" + File.separator + "package-info.java"),
               expected);
    }

    @Test
    public void testNothing() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleNothing.java"),
               expected);
    }

    @Test
    public void packageInfoValid() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
               getPath("pkginfo" + File.separator + "valid"
                       + File.separator + "package-info.java"),
               expected);
    }

    @Test
    public void testRestrictedTokenSet1()
            throws Exception {
        final String[] expected = {
            "74: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleRestrictedTokenSet1.java"), expected);
    }

    @Test
    public void testRestrictedTokenSet2()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleRestrictedTokenSet2.java"), expected);
    }

    @Test
    public void testRestrictedTokenSet3()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleRestrictedTokenSet3.java"), expected);
    }

    @Test
    public void testRestrictedTokenSet4()
            throws Exception {
        final String[] expected = {
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "86: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleRestrictedTokenSet4.java"), expected);
    }

    @Test
    public void testJavadocStyleRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_NO_PERIOD),
            "45: " + getCheckMessage(MSG_NO_PERIOD),
            "58:16: " + getCheckMessage(MSG_UNCLOSED_HTML, "<b>"),
            "61:12: " + getCheckMessage(MSG_EXTRA_HTML, "</td>"),
            "62:54: " + getCheckMessage(MSG_EXTRA_HTML, "</style>"),
            "64:24: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>dummy"),
            "79: " + getCheckMessage(MSG_NO_PERIOD),
            "80:36: " + getCheckMessage(MSG_EXTRA_HTML, "</code>"),
            "81: " + getCheckMessage(MSG_INCOMPLETE_TAG, "         * should fail <"),
            "97:37: " + getCheckMessage(MSG_UNCLOSED_HTML, "<code>"),
            "113: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocStyleRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testHtmlTagToString() {
        final HtmlTag tag = new HtmlTag("id", 3, 5, true, false, "<a href=\"URL\"/>");
        assertWithMessage("Invalid toString result")
            .that(tag.toString())
            .isEqualTo("HtmlTag[id='id', lineNo=3, position=5, text='<a href=\"URL\"/>', "
                + "closedTag=true, incompleteTag=false]");
    }

    @Test
    public void testNeverEndingXmlCommentInsideJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleNeverEndingXmlComment.java"), expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic()
            throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_EMPTY),
            "25: " + getCheckMessage(MSG_EMPTY),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleInterfaceMemberScopeIsPublic.java"),
                expected);
    }

    @Test
    public void testEnumCtorScopeIsPrivate()
            throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_EMPTY),
            "25: " + getCheckMessage(MSG_EMPTY),
            "34: " + getCheckMessage(MSG_EMPTY),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleEnumCtorScopeIsPrivate.java"),
                expected);
    }

    @Test
    public void testLowerCasePropertyForTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheckOptionLowercaseProperty.java"), expected);
    }

    @Test
    public void testJavadocTag() throws Exception {
        final String[] expected = {
            "11: " + getCheckMessage(MSG_NO_PERIOD),
            "15: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleDefault4.java"),
                expected);
    }

    @Test
    public void testJavadocTag2() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_NO_PERIOD),
            "18:16: " + getCheckMessage(MSG_UNCLOSED_HTML,
                    "<AREA ALT=\"alt\" Coordination=\"100,0,200,50\" HREF=\"/href/\"> <"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheck1.java"),
                expected);
    }

    @Test
    public void testJavadocTag3() throws Exception {
        final String[] expected = {
            "21:4: " + getCheckMessage(MSG_EXTRA_HTML, "</body>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheck2.java"),
                expected);
    }

    @Test
    public void testJavadocStyleCheck3() throws Exception {
        final String[] expected = {
            "11: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheck3.java"),
                expected);
    }

    @Test
    public void testJavadocStyleCheck4() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheck5.java"),
                expected);
    }

    @Test
    public void testJavadocStyleAboveComments() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_NO_PERIOD),
            "20: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleAboveComments.java"),
                expected);
    }
}
