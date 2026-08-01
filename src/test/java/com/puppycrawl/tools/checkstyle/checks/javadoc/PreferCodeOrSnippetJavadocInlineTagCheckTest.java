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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.PreferCodeOrSnippetJavadocInlineTagCheck.MSG_KEY_MULTI_LINE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.PreferCodeOrSnippetJavadocInlineTagCheck.MSG_KEY_SINGLE_LINE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class PreferCodeOrSnippetJavadocInlineTagCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return
            "/com/puppycrawl/tools/checkstyle/checks/javadoc/prefercodeorsnippetjavadocinlinetag";
    }

    @Test
    public void testGetAcceptableTokens() {
        final PreferCodeOrSnippetJavadocInlineTagCheck checkObj =
            new PreferCodeOrSnippetJavadocInlineTagCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final PreferCodeOrSnippetJavadocInlineTagCheck checkObj =
            new PreferCodeOrSnippetJavadocInlineTagCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testPreTag() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "pre"),
            "30: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
            "54: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
            "57: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
        };
        verifyWithInlineConfigParser(
            getPath("InputPreferCodeOrSnippetJavadocInlineTagPre.java"), expected);
    }

    @Test
    public void testCodeTag() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "code"),
            "30: " + getCheckMessage(MSG_KEY_MULTI_LINE, "code"),
            "54: " + getCheckMessage(MSG_KEY_MULTI_LINE, "code"),
            "57: " + getCheckMessage(MSG_KEY_MULTI_LINE, "code"),
            "67: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "code"),
        };
        verifyWithInlineConfigParser(
            getPath("InputPreferCodeOrSnippetJavadocInlineTagCode.java"), expected);
    }

    @Test
    public void testMixTags() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
            "31: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
            "48: " + getCheckMessage(MSG_KEY_MULTI_LINE, "code"),
            "65: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
        };
        verifyWithInlineConfigParser(
            getPath("InputPreferCodeOrSnippetJavadocInlineTagMix.java"), expected);
    }

    @Test
    public void testMixTags2() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "pre"),
            "30: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
        };
        verifyWithInlineConfigParser(
            getPath("InputPreferCodeOrSnippetJavadocInlineTagMix2.java"), expected);
    }

    @Test
    public void testOtherTags() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputPreferCodeOrSnippetJavadocInlineTagOther.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "12: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "pre"),
            "19: " + getCheckMessage(MSG_KEY_SINGLE_LINE, "code"),
            "36: " + getCheckMessage(MSG_KEY_MULTI_LINE, "pre"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "compact/InputPreferCodeOrSnippetJavadocInlineTagCompactSourceFile.java"),
                    expected);
    }

}
