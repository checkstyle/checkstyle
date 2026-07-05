///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_KEY_UNCLOSED_HTML_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck.MSG_NO_PERIOD;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocStyleCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
    public void testNonTightHtmlViolation() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_KEY_UNCLOSED_HTML_TAG, "p"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleNonTightHtml.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsOne()
            throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_NO_PERIOD),
            "49: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "61: " + getCheckMessage(MSG_NO_PERIOD),
            "67: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleDefaultSettingsOne.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsTwo()
            throws Exception {
        final String[] expected = {
            "59: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleDefaultSettingsTwo.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsThree()
            throws Exception {
        final String[] expected = {
            "108: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleDefaultSettingsThree.java"), expected);
    }

    @Test
    public void testJavadocStyleDefaultSettingsFour()
            throws Exception {
        final String[] expected = {
            "39: " + getCheckMessage(MSG_NO_PERIOD),
            "44: " + getCheckMessage(MSG_NO_PERIOD),
            "52: " + getCheckMessage(MSG_NO_PERIOD),
            "65: " + getCheckMessage(MSG_NO_PERIOD),
            "79: " + getCheckMessage(MSG_NO_PERIOD),
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
        final String[] expected = {
            "19: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleTrailingSpace.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceOne() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_NO_PERIOD),
            "49: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "61: " + getCheckMessage(MSG_NO_PERIOD),
            "67: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleFirstSentenceOne.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceTwo() throws Exception {
        final String[] expected = {
            "66: " + getCheckMessage(MSG_NO_PERIOD),
            "100: " + getCheckMessage(MSG_NO_PERIOD),
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
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "63: " + getCheckMessage(MSG_NO_PERIOD),
            "76: " + getCheckMessage(MSG_NO_PERIOD),
            "90: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceFour.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceFormatOne() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_NO_PERIOD),
            "34: " + getCheckMessage(MSG_NO_PERIOD),
            "40: " + getCheckMessage(MSG_NO_PERIOD),
            "51: " + getCheckMessage(MSG_NO_PERIOD),
            "57: " + getCheckMessage(MSG_NO_PERIOD),
            "63: " + getCheckMessage(MSG_NO_PERIOD),
            "69: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleFirstSentenceFormatOne.java"), expected);
    }

    @Test
    public void testJavadocStyleFirstSentenceFormatTwo() throws Exception {
        final String[] expected = {
            "73: " + getCheckMessage(MSG_NO_PERIOD),
            "107: " + getCheckMessage(MSG_NO_PERIOD),
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
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "63: " + getCheckMessage(MSG_NO_PERIOD),
            "76: " + getCheckMessage(MSG_NO_PERIOD),
            "90: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleFirstSentenceFormatFour.java"), expected);
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
            "53: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePublic1.java"), expected);
    }

    @Test
    public void testScopePublic2()
            throws Exception {
        final String[] expected = {
            "82: " + getCheckMessage(MSG_EMPTY),
            "101: " + getCheckMessage(MSG_EMPTY),
            "107: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePublic2.java"), expected);
    }

    @Test
    public void testScopePublic4()
            throws Exception {
        final String[] expected = {
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePublic4.java"), expected);
    }

    @Test
    public void testScopeProtected1()
            throws Exception {
        final String[] expected = {
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "59: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopeProtected1.java"), expected);
    }

    @Test
    public void testScopeProtected2()
            throws Exception {
        final String[] expected = {
            "82: " + getCheckMessage(MSG_EMPTY),
            "86: " + getCheckMessage(MSG_EMPTY),
            "101: " + getCheckMessage(MSG_EMPTY),
            "107: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopeProtected2.java"), expected);
    }

    @Test
    public void testScopeProtected4()
            throws Exception {
        final String[] expected = {
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "88: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopeProtected4.java"), expected);
    }

    @Test
    public void testScopePackage1()
            throws Exception {
        final String[] expected = {
            "53: " + getCheckMessage(MSG_NO_PERIOD),
            "59: " + getCheckMessage(MSG_NO_PERIOD),
            "65: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePackage1.java"), expected);
    }

    @Test
    public void testScopePackage2()
            throws Exception {
        final String[] expected = {
            "82: " + getCheckMessage(MSG_EMPTY),
            "86: " + getCheckMessage(MSG_EMPTY),
            "91: " + getCheckMessage(MSG_EMPTY),
            "101: " + getCheckMessage(MSG_EMPTY),
            "107: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleScopePackage2.java"), expected);
    }

    @Test
    public void testScopePackage4()
            throws Exception {
        final String[] expected = {
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "55: " + getCheckMessage(MSG_NO_PERIOD),
            "63: " + getCheckMessage(MSG_NO_PERIOD),
            "76: " + getCheckMessage(MSG_NO_PERIOD),
            "90: " + getCheckMessage(MSG_NO_PERIOD),
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
            "74: " + getCheckMessage(MSG_EMPTY),
            "78: " + getCheckMessage(MSG_EMPTY),
            "83: " + getCheckMessage(MSG_EMPTY),
            "89: " + getCheckMessage(MSG_EMPTY),
            "94: " + getCheckMessage(MSG_EMPTY),
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
        final String[] expected = {
            "39: " + getCheckMessage(MSG_EMPTY),
            "110: " + getCheckMessage(MSG_EMPTY),
        };

        verifyWithInlineConfigParser(
            getPath("InputJavadocStyleEmptyJavadoc4.java"), expected);
    }

    @Test
    public void testExcludeScope1()
            throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_NO_PERIOD),
            "49: " + getCheckMessage(MSG_NO_PERIOD),
            "60: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleExcludeScope1.java"), expected);
    }

    @Test
    public void testExcludeScope2()
            throws Exception {
        final String[] expected = {
            "66: " + getCheckMessage(MSG_NO_PERIOD),
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
            "50: " + getCheckMessage(MSG_NO_PERIOD),
            "63: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleExcludeScope4.java"), expected);
    }

    @Test
    public void packageInfoInheritDoc() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("pkginfo" + File.separator + "invalidinherit" + File.separator
                   + "package-info.java"),
               expected);
    }

    @Test
    public void packageInfoInvalid() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_NO_PERIOD),
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
            "15: " + getCheckMessage(MSG_NO_PERIOD),
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
            "61: " + getCheckMessage(MSG_NO_PERIOD),
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
            "52: " + getCheckMessage(MSG_NO_PERIOD),
            "85: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleRestrictedTokenSet4.java"), expected);
    }

    @Test
    public void testJavadocStyleRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_NO_PERIOD),
            "44: " + getCheckMessage(MSG_NO_PERIOD),
            "59: " + getCheckMessage(MSG_NO_PERIOD),
            "83: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleRecordsAndCompactCtors.java"),
            expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic()
            throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_EMPTY),
            "24: " + getCheckMessage(MSG_EMPTY),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleInterfaceMemberScopeIsPublic.java"),
                expected);
    }

    @Test
    public void testEnumCtorScopeIsPrivate()
            throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_EMPTY),
            "24: " + getCheckMessage(MSG_EMPTY),
            "33: " + getCheckMessage(MSG_EMPTY),
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
            "19: " + getCheckMessage(MSG_NO_PERIOD),
            "23: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleDefault4.java"),
                expected);
    }

    @Test
    public void testJavadocTag2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheck1.java"),
                expected);
    }

    @Test
    public void testJavadocStyleCheck3() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheck3.java"),
                expected);
    }

    @Test
    public void testJavadocStyleCheck4() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleCheck5.java"),
                expected);
    }

    @Test
    public void testJavadocStyleAboveComments() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_NO_PERIOD),
            "27: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocStyleAboveComments.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_NO_PERIOD),
            "27: " + getCheckMessage(MSG_NO_PERIOD),
            "37: " + getCheckMessage(MSG_NO_PERIOD),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocStyleCompactSourceFile.java"), expected);
    }

    @Test
    public void testCompactSourceFileEmptyJavadoc() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_EMPTY),
            "24: " + getCheckMessage(MSG_EMPTY),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocStyleCompactSourceFileEmptyJavadoc.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileScope() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavadocStyleCompactSourceFileScope.java"), expected);
    }
}
