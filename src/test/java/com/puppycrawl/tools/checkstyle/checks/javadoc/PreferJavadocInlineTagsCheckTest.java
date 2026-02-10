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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.PreferJavadocInlineTagsCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class PreferJavadocInlineTagsCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/preferjavadocinlinetags";
    }

    @Test
    public void testGetRequiredTokens() {
        final PreferJavadocInlineTagsCheck checkObj = new PreferJavadocInlineTagsCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsCorrect.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCodeTag() throws Exception {
        final String[] expected = {
            "12:12: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
            "18:16: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
            "26:21: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsCodeTag.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsAnchorTag() throws Exception {
        final String[] expected = {
            "12:8: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "18:17: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "25:39: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "32:28: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "39:34: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
        };
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsAnchorTag.java"), expected);
    }

    @Test
    public void testExternalLinksSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsExternalLinks.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsAngleBracketEntities() throws Exception {
        final String[] expected = {
            "14:8: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "14:13: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "22:26: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "22:31: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
        };

        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsAngleBracketEntities.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckInsidePreTagSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsInsidePreSkipped.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsMixedCases() throws Exception {
        final String[] expected = {
            "14:16: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
            "21:12: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "30:12: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "30:17: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsMixed.java"), expected);
    }

    @Test
    public void testMultipleEntitiesInLine() throws Exception {
        final String[] expected = {
            "16:14: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "16:19: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "16:28: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "16:33: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsMultipleEntities.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsEdgeCases.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckInsideInlineTagsSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsSkipInsideInlineTags.java"), expected);
    }
}
