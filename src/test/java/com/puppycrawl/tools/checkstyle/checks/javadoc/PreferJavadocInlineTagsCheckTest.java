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
            "13:12: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
            "19:16: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
            "27:21: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsCodeTag.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsAnchorTag() throws Exception {
        final String[] expected = {
            "13:8: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "19:17: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "26:39: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "33:28: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "40:34: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "96:14: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
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
            "15:8: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "15:13: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "23:26: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "23:31: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "32:8: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "32:13: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "39:8: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "48:11: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "48:16: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "55:11: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
        };

        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsAngleBracketEntities.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckForPre() throws Exception {
        final String[] expected = {
            "15:4: " + getCheckMessage(MSG_KEY, "{@snippet ...}", "<pre>"),
            "27:8: " + getCheckMessage(MSG_KEY, "{@snippet ...}", "<pre>"),
            "37:8: " + getCheckMessage(MSG_KEY, "{@snippet ...}", "<pre>"),
            "47:8: " + getCheckMessage(MSG_KEY, "{@snippet ...}", "<pre>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsForPre.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsJdkVersion8() throws Exception {
        final String[] expected = {
            "21:8: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsJdkVersion8.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsMixedCases() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_KEY, "{@code ...}", "<code>"),
            "22:12: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "31:12: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "31:17: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsMixed.java"), expected);
    }

    @Test
    public void testMultipleEntitiesInLine() throws Exception {
        final String[] expected = {
            "17:14: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "17:19: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
            "17:28: " + getCheckMessage(MSG_KEY, "{@literal <}", "&lt;"),
            "17:33: " + getCheckMessage(MSG_KEY, "{@literal >}", "&gt;"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferJavadocInlineTagsMultipleEntities.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = {
            "76:8: " + getCheckMessage(MSG_KEY, "{@snippet ...}", "<pre>"),
        };
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsEdgeCases.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckInsideInlineTagsSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsSkipInsideInlineTags.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckInsideSnippetSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsSnippetTag.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckJdkVersion17() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsJdkVersion.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckJdkVersion4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferJavadocInlineTagsJdkVersion4.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckCompactDefault() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "20:35: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "34:4: " + getCheckMessage(MSG_KEY, "{@snippet ...}", "<pre>"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("compact/InputPreferJavadocInlineTagsCompactSourceFile.java"),
                expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckCompactNonDefault() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
            "20:35: " + getCheckMessage(MSG_KEY, "{@link ...}", "<a href=\"#...\">"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath("compact/InputPreferJavadocInlineTagsCompactSourceFileTwo.java"),
                expected);
    }

}
