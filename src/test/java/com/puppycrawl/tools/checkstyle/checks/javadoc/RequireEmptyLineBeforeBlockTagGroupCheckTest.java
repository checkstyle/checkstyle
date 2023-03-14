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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.RequireEmptyLineBeforeBlockTagGroupCheck.MSG_JAVADOC_TAG_LINE_BEFORE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RequireEmptyLineBeforeBlockTagGroupCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/"
                + "requireemptylinebeforeblocktaggroup";
    }

    @Test
    public void testGetRequiredTokens() {
        final RequireEmptyLineBeforeBlockTagGroupCheck checkObj =
                new RequireEmptyLineBeforeBlockTagGroupCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testCorrect() throws Exception {
        createModuleConfig(
                RequireEmptyLineBeforeBlockTagGroupCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRequireEmptyLineBeforeBlockTagGroupCorrect.java"),
                expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@since"),
            "20: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@param"),
            "28: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@param"),
            "35: " + getCheckMessage(MSG_JAVADOC_TAG_LINE_BEFORE, "@return"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireEmptyLineBeforeBlockTagGroupIncorrect.java"),
                expected);
    }
}
