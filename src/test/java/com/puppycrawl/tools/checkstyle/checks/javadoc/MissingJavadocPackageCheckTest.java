///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocPackageCheck.MSG_PKG_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocPackageCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadocpackage";
    }

    @Test
    public void testPackageJavadocPresent() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageSingleLineJavadocPresent() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("singleline/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocPresentWithAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("annotation/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocPresentWithBlankLines() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("blank/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissing() throws Exception {
        final String[] expected = {
            "7:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(getPath("nojavadoc/package-info.java"), expected);
    }

    @Test
    public void testBlockCommentInsteadOfJavadoc() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("nojavadoc/blockcomment/package-info.java"), expected);
    }

    @Test
    public void testSinglelineCommentInsteadOfJavadoc() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("nojavadoc/singleline/package-info.java"), expected);
    }

    @Test
    public void testSinglelineCommentInsteadOfJavadoc2() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("nojavadoc/single/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingWithAnnotation() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("nojavadoc/annotation/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingWithAnnotationAndBlockComment() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("nojavadoc/annotation/blockcomment/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingDetachedJavadoc() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("nojavadoc/detached/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocPresentWithHeader() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("header/package-info.java"), expected);
    }

    @Test
    public void testPackageJavadocMissingWithBlankLines() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_PKG_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("nojavadoc/blank/package-info.java"), expected);
    }

    @Test
    public void testNotPackageInfo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyFilterWithInlineConfigParser(
                getPath("InputMissingJavadocPackageNotPackageInfo-package-info.java"), expected);
    }

    @Test
    public void testTokensAreCorrect() {
        final MissingJavadocPackageCheck check = new MissingJavadocPackageCheck();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
        };
        assertWithMessage("Acceptable required tokens are invalid")
                .that(check.getAcceptableTokens())
                .isEqualTo(expected);
        assertWithMessage("Default required tokens are invalid")
                .that(check.getDefaultTokens())
                .isEqualTo(expected);
        assertWithMessage("Required required tokens are invalid")
                .that(check.getRequiredTokens())
                .isEqualTo(expected);
    }
}
