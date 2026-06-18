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
            "14:8: " + getCheckMessage(MSG_KEY, "&lt;"),
            "14:13: " + getCheckMessage(MSG_KEY, "&gt;"),
            "22:26: " + getCheckMessage(MSG_KEY, "&lt;"),
            "22:31: " + getCheckMessage(MSG_KEY, "&gt;"),
            "31:8: " + getCheckMessage(MSG_KEY, "&lt;"),
            "31:13: " + getCheckMessage(MSG_KEY, "&gt;"),
            "38:8: " + getCheckMessage(MSG_KEY, "&gt;"),
            "47:11: " + getCheckMessage(MSG_KEY, "&lt;"),
            "47:16: " + getCheckMessage(MSG_KEY, "&gt;"),
            "54:11: " + getCheckMessage(MSG_KEY, "&gt;"),
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
            "14:4: " + getCheckMessage(MSG_KEY, "&lt;"),
            "14:43: " + getCheckMessage(MSG_KEY, "&gt;"),
        };
        verifyWithInlineConfigParser(
            getNonCompilablePath(
                "compact/InputPreferLiteralJavadocInlineTagCompactSourceFile.java"),
                expected);
    }

}
