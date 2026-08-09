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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.PreferLiteralJavadocInlineTagCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class PreferLiteralJavadocInlineTagCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/preferliteraljavadocinlinetag";
    }

    @Test
    public void testGetRequiredTokens() {
        final PreferLiteralJavadocInlineTagCheck checkObj =
                new PreferLiteralJavadocInlineTagCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputPreferLiteralJavadocInlineTagCorrect.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsAngleBracketEntities() throws Exception {
        final String[] expected = {
            "17:11: " + getCheckMessage(MSG_KEY, "&amp;"),
            "17:19: " + getCheckMessage(MSG_KEY, "&quot;"),
            "17:28: " + getCheckMessage(MSG_KEY, "&apos;"),
            "17:35: " + getCheckMessage(MSG_KEY, "&lt;"),
            "17:40: " + getCheckMessage(MSG_KEY, "&gt;"),
            "25:26: " + getCheckMessage(MSG_KEY, "&lt;"),
            "25:31: " + getCheckMessage(MSG_KEY, "&gt;"),
            "34:8: " + getCheckMessage(MSG_KEY, "&lt;"),
            "34:13: " + getCheckMessage(MSG_KEY, "&gt;"),
            "41:8: " + getCheckMessage(MSG_KEY, "&gt;"),
            "42:8: " + getCheckMessage(MSG_KEY, "&apos;"),
            "42:19: " + getCheckMessage(MSG_KEY, "&quot;"),
            "54:11: " + getCheckMessage(MSG_KEY, "&lt;"),
            "54:16: " + getCheckMessage(MSG_KEY, "&gt;"),
            "55:18: " + getCheckMessage(MSG_KEY, "&amp;"),
            "55:26: " + getCheckMessage(MSG_KEY, "&gt;"),
            "65:11: " + getCheckMessage(MSG_KEY, "&gt;"),
        };

        verifyWithInlineConfigParser(
            getPath("InputPreferLiteralJavadocInlineTagAngleBracketEntities.java"), expected);
    }

    @Test
    public void testMultipleEntitiesInLine() throws Exception {
        final String[] expected = {
            "16:14: " + getCheckMessage(MSG_KEY, "&lt;"),
            "16:19: " + getCheckMessage(MSG_KEY, "&gt;"),
            "16:28: " + getCheckMessage(MSG_KEY, "&lt;"),
            "16:33: " + getCheckMessage(MSG_KEY, "&gt;"),
            "26:14: " + getCheckMessage(MSG_KEY, "&amp;"),
            "26:19: " + getCheckMessage(MSG_KEY, "&amp;"),
            "26:32: " + getCheckMessage(MSG_KEY, "&amp;"),
            "26:37: " + getCheckMessage(MSG_KEY, "&amp;"),
        };

        verifyWithInlineConfigParser(
                getPath("InputPreferLiteralJavadocInlineTagMultipleEntities.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferLiteralJavadocInlineTagEdgeCases.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckInsideInlineTagsSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputPreferLiteralJavadocInlineTagSkipInsideInlineTags.java"), expected);
    }

    @Test
    public void testPreferJavadocInlineTagsCheckCompactDefault() throws Exception {
        final String[] expected = {
            "17:19: " + getCheckMessage(MSG_KEY, "&lt;"),
            "17:24: " + getCheckMessage(MSG_KEY, "&gt;"),
            "17:29: " + getCheckMessage(MSG_KEY, "&amp;"),
            "17:35: " + getCheckMessage(MSG_KEY, "&quot;"),
            "17:42: " + getCheckMessage(MSG_KEY, "&apos;"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "compact/InputPreferLiteralJavadocInlineTagCompactSourceFile.java"),
                expected);
    }

}
